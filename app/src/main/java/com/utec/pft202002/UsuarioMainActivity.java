package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Usuario;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioMainActivity extends AppCompatActivity {

    Button btnAddUsuario;
    Button btnGetUsuariosList;
    Button btnVolverUsuMain;
    ListView listViewUsuarios;

    UsuarioService usuarioService;
    List<Usuario> list = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);

        setTitle("CRUD Usuarios");

        btnAddUsuario = (Button) findViewById(R.id.btnAddUsuario);
        btnGetUsuariosList = (Button) findViewById(R.id.btnGetUsuariosList);
        btnVolverUsuMain = (Button) findViewById(R.id.btnVolverUsuMain);
        listViewUsuarios = (ListView) findViewById(R.id.listViewUsuarios);
        usuarioService = APIUtils.getUsuarioService();

        btnGetUsuariosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsuariosList();
            }
        });

        btnAddUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsuarioMainActivity.this, UsuarioActivity.class);
                intent.putExtra("usuario_nombre", "");
                intent.putExtra("usuario_apellido", "");
                intent.putExtra("usuario_nomacceso", "");
                intent.putExtra("usuario_contrasena", "");
                intent.putExtra("usuario_correo", "");
                intent.putExtra("usuario_tipoperfil", "");
                startActivity(intent);
            }
        });
        btnVolverUsuMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getUsuariosList(){
        Call<List<Usuario>> call = usuarioService.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewUsuarios.setAdapter(new UsuarioAdapter(UsuarioMainActivity.this, R.layout.list_usuario2, list));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(UsuarioMainActivity.this, "*** No se pudo obtener lista de Usuarios", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
