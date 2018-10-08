package com.example.cdelplac.testassets;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UTFDataFormatException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static com.example.cdelplac.testassets.DatabaseHelper.EQUIPMENT_TABLE_NAME;

public class ExtractDB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_db);
        exportDb();

    }

    //todo : essayer de l'utiliser pour extract db
    public void exportDb() {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        File exportDir = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "");
        if (!exportDir.exists()) {
            exportDir.mkdir();
        }

        File file = new File(exportDir, "TestExport.csv");
        String line = "";
        try {
            file.createNewFile();
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file), ',', CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            //SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor curCSV = db.rawQuery("select * from " + EQUIPMENT_TABLE_NAME, null);

            csvWriter.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                String[] arrStr = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2), curCSV.getString(3),
                        curCSV.getString(4), curCSV.getString(5), curCSV.getString(6), curCSV.getString(7), curCSV.getString(8),
                        curCSV.getString(9), curCSV.getString(10), curCSV.getString(11), curCSV.getString(12), curCSV.getString(13), curCSV.getString(14)};
                csvWriter.writeNext(arrStr);
            }

            Toast.makeText(getApplicationContext(), "Extract done", Toast.LENGTH_SHORT).show();
            TextView tvInfo = findViewById(R.id.tv_info);
            tvInfo.setText("Equipments Data Extract under Excel File");
            TextView textView = findViewById(R.id.tv_download);
            textView.setText("Path's file : " + file.getAbsolutePath());
            csvWriter.close();
            curCSV.close();


        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}