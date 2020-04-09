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

import com.utec.pft202002.model.Ciudad;

import java.util.List;


public class CiudadAdapter extends ArrayAdapter<Ciudad> {

    private Context context;
    private List<Ciudad> ciudades;

    public CiudadAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Ciudad> objects) {
        super(context, resource, objects);
        this.context = context;
        this.ciudades = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_ciudad, parent, false);

        TextView txtCiudadId = (TextView) rowView.findViewById(R.id.txtCiudadId);
        TextView txtCiudadNombre = (TextView) rowView.findViewById(R.id.txtCiudadNombre);

        txtCiudadId.setText(String.format("#ID: %d", ciudades.get(pos).getId()));
        txtCiudadNombre.setText(String.format("CIUDAD NOMBRE: %s", ciudades.get(pos).getNombre()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Ciudad Form
                Intent intent = new Intent(context, CiudadActivity.class);
                intent.putExtra("ciudad_id", String.valueOf(ciudades.get(pos).getId()));
                intent.putExtra("ciudad_nombre", ciudades.get(pos).getNombre());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
