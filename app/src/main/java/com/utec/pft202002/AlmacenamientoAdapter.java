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

import com.utec.pft202002.model.Almacenamiento;

import java.util.List;


public class AlmacenamientoAdapter extends ArrayAdapter<Almacenamiento> {

    private Context context;
    private List<Almacenamiento> almacenamientos;

    public AlmacenamientoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Almacenamiento> objects) {
        super(context, resource, objects);
        this.context = context;
        this.almacenamientos = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_almacenamiento2, parent, false);

        TextView txtAlmacenamientoId = (TextView) rowView.findViewById(R.id.txtAlmacenamientoId2);
        TextView txtAlmacenamientoNombre = (TextView) rowView.findViewById(R.id.txtAlmacenamientoNombre2);
//        TextView txtAlmacenamientoCostoOp = (TextView) rowView.findViewById(R.id.txtAlmacenamientoCostoOp);
//        TextView txtAlmacenamientoCapEstiba = (TextView) rowView.findViewById(R.id.txtAlmacenamientoCapEstiba);
//        TextView txtAlmacenamientoCapPeso = (TextView) rowView.findViewById(R.id.txtAlmacenamientoCapPeso);
//        TextView txtAlmacenamientoVolumen = (TextView) rowView.findViewById(R.id.txtAlmacenamientoVolumen);
//        TextView txtAlmacenamientoEntidadLocId = (TextView) rowView.findViewById(R.id.txtAlmacenamientoEntidadLocId);

        txtAlmacenamientoId.setText(String.format("#ID: %d", almacenamientos.get(pos).getId()));
        txtAlmacenamientoNombre.setText(String.format("%s", almacenamientos.get(pos).getNombre()));
//        txtAlmacenamientoCostoOp.setText(String.format("Costo Op: %f", almacenamientos.get(pos).getCostoop()));
//        txtAlmacenamientoCapEstiba.setText(String.format("Cap Estiba: %f", almacenamientos.get(pos).getCapestiba()));
//        txtAlmacenamientoCapPeso.setText(String.format("Cap Peso: %f", almacenamientos.get(pos).getCappeso()));
//        txtAlmacenamientoVolumen.setText(String.format("Volumen: %d", almacenamientos.get(pos).getVolumen()));
//        txtAlmacenamientoEntidadLocId.setText(String.format("Ent Loc: %d", almacenamientos.get(pos).getEntidadLoc().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Almacenamiento Form
                Intent intent = new Intent(context, AlmacenamientoActivity.class);
                intent.putExtra("almacenamiento_id", String.valueOf(almacenamientos.get(pos).getId()));
                intent.putExtra("almacenamiento_nombre", String.valueOf(almacenamientos.get(pos).getNombre()));
                intent.putExtra("almacenamiento_costoop", String.valueOf(almacenamientos.get(pos).getCostoop()));
                intent.putExtra("almacenamiento_capestiba", String.valueOf(almacenamientos.get(pos).getCapestiba()));
                intent.putExtra("almacenamiento_cappeso", String.valueOf(almacenamientos.get(pos).getCappeso()));
                intent.putExtra("almacenamiento_volumen", String.valueOf(almacenamientos.get(pos).getVolumen()));
                intent.putExtra("almacenamiento_entidadlocid", String.valueOf(almacenamientos.get(pos).getEntidadLoc().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }

}
