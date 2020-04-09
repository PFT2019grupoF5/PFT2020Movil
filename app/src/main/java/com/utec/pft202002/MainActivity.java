package com.utec.pft202002;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("PFT Grupo F5");

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
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

