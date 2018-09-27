package com.example.cdelplac.testassets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EquipmentAdapter extends ArrayAdapter<Model> {
    public EquipmentAdapter(Context context, ArrayAdapter<Model> equipment, View convertView){
        super(context, 0, (List<Model>) equipment);
    }
    public EquipmentAdapter (Context context, ArrayList<Model> equipment) {
        super(context, 0, equipment);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_equipment, parent, false);
        }
        TextView model = convertView.findViewById(R.id.tv_model);
        TextView standard = convertView.findViewById(R.id.tv_standard);
        TextView pn = convertView.findViewById(R.id.tv_pn);
        TextView sn = convertView.findViewById(R.id.tv_sn);
        TextView pnsoft = convertView.findViewById(R.id.tv_pnsoft);

        Model equipment = (Model) getItem(position);
        if (equipment != null) {
            model.setText(equipment.getModel());
            standard.setText(equipment.getStandard());
            pn.setText(equipment.getPn());
            sn.setText(equipment.getSn());
            pnsoft.setText(equipment.getPnsoft());
        }

        return convertView;
    }
}
