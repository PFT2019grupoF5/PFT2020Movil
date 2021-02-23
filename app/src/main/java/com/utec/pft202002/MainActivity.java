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
    Button btnAlmacenamientos, btnCiudades, btnLocalesEnt, btnFamilias, btnMovimientos, btnPedidos, btnProductos, btnUsuarios, btnReportev1, btnReportev2, btnLogout;

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
        btnProductos = (Button) findViewById(R.id.btnProductos);
        btnUsuarios = (Button) findViewById(R.id.btnUsuarios);
        btnReportev1 = (Button) findViewById(R.id.btnReportev1);
        btnReportev2 = (Button) findViewById(R.id.btnReportev2);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Bundle extras = getIntent().getExtras();
        String usuarioNombre;
        String usuarioApellido;
        String usuarioNomAcceso;
        String usuarioTipoPerfil;

        usuarioNombre = extras.getString("usuario_nombre");
        usuarioApellido = extras.getString("usuario_apellido");
        usuarioNomAcceso = extras.getString("usuario_nomacceso");
        usuarioTipoPerfil = extras.getString("usuario_tipoperfil");

        txtBienvenido.setText("Bienvenida/o " + usuarioNombre + " " + usuarioApellido );
        txtNomAccesoPerfilID.setText("nomacceso: " + usuarioNomAcceso + " perfil: " + usuarioTipoPerfil );

        switch (usuarioTipoPerfil)
        {
            case "OPERARIO": // Perfil Operario
                btnAlmacenamientos.setVisibility(View.INVISIBLE);
                btnCiudades.setVisibility(View.INVISIBLE);
                btnLocalesEnt.setVisibility(View.INVISIBLE);
                btnFamilias.setVisibility(View.INVISIBLE);
                btnMovimientos.setVisibility(View.VISIBLE); // Si
                btnPedidos.setVisibility(View.VISIBLE); // Si
                btnProductos.setVisibility(View.INVISIBLE);
                btnUsuarios.setVisibility(View.INVISIBLE);
                btnReportev1.setVisibility(View.VISIBLE); // Si
                btnReportev2.setVisibility(View.VISIBLE); // Si
                btnLogout.setVisibility(View.VISIBLE); // Si
                break;
            case "SUPERVISOR": // Perfil Supervisor
                btnAlmacenamientos.setVisibility(View.VISIBLE);
                btnCiudades.setVisibility(View.VISIBLE);
                btnLocalesEnt.setVisibility(View.VISIBLE);
                btnFamilias.setVisibility(View.VISIBLE);
                btnMovimientos.setVisibility(View.VISIBLE);
                btnPedidos.setVisibility(View.VISIBLE);
                btnProductos.setVisibility(View.VISIBLE);
                btnUsuarios.setVisibility(View.INVISIBLE);  // Solo admin ve
                btnReportev1.setVisibility(View.VISIBLE); // Si
                btnReportev2.setVisibility(View.VISIBLE); // Si
                btnLogout.setVisibility(View.VISIBLE); // Si
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
//            Intent intent = new Intent(this, CiudadMainActivity.class);
            Intent intent = new Intent(this, CiudadMainActivity3.class);
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
        public void menuProductos (View view){
            Intent intent = new Intent(this, ProductoMainActivity.class);
            startActivity(intent);
        }
        public void menuUsuarios (View view){
            Intent intent = new Intent(this, UsuarioMainActivity.class);
            startActivity(intent);
        }
        public void menuReportev1(View view){
            Intent intent = new Intent(this, ReporteMainActivity.class);
            startActivity(intent);
        }
        public void menuReportev2 (View view){
            Intent intent = new Intent(this, Reporte2MainActivity.class);
            startActivity(intent);
        }
        public void menuLogout (View view){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

}

