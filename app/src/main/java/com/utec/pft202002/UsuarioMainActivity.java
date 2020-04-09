package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
    ListView listViewUsuarios;

    UsuarioService usuarioService;
    List<Usuario> list = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_main);

        setTitle("Retrofit 2 CRUD Usuario");

        btnAddUsuario = (Button) findViewById(R.id.btnAddUsuario);
        btnGetUsuariosList = (Button) findViewById(R.id.btnGetUsuariosList);
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
                intent.putExtra("usuario_perfilid", "");
                startActivity(intent);
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
                    listViewUsuarios.setAdapter(new UsuarioAdapter(UsuarioMainActivity.this, R.layout.list_usuario, list));
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
