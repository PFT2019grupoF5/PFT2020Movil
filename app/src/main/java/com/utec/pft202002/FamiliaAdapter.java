package com.utec.pft202002;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.utec.pft202002.model.Familia;

import java.util.List;


public class FamiliaAdapter extends ArrayAdapter<Familia> {

    private Context context;
    private List<Familia> familias;

    public FamiliaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Familia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.familias = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_familia2, parent, false);

        //TextView txtFamiliaId = (TextView) rowView.findViewById(R.id.txtFamiliaId);
        TextView txtFamiliaId = (TextView) rowView.findViewById(R.id.txtFamiliaId2);
        //TextView txtFamiliaNombre = (TextView) rowView.findViewById(R.id.txtFamiliaNombre);
        TextView txtFamiliaNombre = (TextView) rowView.findViewById(R.id.txtFamiliaNombre2);
        //TextView txtFamiliaDescrip = (TextView) rowView.findViewById(R.id.txtFamiliaDescrip);
        //TextView txtFamiliaIncompat = (TextView) rowView.findViewById(R.id.txtFamiliaIncompat);


        txtFamiliaId.setText(String.format("#ID: %d", familias.get(pos).getId()));
        txtFamiliaNombre.setText(String.format("%s", familias.get(pos).getNombre()));
       // txtFamiliaDescrip.setText(String.format(" %s", familias.get(pos).getDescrip()));
       // txtFamiliaIncompat.setText(String.format(" %s", familias.get(pos).getIncompat()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Familia Form
                Intent intent = new Intent(context, FamiliaActivity.class);
                intent.putExtra("familia_id", String.valueOf(familias.get(pos).getId()));
                intent.putExtra("familia_nombre", String.valueOf(familias.get(pos).getNombre()));
                intent.putExtra("familia_descrip", String.valueOf(familias.get(pos).getDescrip()));
                intent.putExtra("familia_incompat", String.valueOf(familias.get(pos).getIncompat()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
