package com.example.cdelplac.testassets;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.cdelplac.testassets.DatabaseHelper.CMS;
import static com.example.cdelplac.testassets.DatabaseHelper.COMMEBTS;
import static com.example.cdelplac.testassets.DatabaseHelper.DATABASE_NAME;
import static com.example.cdelplac.testassets.DatabaseHelper.DATECREA;
import static com.example.cdelplac.testassets.DatabaseHelper.DATEMAJ;

import static com.example.cdelplac.testassets.DatabaseHelper.ID_TYPE;
import static com.example.cdelplac.testassets.DatabaseHelper.LOCATION;
import static com.example.cdelplac.testassets.DatabaseHelper.MODEL;
import static com.example.cdelplac.testassets.DatabaseHelper.PN;
import static com.example.cdelplac.testassets.DatabaseHelper.PNSOFT;
import static com.example.cdelplac.testassets.DatabaseHelper.PROGRAM;
import static com.example.cdelplac.testassets.DatabaseHelper.SN;
import static com.example.cdelplac.testassets.DatabaseHelper.STATUT;
import static com.example.cdelplac.testassets.ResultSearch.EQUIPMENT_TABLE_NAME;

import static com.example.cdelplac.testassets.MainActivity.EXTRA_ID;

public class DetailsEquipment extends AppCompatActivity {
    private String mType, mProgram, mProduct, mStatut, mModel, mStandard, mPn, mSn, mPnsoft, mCms, mLocation, mDateCrea, mUpdateDate, mComments;
    private EditText etCreaDate;
    private Calendar mCreaCalendar;
    private boolean mChangeDb = false;
    private String modelId;
    public static String finalDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_equipment);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
       if(bd != null){
            modelId = intent.getStringExtra(EXTRA_ID);
           // modelId = intent.getStringExtra("test");
        }

        final Button btnModifSave = findViewById(R.id.btn_save);
        Button btnReturn = findViewById(R.id.btn_cancel);

        final EditText etType = findViewById(R.id.actv_type);
        final EditText etProgram = findViewById(R.id.et_program);
        final EditText etProduct = findViewById(R.id.et_product);
        final EditText etStatut = findViewById(R.id.et_statut);
        final EditText etModel = findViewById(R.id.et_model);
        final EditText etStandard = findViewById(R.id.et_standard);
        final EditText etPn = findViewById(R.id.et_pn);
        final EditText etSn = findViewById(R.id.et_sn);
        final EditText etPnSoft = findViewById(R.id.et_pnsoft);
        final EditText etCms = findViewById(R.id.et_cms);
        final EditText etLocation = findViewById(R.id.et_location);
        etCreaDate = findViewById(R.id.et_datecrea);
        final EditText etUpdateDate= findViewById(R.id.et_dateMAJ);
        final EditText etComs= findViewById(R.id.et_comm);




        mCreaCalendar = Calendar.getInstance();
        mCreaCalendar.setTimeInMillis(System.currentTimeMillis());
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mCreaCalendar.set(Calendar.YEAR, year);
                mCreaCalendar.set(Calendar.MONTH, month);
                mCreaCalendar.set(Calendar.DATE, day);
                updateLabel();
            }
        };

        DatabaseHelper db = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        String query = "select * from Equipment_tableTest where id_equipment = '";

        Cursor cursor = sqLiteDatabase.rawQuery(query+modelId+"'", null);
        if (cursor.moveToFirst()){
            Model model = new Model();
            mType = model.setEquiment_type(cursor.getString(1));
            mProgram = model.setProgram(cursor.getString(2));
            mProduct = model.setProduct(cursor.getString(3));
            mStatut = model.setStatut(cursor.getString(4));
            mModel = model.setModel(cursor.getString(5));
            mStandard = model.setStandard(cursor.getString(6));
            mPn = model.setPn(cursor.getString(7));
            mSn = model.setSn(cursor.getString(8));
            mPnsoft = model.setPnsoft(cursor.getString(9));
            mCms = model.setCms(cursor.getString(10));
            mLocation = model.setLocation(cursor.getString(11));
            mDateCrea = model.setLocation(cursor.getString(12));
            mUpdateDate = model.setLocation(cursor.getString(13));
            mComments = model.setLocation(cursor.getString(14));

        }
        etType.setText(mType);
        etProgram.setText(mProgram);
        etProduct.setText(mProduct);
        etStatut.setText(mStatut);
        etModel.setText(mModel);
        etStandard.setText(mStandard);
        etPn.setText(mPn);
        etSn.setText(mSn);
        etPnSoft.setText(mPnsoft);
        etCms.setText(mCms);
        etLocation.setText(mLocation);
        etCreaDate.setText(mDateCrea);
        etUpdateDate.setText(mUpdateDate);
        etComs.setText(mComments);


        btnModifSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnModifSave.setText("Save");

                etType.setFocusableInTouchMode(true);
                etProgram.setFocusableInTouchMode(true);
                etProduct.setFocusableInTouchMode(true);
                etStatut.setFocusableInTouchMode(true);
                etModel.setFocusableInTouchMode(true);
                etStandard.setFocusableInTouchMode(true);
                etPn.setFocusableInTouchMode(true);
                etSn.setFocusableInTouchMode(true);
                etPnSoft.setFocusableInTouchMode(true);
                etCms.setFocusableInTouchMode(true);
                etLocation.setFocusableInTouchMode(true);
                etCreaDate.setFocusableInTouchMode(true);
                etUpdateDate.setFocusableInTouchMode(true);
                etComs.setFocusableInTouchMode(true);
                etCreaDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(DetailsEquipment.this, date, mCreaCalendar
                                .get(Calendar.YEAR), mCreaCalendar.get(Calendar.MONTH),
                                mCreaCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                btnModifSave.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onClick(View view) {
                        String type = etType.getText().toString();
                        String program = etProgram.getText().toString();
                        String product = etProduct.getText().toString();
                        String model = etModel.getText().toString();
                        String standard = etStandard.getText().toString();
                        String pn = etPn.getText().toString();
                        String sn = etSn.getText().toString();
                        String pnsoft = etPnSoft.getText().toString();
                        String statut = etStatut.getText().toString();
                        String cms = etCms.getText().toString();
                        String location = etLocation.getText().toString();
                        String dateCrea = etCreaDate.getText().toString();
                        String dateMAJ = etUpdateDate.getText().toString();
                        String comm = etComs.getText().toString();



                        if(!type.equals(mType) || !location.equals(mLocation) || !sn.equals(mSn)  || !pnsoft.equals(mPnsoft) || !cms.equals(mCms)|| !statut.equals(mStatut)
                                || !type.equals(mType) || !program.equals(mProgram) || !product.equals(mProduct) || !standard.equals(mStandard)
                                || !pn.equals(mPn) || !model.equals(mModel) || !dateCrea.equals(mDateCrea) || !dateMAJ.equals(mUpdateDate) || !comm.equals(mComments)){

                            mChangeDb = true;
                        }
                        else{
                            mChangeDb = false;
                        }

                        if(mChangeDb){

                            DatabaseHelper db = new DatabaseHelper(DetailsEquipment.this);
                            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
                            String query = "id_equipment = '"+ modelId+"'";

                            ContentValues updateValues = new ContentValues();
                            updateValues.put(ID_TYPE, type);
                            updateValues.put(PROGRAM, program);
                            updateValues.put(MODEL, model);
                            updateValues.put(PN, pn);
                            updateValues.put(SN, sn);
                            updateValues.put(PNSOFT, pnsoft);
                            updateValues.put(STATUT, statut);
                            updateValues.put(CMS, cms);
                            updateValues.put(LOCATION, location);
                            updateValues.put(DATECREA, dateCrea);
                            updateValues.put(DATEMAJ, (new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date())));
                            updateValues.put(COMMEBTS, comm);

                            sqLiteDatabase.update(EQUIPMENT_TABLE_NAME, updateValues, query, null);
                            Toast.makeText(DetailsEquipment.this, "Update ok", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else {
                            Toast.makeText(DetailsEquipment.this, "Nothing change", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void updateLabel(){
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etCreaDate.setText(sdf.format(mCreaCalendar.getTime()));

    }


}

