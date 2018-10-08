package com.example.cdelplac.testassets;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String EQUIPMENT_TABLE_NAME = "Equipment_tableTest";
    private ArrayList<Model> equipment;
    private ListView listTest;
    private Adapter adapter;
    public static String EXTRA_ID = "id_equipment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper db = new DatabaseHelper(this);

        //________Method to clean and populate DATABASE
        //db.addBdd()
        //db.deleteAllAudioTests();
        //db.addObjetEquipment(this);
        //A faire qu'une fois pour créer la table


        //extractDatabase();


        listTest = findViewById(R.id.listview);

        equipment = new ArrayList<>();
        adapter = new Adapter(this, equipment);
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        final Cursor cursor = sqLiteDatabase.rawQuery("select * from " + EQUIPMENT_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Model equipmentModel = new Model();
                equipmentModel.setId(cursor.getInt(0));
                equipmentModel.setEquiment_type(cursor.getString(1));
                equipmentModel.setProgram(cursor.getString(2));
                equipmentModel.setProduct(cursor.getString(3));
                equipmentModel.setStatut(cursor.getString(4));
                equipmentModel.setModel(cursor.getString(5));
                equipmentModel.setStandard(cursor.getString(6));
                equipmentModel.setPn(cursor.getString(7));
                equipmentModel.setSn(cursor.getString(8));
                equipmentModel.setPnsoft(cursor.getString(9));
                equipmentModel.setCms(cursor.getString(10));
                equipmentModel.setLocation(cursor.getString(11));

                equipment.add(equipmentModel);
            }
            while (cursor.moveToNext());
        }
        listTest.setAdapter(adapter);

        //------------------------SEARCH LIST----------------------------------------------------

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                equipment = new ArrayList<>();
                adapter = new Adapter(getApplicationContext(), equipment);
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                Cursor cursor1 = db.searchDataBase(s);

                if (cursor1.moveToFirst()) {
                    do {
                        Model model = new Model();
                        model.setId(cursor1.getInt(0));
                        model.setEquiment_type(cursor1.getString(1));
                        model.setProgram(cursor1.getString(2));
                        model.setProduct(cursor1.getString(3));
                        model.setStatut(cursor1.getString(4));
                        model.setModel(cursor1.getString(5));
                        model.setStandard(cursor1.getString(6));
                        model.setPn(cursor1.getString(7));
                        model.setSn(cursor1.getString(8));
                        model.setPnsoft(cursor1.getString(9));
                        model.setCms(cursor1.getString(10));
                        model.setLocation(cursor1.getString(11));

                        equipment.add(model);

                    } while (cursor1.moveToNext());
                }
                listTest.setAdapter(adapter);
                return true;
            }
        });

        listTest.setAdapter(adapter);

        listTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Model modelSend = equipment.get(position);
                int vall = modelSend.getId();
                Intent intentSend = new Intent(getApplicationContext(), DetailsEquipment.class);
                intentSend.putExtra(EXTRA_ID, String.valueOf(vall));
                startActivity(intentSend);
            }
        });
    }

    //----------------------------------------------------------------------------

    public void extractDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/Test_CSV.db";
                String backupDBPath = "backup.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                Toast.makeText(getApplicationContext(), sd.getAbsolutePath(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }
    }
}
