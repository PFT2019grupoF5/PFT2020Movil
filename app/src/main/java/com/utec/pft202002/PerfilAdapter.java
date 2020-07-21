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

import com.utec.pft202002.model.Perfil;

import java.util.List;


public class PerfilAdapter extends ArrayAdapter<Perfil> {

    private Context context;
    private List<Perfil> perfiles;

    public PerfilAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Perfil> objects) {
        super(context, resource, objects);
        this.context = context;
        this.perfiles = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_perfil, parent, false);

        TextView txtPerfilId = (TextView) rowView.findViewById(R.id.txtPerfilId);
        TextView txtPerfilTipoPerfil = (TextView) rowView.findViewById(R.id.txtPerfilTipoPerfil);

        txtPerfilId.setText(String.format("#ID: %d", perfiles.get(pos).getId()));
        txtPerfilTipoPerfil.setText(String.format("TipoPerfil: %s", perfiles.get(pos).getTipoPerfil()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Perfil Form
                Intent intent = new Intent(context, PerfilActivity.class);
                intent.putExtra("perfil_id", String.valueOf(perfiles.get(pos).getId()));
                intent.putExtra("perfil_tipoperfil", String.valueOf(perfiles.get(pos).getTipoPerfil()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
