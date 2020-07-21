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

import com.utec.pft202002.model.EntidadLoc;

import java.util.List;


public class EntidadLocAdapter extends ArrayAdapter<EntidadLoc> {

    private Context context;
    private List<EntidadLoc> entidadesLoc;

    public EntidadLocAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EntidadLoc> objects) {
        super(context, resource, objects);
        this.context = context;
        this.entidadesLoc = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_entidadloc, parent, false);

        TextView txtEntidadLocId = (TextView) rowView.findViewById(R.id.txtEntidadLocId);
        TextView txtEntidadLocCodigo = (TextView) rowView.findViewById(R.id.txtEntidadLocCodigo);
        TextView txtEntidadLocNombre = (TextView) rowView.findViewById(R.id.txtEntidadLocNombre);
        TextView txtEntidadLocDireccion = (TextView) rowView.findViewById(R.id.txtEntidadLocDireccion);
        TextView txtEntidadLocTipoLoc = (TextView) rowView.findViewById(R.id.txtEntidadLocTipoLoc);
        TextView txtEntidadLocCiudadId = (TextView) rowView.findViewById(R.id.txtEntidadLocCiudadId);

        txtEntidadLocId.setText(String.format("#ID: %d", entidadesLoc.get(pos).getId()));
        txtEntidadLocCodigo.setText(String.format("Codigo: %d", entidadesLoc.get(pos).getCodigo()));
        txtEntidadLocNombre.setText(String.format("Nombre: %s", entidadesLoc.get(pos).getNombre()));
        txtEntidadLocDireccion.setText(String.format("Direccion: %s", entidadesLoc.get(pos).getDireccion()));
        txtEntidadLocTipoLoc.setText(String.format("TipoLoc: %s", entidadesLoc.get(pos).getTipoLoc()));
        txtEntidadLocCiudadId.setText(String.format("CiudadId: %d", entidadesLoc.get(pos).getCiudad().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity EntidadLoc Form
                Intent intent = new Intent(context, EntidadLocActivity.class);
                intent.putExtra("entidadloc_id", String.valueOf(entidadesLoc.get(pos).getId()));
                intent.putExtra("entidadloc_codigo", String.valueOf(entidadesLoc.get(pos).getCodigo()));
                intent.putExtra("entidadloc_nombre", String.valueOf(entidadesLoc.get(pos).getNombre()));
                intent.putExtra("entidadloc_direccion", String.valueOf(entidadesLoc.get(pos).getDireccion()));
                intent.putExtra("entidadloc_tipoloc", String.valueOf(entidadesLoc.get(pos).getTipoLoc()));
                intent.putExtra("entidadloc_ciudadid", String.valueOf(entidadesLoc.get(pos).getCiudad().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
