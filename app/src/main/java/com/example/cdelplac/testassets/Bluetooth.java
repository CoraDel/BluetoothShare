package com.example.cdelplac.testassets;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;



public class Bluetooth extends Activity {
    public static final String TOAST = "toast";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int PICKFILE_RESULT_CODE = 3;

    // Member fields
    private BluetoothAdapter mBluetoothAdapter = null;
    private String mConnectedDeviceName = null;
    public static final String DEVICE_NAME = "device_name";

    private BluetoothConnectionService mBluetoothService;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    TextView mtvDevice, tvContent, tvData;
    Button parseTest, sendToDevice;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        tvData = findViewById(R.id.tv_data_retieve);

        mtvDevice = findViewById(R.id.tv_connect_device);
        mtvDevice.setText("Select a device to synchro");
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        parseTest = findViewById(R.id.btn_test_file_parse);
        parseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                Intent i = Intent.createChooser(intent, "File");
                startActivityForResult(i, PICKFILE_RESULT_CODE);

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        if(mBluetoothService == null){
            mBluetoothService = new BluetoothConnectionService(this, mHandler);
        }
        checkBTState();
        if(mBluetoothService != null) setup();
    }


    @Override
    public void onResume() {
        super.onResume();
        //It is best to check BT status at onResume in case something has changed while app was paused etc
        if(mBluetoothService != null){
            if(mBluetoothService.getState() == BluetoothConnectionService.STATE_NONE){
                mBluetoothService.start();
            }
        }
    }

    private void setup(){
        sendToDevice = findViewById(R.id.btn_send);
        sendToDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //--------------------Fonctionne mais que String-------------------
               /* mfileContent = mFileToSend.getAbsolutePath();
                byte[] send = mfileContent.getBytes();
                mBluetoothService.write(send);
                Toast.makeText(Bluetooth.this,mfileContent, Toast.LENGTH_SHORT).show();*/
                //-------------------------------------------------
                try {
                    //Uri uriSend = Uri.fromFile(new File(mFileToSend.getPath()));
                    byte[] send = readBytes(uri);
                    mBluetoothService.write(send);
                    Toast.makeText(Bluetooth.this, uri.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Bluetooth.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    tvData.setText("Data send " +writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    tvData.setText("Data received" + readMessage);
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                    mtvDevice.setText("Start synchronization with " + mConnectedDeviceName) ;
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    //method to check if the device has Bluetooth and if it is on.
    //Prompts the user to turn it on if it is off
    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        mBluetoothAdapter =BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBluetoothAdapter ==null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                    //String filePath = data.getData().getPath();
                    uri = data.getData();
                    tvContent = findViewById(R.id.tv_content_name);
                    tvContent.setText(uri.toString());

                break;
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras().getString(EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mBluetoothService.connect(device);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode ==  Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    mBluetoothService = new BluetoothConnectionService(this, mHandler);
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, "Bluetooth was not enabled. Leaving Bluetooth Chat", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public byte[] readBytes(Uri uri) throws IOException {
        // this dynamically extends to take the bytes you read
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    public void connect(View v) {
        Intent serverIntent = new Intent(this, DeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }

}

