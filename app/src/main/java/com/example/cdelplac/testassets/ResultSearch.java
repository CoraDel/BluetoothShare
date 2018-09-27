package com.example.cdelplac.testassets;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


public class ResultSearch extends AppCompatActivity {
    public static final String EQUIPMENT_TABLE_NAME = "Equipment_tableTest";
    //Constants rows

    private static final String ID_TYPE = "id_type";
    private static final String PROGRAM = "program";
    private static final String PRODUCT = "product";
    public static String EXTRA_ID = "id_equipment";
    private Model search;
    private ArrayList<Model> equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search);
        DatabaseHelper db = new DatabaseHelper(this);

        TextView tvTag1 = findViewById(R.id.tag1);
        TextView tvTag2 = findViewById(R.id.tag2);
        TextView tvTag3 = findViewById(R.id.tag3);

        Intent b = getIntent();
        String equipmentType = (String)b.getStringExtra("radioButtonEquipment");
        String program = (String)b.getStringExtra("radioButtonProgram");
        String spinnerProduct = (String)b.getStringExtra("spinnerValue");

        tvTag1.setText(equipmentType);
        tvTag2.setText(program);
        tvTag3.setText(spinnerProduct);

        final ListView equipmentlist = findViewById(R.id.listEquipment);
        equipment = new ArrayList<>();
        Model title = new Model("model", "standard", "pn", "sn", "pnsoft");
        final EquipmentAdapter adapter = new EquipmentAdapter(this, equipment);
        equipment.add(title);

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        if(equipmentType.equals("VHF") && program.equals("SA/LR") ||
                equipmentType.equals("HF")&& program.equals("SA/LR") ){
            equipmentType = "VHF-HF";
            program ="SA-LR";
        }

        Cursor cursor2 = sqLiteDatabase.rawQuery("select * from " +EQUIPMENT_TABLE_NAME +" where "
                + ID_TYPE + " = '"  + equipmentType+"'"+" and " + PROGRAM + " = '"  + program+"'"+" and " + PRODUCT + " = '" + spinnerProduct+"'",null);

        if(!cursor2.moveToFirst()){
            TextView noFound = findViewById(R.id.tv_no_found);
            noFound.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("No results");
            String[] choice = {"List of equipments", "Add an equipment"};
            builder.setItems(choice, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentList = new Intent(ResultSearch.this, MainActivity.class);
                    Intent intentAdd = new Intent(ResultSearch.this, AddEquipement.class);

                    switch (which) {
                        case 0: startActivity(intentList);
                        break;
                        case 1: startActivity(intentAdd);
                        break;
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        else if(cursor2.moveToFirst()){
            do {
                search = new Model();
                //mModel.setId(cursor2.getColumnIndex(ID_EQUIPMENT));
                search.setId(cursor2.getInt(0));
                search.setModel(cursor2.getString(5));
                search.setStandard(cursor2.getString(6));
                search.setPn(cursor2.getString(7));
                search.setSn(cursor2.getString(8));
                search.setPnsoft(cursor2.getString(9));

                equipment.add(search);
            }  while (cursor2.moveToNext());
        }

        equipmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position>0){
                    Model model = equipment.get(position);
                    Intent intent1Details = new Intent(ResultSearch.this, DetailsEquipment.class);
                    intent1Details.putExtra(EXTRA_ID, String.valueOf(model.getId()));
                    startActivity(intent1Details);
                }
            }
        });
        equipmentlist.setAdapter(adapter);

    }

}
