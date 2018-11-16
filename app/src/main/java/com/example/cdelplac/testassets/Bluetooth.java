package com.example.cdelplac.testassets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.DIRECTORY_DOWNLOADS;


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
    private String bluetoothCsvFile = "/blt.csv";
    private TextView mtvDevice, tvData;
    private Button parseTest, sendToDevice;
    private Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    }

    @Override
    public void onStart() {
        super.onStart();
        checkBTState();
        if (mBluetoothService == null) {
            mBluetoothService = new BluetoothConnectionService(this, mHandler);
        }
        if (mBluetoothService != null) {
            setup();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //It is best to check BT status at onResume in case something has changed while app was paused etc
        if (mBluetoothService != null) {
            if (mBluetoothService.getState() == BluetoothConnectionService.STATE_NONE) {
                mBluetoothService.start();
            }
        }
    }

    private void setup() {
        sendToDevice = findViewById(R.id.btn_send);
        sendToDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    uri = Uri.fromFile(new File("/sdcard/Download/TestExport.csv"));
                    byte[] send = readBytes(uri);
                    mBluetoothService.write(send);

                    try {
                        //todo : tester avec plus de temps sur la pause du thread
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    byte[] stop = ("stop").getBytes();
                    mBluetoothService.write(stop);
                    //todo : tester
                    Toast.makeText(Bluetooth.this, uri.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Bluetooth.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Protocole pour concaténer l'envoie de byte qui est limité  à 1024
    String receive = "";
    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //OutputStream
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    tvData.setText("Data send " + writeMessage);
                    //arrayToDbBlt(writeMessage);

                    break;
                //InputStream
                case MESSAGE_READ:
                    byte[] readBuf = null;
                    readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf);

                    if (!readMessage.substring(0, 5).contains("stop")) {
                        receive += readMessage;
                    } else {
                        writeCsvBlutooth(receive);
                        tvData.setText("Data received" + (receive));
                        //todo : traitement avec la base
                        receive = "";
                        readBuf = null;
                        readMessage = "";

                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    mtvDevice.setText("Start synchronization with " + mConnectedDeviceName);
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
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if (mBluetoothAdapter == null) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
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
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    mBluetoothService = new BluetoothConnectionService(this, mHandler);
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, "Bluetooth was not enabled. Leaving Bluetooth Chat"
                            , Toast.LENGTH_SHORT).show();
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


    //---------------------------Methode to generate new file by data received

    public void writeCsvBlutooth(String content) {
        File file = new File(Environment.getExternalStoragePublicDirectory
                (DIRECTORY_DOWNLOADS), "");
        if (!file.exists()) {
            file.mkdir();
        }
        File nFile = new File(file, bluetoothCsvFile);
        try {
            FileWriter writer = new FileWriter(nFile);
            writer.append(content);
            writer.flush();
            writer.close();
            Toast.makeText(this, "File saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}





