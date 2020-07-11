package com.utec.pft202002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtBienvenido, txtNomAccesoPerfilID;
    Button btnAlmacenamientos, btnCiudades, btnLocalesEnt, btnFamilias, btnMovimientos, btnPedidos, btnPerfiles, btnProductos, btnUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("PFT Grupo F5");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtBienvenido = (TextView) findViewById(R.id.txtBienvenido);
        txtNomAccesoPerfilID = (TextView) findViewById(R.id.txtNomAccesoPerfilID);
        btnAlmacenamientos = (Button) findViewById(R.id.btnAlmacenamientos);
        btnCiudades = (Button) findViewById(R.id.btnCiudades);
        btnLocalesEnt = (Button) findViewById(R.id.btnLocalesEnt);
        btnFamilias = (Button) findViewById(R.id.btnFamilias);
        btnMovimientos = (Button) findViewById(R.id.btnMovimientos);
        btnPedidos = (Button) findViewById(R.id.btnPedidos);
        btnPerfiles = (Button) findViewById(R.id.btnPerfiles);
        btnProductos = (Button) findViewById(R.id.btnProductos);
        btnUsuarios = (Button) findViewById(R.id.btnUsuarios);

        Bundle extras = getIntent().getExtras();
        String usuarioNombre; // = "Adrian";
        String usuarioApellido; // = "Sigot";
        String usuarioNomAcceso; // = "adrian";
        String usuarioPerfilId; // = "3";  // OJO PERFIL ACA ES STRING !!!!!

        // if (extras != null) {
            usuarioNombre = extras.getString("usuario_nombre");
            usuarioApellido = extras.getString("usuario_apellido");
            usuarioNomAcceso = extras.getString("usuario_nomacceso");
            usuarioPerfilId = extras.getString("usuario_perfilid");
        // }
        txtBienvenido.setText("Bienvenida/o " + usuarioNombre + " " + usuarioApellido );
        txtNomAccesoPerfilID.setText("nomacceso: " + usuarioNomAcceso + " perfil: " + usuarioPerfilId );

        switch (usuarioPerfilId)
        {
            case "3": // Perfil Operario
                btnAlmacenamientos.setVisibility(View.INVISIBLE);
                btnCiudades.setVisibility(View.INVISIBLE);
                btnLocalesEnt.setVisibility(View.INVISIBLE);
                btnFamilias.setVisibility(View.INVISIBLE);
                btnMovimientos.setVisibility(View.VISIBLE); // Si
                btnPedidos.setVisibility(View.VISIBLE); // Si
                btnPerfiles.setVisibility(View.INVISIBLE);
                btnProductos.setVisibility(View.INVISIBLE);
                btnUsuarios.setVisibility(View.INVISIBLE);
                break;
            case "2": // Perfil Supervisor
                btnAlmacenamientos.setVisibility(View.VISIBLE);
                btnCiudades.setVisibility(View.VISIBLE);
                btnLocalesEnt.setVisibility(View.VISIBLE);
                btnFamilias.setVisibility(View.VISIBLE);
                btnMovimientos.setVisibility(View.VISIBLE);
                btnPedidos.setVisibility(View.VISIBLE);
                btnPerfiles.setVisibility(View.INVISIBLE);  // Solo admin ve
                btnProductos.setVisibility(View.VISIBLE);
                btnUsuarios.setVisibility(View.INVISIBLE);  // Solo admin ve
                break;
            default:  // "1" Perfil Administrador -- muestro todos los botones
                break;
        }

    }

        public void menuAlmacenamientos (View view){
            Intent intent = new Intent(this, AlmacenamientoMainActivity.class);
            startActivity(intent);
        }
        public void menuCiudades (View view){
            Intent intent = new Intent(this, CiudadMainActivity.class);
            startActivity(intent);
        }
        public void menuLocalesEnt (View view){
            Intent intent = new Intent(this, EntidadLocMainActivity.class);
            startActivity(intent);
        }
        public void menuFamilias (View view){
            Intent intent = new Intent(this, FamiliaMainActivity.class);
            startActivity(intent);
        }
        public void menuMovimientos (View view){
            Intent intent = new Intent(this, MovimientoMainActivity.class);
            startActivity(intent);
        }
        public void menuPedidos (View view){
            Intent intent = new Intent(this, PedidoMainActivity.class);
            startActivity(intent);
        }
        public void menuPerfiles (View view){
            Intent intent = new Intent(this, PerfilMainActivity.class);
            startActivity(intent);
        }
        public void menuProductos (View view){
            Intent intent = new Intent(this, ProductoMainActivity.class);
            startActivity(intent);
        }
        public void menuUsuarios (View view){
            Intent intent = new Intent(this, UsuarioMainActivity.class);
            startActivity(intent);
        }

}

