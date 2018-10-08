package com.example.cdelplac.testassets;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Test_CSV";
    public static final String EQUIPMENT_TABLE_NAME = "Equipment_tableTest";
    public static final String DUPLICATE_TABLE = "duplicate_table";

    public final ArrayList<Model> searchListEquipment = new ArrayList<Model>();
    //Constants rows
    public static final String ID_TYPE = "id_type";
    public static final String ID_STANDARD = "id_standard";
    public static final String ID_EQUIPMENT = "id_equipment";
    public static final String PROGRAM = "program";
    public static final String PRODUCT = "product";
    public static final String PN = "pn";
    public static final String SN = "sn";
    public static final String PNSOFT = "pnsoft";
    public static final String MODEL= "model";
    public static final String CMS= "cms";
    public static final String LOCATION= "location";
    public static final String STATUT = "statut";
    public static final String DATECREA = "CreaDate";
    public static final String DATEMAJ = "updateDate";
    public static final String COMMEBTS = "comments";

    private static final String EQUIPMENT_TABLE_CREATE =
            "CREATE TABLE " + EQUIPMENT_TABLE_NAME  + " (" +
                    ID_EQUIPMENT + " INTEGER PRIMARY KEY, " +
                    ID_TYPE  + " TEXT, " +
                    PROGRAM  + " TEXT, " +
                    PRODUCT  + " TEXT, " +
                    STATUT  + " TEXT, " +
                    MODEL  + " TEXT, " +
                    ID_STANDARD + " TEXT, " +
                    PN + " TEXT, " +
                    SN + " TEXT, " +
                    PNSOFT + " TEXT, " +
                    CMS + " TEXT, " +
                    LOCATION + " TEXT, " +
                    DATECREA + " TEXT, " +
                    DATEMAJ + " TEXT, " +
                    COMMEBTS + " TEXT);";

    //-------------Duplicate Table--------------------------

    private static final String DUPLICATE_TABLE_CREATE =
            "CREATE TABLE " + DUPLICATE_TABLE  + " (" +
                    ID_EQUIPMENT + " INTEGER PRIMARY KEY, " +
                    ID_TYPE  + " TEXT, " +
                    PROGRAM  + " TEXT, " +
                    PRODUCT  + " TEXT, " +
                    STATUT  + " TEXT, " +
                    MODEL  + " TEXT, " +
                    ID_STANDARD + " TEXT, " +
                    PN + " TEXT, " +
                    SN + " TEXT, " +
                    PNSOFT + " TEXT, " +
                    CMS + " TEXT, " +
                    LOCATION + " TEXT, " +
                    DATECREA + " TEXT, " +
                    DATEMAJ + " TEXT, " +
                    COMMEBTS + " TEXT);";
    //-------------Duplicate Table--------------------------



    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EQUIPMENT_TABLE_CREATE);

    }

    public  void createEquipmentTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(EQUIPMENT_TABLE_CREATE);
    }

    //----------------Create duplicate Table------------------
    public  void createDuplicateTable(){
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL(DUPLICATE_TABLE_CREATE);
    }
    //_!!!_____ FAIRE LE DELETE DUPLICATE TABLE_______________!!!
    //----------------Create duplicate Table------------------


    public void addBdd(){
        createEquipmentTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }


    //todo : creer une methode avec table_name en argument pour nouvelle table bluetooth
    //Ajout de la bdd en raw/csv


    public void addObjetEquipment(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line = "";
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                Log.d("MyActivity", "Line: " + line);
                String[] tokens = line.split(";");
                final  Model equipmentData = new Model();
                equipmentData.setEquiment_type(tokens[0]);
                equipmentData.setProgram(tokens[1]);
                equipmentData.setProduct(tokens[2]);
                equipmentData.setModel(tokens[3]);
                equipmentData.setStandard(tokens[4]);
                equipmentData.setPn(tokens[5]);
                equipmentData.setSn(tokens[6]);
                equipmentData.setPnsoft(tokens[7]);
                equipmentData.setStatut(tokens[8]);
                equipmentData.setCms(tokens[9]);
                equipmentData.setLocation(tokens[10]);
                equipmentData.setDatecrea(tokens[11]);
                equipmentData.setDateMaj(tokens[12]);
                equipmentData.setComments(tokens[13]);

                SQLiteDatabase sqLiteDatabase = getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put(ID_TYPE, equipmentData.getEquiment_type());
                values.put(PROGRAM, equipmentData.getProgram());
                values.put(PRODUCT, equipmentData.getProduct());
                values.put(MODEL, equipmentData.getModel());
                values.put(ID_STANDARD, equipmentData.getStandard());;
                values.put(PN, equipmentData.getPn());
                values.put(SN, equipmentData.getSn());
                values.put(PNSOFT, equipmentData.getPnsoft());
                values.put(STATUT, equipmentData.getStatut());
                values.put(CMS, equipmentData.getCms());
                values.put(LOCATION, equipmentData.getLocation());
                values.put(DATECREA, equipmentData.getDatecrea());
                values.put(DATEMAJ, equipmentData.getDateMaj());
                values.put(COMMEBTS, equipmentData.getComments());

                sqLiteDatabase.insert(EQUIPMENT_TABLE_NAME, null, values);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteAllAudioTests(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + EQUIPMENT_TABLE_NAME);
        createEquipmentTable();
    }

    public ArrayList<Model> getAllEquipment(){
        String query = "SELECT * FROM "+EQUIPMENT_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                Model modelSearch = new Model();
                modelSearch.setId(cursor.getInt(0));
                modelSearch.setEquiment_type(cursor.getString(1));
                modelSearch.setProgram(cursor.getString(2));
                modelSearch.setProduct(cursor.getString(3));
                modelSearch.setStatut(cursor.getString(4));
                modelSearch.setModel(cursor.getString(5));
                modelSearch.setStandard(cursor.getString(6));
                modelSearch.setPn(cursor.getString(7));
                modelSearch.setSn(cursor.getString(8));
                modelSearch.setPnsoft(cursor.getString(9));
                modelSearch.setCms(cursor.getString(10));
                modelSearch.setLocation(cursor.getString(11));
                modelSearch.setDatecrea(cursor.getString(12));
                modelSearch.setDateMaj(cursor.getString(13));
                modelSearch.setComments(cursor.getString(14));
                searchListEquipment.add(modelSearch);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return searchListEquipment;
    }

    public Cursor searchDataBase(String text){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+EQUIPMENT_TABLE_NAME+" where "+ PROGRAM+" like '%"+text+"%'"+" or "+ ID_TYPE+" like '%"+text+"%'"+" or "+ SN+" like '%"+text+"%'"+" or "+ PN+" like '%"+text+"%'"+" or "+ PRODUCT+" like '%"+text+"%'"+" or "+ MODEL+" like '%"+text+"%'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}




