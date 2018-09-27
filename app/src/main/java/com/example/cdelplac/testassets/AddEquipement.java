package com.example.cdelplac.testassets;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.cdelplac.testassets.DatabaseHelper.CMS;
import static com.example.cdelplac.testassets.DatabaseHelper.COMMEBTS;
import static com.example.cdelplac.testassets.DatabaseHelper.DATECREA;
import static com.example.cdelplac.testassets.DatabaseHelper.EQUIPMENT_TABLE_NAME;
import static com.example.cdelplac.testassets.DatabaseHelper.ID_STANDARD;
import static com.example.cdelplac.testassets.DatabaseHelper.ID_TYPE;
import static com.example.cdelplac.testassets.DatabaseHelper.LOCATION;
import static com.example.cdelplac.testassets.DatabaseHelper.MODEL;
import static com.example.cdelplac.testassets.DatabaseHelper.PN;
import static com.example.cdelplac.testassets.DatabaseHelper.PNSOFT;
import static com.example.cdelplac.testassets.DatabaseHelper.PRODUCT;
import static com.example.cdelplac.testassets.DatabaseHelper.PROGRAM;
import static com.example.cdelplac.testassets.DatabaseHelper.SN;
import static com.example.cdelplac.testassets.DatabaseHelper.STATUT;

public class AddEquipement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipement);

        String test = "select product from Equipment_tableTest";
        ArrayAdapter<String> productArray = resultRequest(test);
        final AutoCompleteTextView actv = findViewById(R.id.autocomplet_product);
        setAutoCmplete(actv);
        actv.setAdapter(productArray);

        String requestStandard = "select id_standard from Equipment_tableTest";
        ArrayAdapter<String> stAdapter = resultRequest(requestStandard);
        final AutoCompleteTextView actvstandar = findViewById(R.id.actv_standard);
        actvstandar.setAdapter(stAdapter);
        setAutoCmplete(actvstandar);

        String modelbdrequest = "select model from Equipment_tableTest";
        ArrayAdapter<String> mdlAdpater = resultRequest(modelbdrequest);
        final AutoCompleteTextView actmd = findViewById(R.id.actv_model);
        actmd.setAdapter(mdlAdpater);
        setAutoCmplete(actmd);

        String pnrequest = "select pn from Equipment_tableTest";
        ArrayAdapter<String> pnAdpater = resultRequest(pnrequest);
        final AutoCompleteTextView acpn = findViewById(R.id.actv_pn);
        acpn.setAdapter(pnAdpater);
        setAutoCmplete(acpn);

        String snrequest = "select sn from Equipment_tableTest";
        ArrayAdapter<String> snAdpater = resultRequest(snrequest);
        final AutoCompleteTextView acsn = findViewById(R.id.actv_sn);
        acsn.setAdapter(snAdpater);
        setAutoCmplete(acsn);

        String pnsoftReq = "select pnsoft from Equipment_tableTest";
        ArrayAdapter<String> pnSdpater = resultRequest(pnsoftReq);
        final AutoCompleteTextView acpnsoft = findViewById(R.id.actv_Pnsoft);
        acpnsoft.setAdapter(pnSdpater);
        setAutoCmplete(acpnsoft);

        String statutReq = "select statut from Equipment_tableTest";
        ArrayAdapter<String> statutAd = resultRequest(statutReq);
        final AutoCompleteTextView acpnstt = findViewById(R.id.actv_statut);
        acpnstt.setAdapter(statutAd);
        setAutoCmplete(acpnstt);

        String cmsReq = "select cms from Equipment_tableTest";
        ArrayAdapter<String> cmsAd = resultRequest(cmsReq);
        final AutoCompleteTextView accms = findViewById(R.id.actv_cms);
        accms.setAdapter(cmsAd);
        setAutoCmplete(accms);

        String locReq = "select location from Equipment_tableTest";
        ArrayAdapter<String> locAd = resultRequest(locReq);
        final AutoCompleteTextView acloc = findViewById(R.id.actv_location);
        setAutoCmplete(acloc);
        acloc.setAdapter(locAd);

        final RadioGroup rgequipment = findViewById(R.id.equipementRadioGroupAdd);
        final RadioGroup rgpgm = findViewById(R.id.programmeRadioGroupAdd);
        Button button = findViewById(R.id.btn_add);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selectedIdEqpmt = rgequipment.getCheckedRadioButtonId();
                    RadioButton rbEqpmt = (RadioButton)findViewById(selectedIdEqpmt);
                    int selectedIdPgm = rgpgm.getCheckedRadioButtonId();
                    RadioButton rbPgm = (RadioButton)findViewById(selectedIdPgm);

                        String type = rbEqpmt.getText().toString();
                        String program = rbPgm.getText().toString();

                        String product = actv.getText().toString().trim();
                        String standard = actvstandar.getText().toString().trim();
                        String model = actmd.getText().toString().trim();
                        String pn = acpn.getText().toString().trim();
                        String sn = acsn.getText().toString().trim();
                        String pnsoft = acpnsoft.getText().toString().trim();
                        String statut = acpnstt.getText().toString().trim();
                        String cms = accms.getText().toString().trim();
                        String location = acloc.getText().toString().trim();
                        EditText coms = findViewById(R.id.et_comsadd);
                        String commAdd = coms.getText().toString().trim();

                        String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                        ContentValues insertValues = new ContentValues();
                        insertValues.put(ID_TYPE, type);
                        insertValues.put(PROGRAM, program);
                        insertValues.put(PRODUCT, product);
                        insertValues.put(STATUT, statut);
                        insertValues.put(MODEL, model);
                        insertValues.put(ID_STANDARD, standard);
                        insertValues.put(PN, pn);
                        insertValues.put(SN, sn);
                        insertValues.put(PNSOFT, pnsoft);
                        insertValues.put(CMS, cms);
                        insertValues.put(LOCATION, location);
                        insertValues.put(DATECREA, date);
                        insertValues.put(COMMEBTS, commAdd);

                        sqLiteDatabase.insert(EQUIPMENT_TABLE_NAME, null, insertValues);


                    Toast.makeText(AddEquipement.this, "Adding ok", Toast.LENGTH_SHORT).show();
                    Intent intentR = new Intent(AddEquipement.this,Search.class);
                    startActivity(intentR);

                }
            });
    }

    public ArrayAdapter<String> resultRequest(String request){
        AutocompleteClass ac = new AutocompleteClass(getApplicationContext());
        List<String> list = ac.getAlldbList(request);
        ArrayAdapter<String> arrayAdapter = ac.getAdpaterBd(list);
        return arrayAdapter;
    }

    public void setAutoCmplete(AutoCompleteTextView autoCmplete){
        autoCmplete.setThreshold(1);
        autoCmplete.setTextColor(Color.RED);
    }

}
