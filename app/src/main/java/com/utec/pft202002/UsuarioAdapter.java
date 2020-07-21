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

import com.utec.pft202002.model.Usuario;

import java.util.List;


public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);
        this.context = context;
        this.usuarios = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_usuario, parent, false);

        TextView txtUsuarioId = (TextView) rowView.findViewById(R.id.txtUsuarioId);
        TextView txtUsuarioNombre = (TextView) rowView.findViewById(R.id.txtUsuarioNombre);
        TextView txtUsuarioApellido = (TextView) rowView.findViewById(R.id.txtUsuarioApellido);
        TextView txtUsuarioNomAcceso = (TextView) rowView.findViewById(R.id.txtUsuarioNomAcceso);
        TextView txtUsuarioContrasena = (TextView) rowView.findViewById(R.id.txtUsuarioContrasena);
        TextView txtUsuarioCorreo = (TextView) rowView.findViewById(R.id.txtUsuarioCorreo);
        TextView txtUsuarioPerfilId = (TextView) rowView.findViewById(R.id.txtUsuarioPerfilId);

        txtUsuarioId.setText(String.format("#ID: %d", usuarios.get(pos).getId()));
        txtUsuarioNombre.setText(String.format("Nombre: %s", usuarios.get(pos).getNombre()));
        txtUsuarioApellido.setText(String.format("Apellido: %s", usuarios.get(pos).getApellido()));
        txtUsuarioNomAcceso.setText(String.format("NomAcceso: %s", usuarios.get(pos).getNomAcceso()));
        txtUsuarioContrasena.setText(String.format("Contrasena: %s", usuarios.get(pos).getContrasena()));
        txtUsuarioCorreo.setText(String.format("Correo: %s", usuarios.get(pos).getCorreo()));
        txtUsuarioPerfilId.setText(String.format("PerfilId: %d", usuarios.get(pos).getPerfil().getId()));

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity Usuario Form
                Intent intent = new Intent(context, UsuarioActivity.class);
                intent.putExtra("usuario_id", String.valueOf(usuarios.get(pos).getId()));
                intent.putExtra("usuario_nombre", String.valueOf(usuarios.get(pos).getNombre()));
                intent.putExtra("usuario_apellido", String.valueOf(usuarios.get(pos).getApellido()));
                intent.putExtra("usuario_nomacceso", String.valueOf(usuarios.get(pos).getNomAcceso()));
                intent.putExtra("usuario_contrasena", String.valueOf(usuarios.get(pos).getContrasena()));
                intent.putExtra("usuario_correo", String.valueOf(usuarios.get(pos).getCorreo()));
                intent.putExtra("usuario_perfilid", String.valueOf(usuarios.get(pos).getPerfil().getId()));
                context.startActivity(intent);
            }
        });

        return rowView;
    }
    
}
