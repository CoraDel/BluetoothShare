package com.example.cdelplac.testassets;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Search extends AppCompatActivity {
    private static final String EQUIPMENT_TABLE_NAME = "Equipment_tableTest";
    private static final String PRODUCT = "product";
    private static final String PROGRAM = "program";
    private static final String ID_TYPE = "id_type";
    private String mType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        final RadioGroup radioGroupEquipement = (RadioGroup) findViewById(R.id.equipementRadioGroup);
        radioGroupEquipement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int isCheck) {
                if(isCheck == R.id.radioButtonVHF || isCheck == R.id.radioButtonHF){
                    mType = "VHF-HF";
                }
                else if (isCheck == R.id.radioButtonAUDIO){
                    mType = "AUDIO";
                }
                else if (isCheck == R.id.radioButtonSATCOM){
                    mType = "SATCOM";
                }
                loadSpinnerData();
            }
        });

        Button btnAll = findViewById(R.id.btn_all_equipment);
        sendExtraChecked();
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnAdd = findViewById(R.id.btn_add_newequipment);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddE = new Intent(Search.this, AddEquipement.class);
                startActivity(intentAddE);
            }
        });


        Button extractDb = findViewById(R.id.btn_extract_data);
        extractDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ina = new Intent(Search.this, ExtractDB.class);
                startActivity(ina);
            }
        });
        Button bluethoohBtn = findViewById(R.id.btn_bluethooh_data);
        bluethoohBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Search.this, Bluetooth.class);
                startActivity(in);
            }
        });

    }

    private void sendExtraChecked(){
        final Spinner spinner = findViewById(R.id.ProductSpinner);
        final RadioGroup radioGroupEquipement = (RadioGroup) findViewById(R.id.equipementRadioGroup);
        final RadioGroup radioGroupProgram = findViewById(R.id.programmeRadioGroup);
        Button btnDisplay = (Button) findViewById(R.id.SearchEquipmentButton);

        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedIdEquipement = radioGroupEquipement.getCheckedRadioButtonId();
                int selectedIdProgram = radioGroupProgram.getCheckedRadioButtonId();
                RadioButton radioButtonEquipement = (RadioButton)findViewById(selectedIdEquipement);
                RadioButton radioButtonProgram = (RadioButton)findViewById(selectedIdProgram);

                if(radioGroupEquipement.getCheckedRadioButtonId() == -1 ||
                        radioGroupProgram.getCheckedRadioButtonId() == -1){
                    Toast.makeText(Search.this, "Please fill all fields",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    String type = radioButtonEquipement.getText().toString();
                    String program = radioButtonProgram.getText().toString();
                    String product = spinner.getSelectedItem().toString();

                    Intent intent = new Intent(Search.this, ResultSearch.class);
                    intent.putExtra("radioButtonEquipment",type);
                    intent.putExtra("radioButtonProgram", program);
                    intent.putExtra("spinnerValue", product);
                    startActivity(intent);
                }
            }
        });
    }

    private void loadSpinnerData() {
        final Spinner spinner = findViewById(R.id.ProductSpinner);
        //DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> lables = getAllLabel();
        //Avoid repetitions with HashSet
        Set<String> hs = new HashSet<>();
        hs.addAll(lables);
        lables.clear();
        lables.addAll(hs);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public List<String> getAllLabel(){
            List<String> labels = new ArrayList<String>();
            //String test = "select product from Equipment_tableTest where id_type = 'SATCOM'";
            String test = "select product from Equipment_tableTest where id_type =" +" '"+mType+"'";
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            //String selectQuery = "SELECT "+PRODUCT+" FROM " + EQUIPMENT_TABLE_NAME+" where "+ ID_TYPE+ "="+ ;
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery(test, null);
            if(cursor.moveToFirst()){
                do{
                    labels.add(cursor.getString(0));

                }while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
        return labels;
    }

    @Override
    public void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
