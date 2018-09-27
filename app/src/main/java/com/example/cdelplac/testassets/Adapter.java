package com.example.cdelplac.testassets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Model> {

public Adapter (Context context, ArrayList<Model> equipment) {
        super(context, 0, equipment);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
        Model equipment = (Model) getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        TextView program = convertView.findViewById(R.id.tv_program);
        TextView equi_type = convertView.findViewById(R.id.tv_equip_type);
        TextView product = convertView.findViewById(R.id.tv_product);
        TextView model = convertView.findViewById(R.id.tv_model);
        TextView standard = convertView.findViewById(R.id.tv_standard);
        TextView pn = convertView.findViewById(R.id.tv_pn);
        TextView sn = convertView.findViewById(R.id.tv_sn);
        TextView pnsoft = convertView.findViewById(R.id.tv_pnsoft);
        TextView statut = convertView.findViewById(R.id.tv_statut);
        TextView cms = convertView.findViewById(R.id.tv_cms);
        TextView location = convertView.findViewById(R.id.tv_location);


        equi_type.setText(equipment.getEquiment_type());
        program.setText(equipment.getProgram());
        product.setText(equipment.getProduct());
        model.setText(equipment.getModel());
        standard.setText(equipment.getStandard());
        pn.setText(equipment.getPn());
        sn.setText(equipment.getSn());
        pnsoft.setText(equipment.getPnsoft());
        statut.setText(equipment.getStatut());
        cms.setText(equipment.getCms());
        location.setText(equipment.getLocation());

        return convertView;
}
}
