package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Perfil;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PerfilService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilMainActivity extends AppCompatActivity {

    Button btnAddPerfil;
    Button btnGetPerfilesList;
    ListView listViewPerfiles;

    PerfilService perfilService;
    List<Perfil> list = new ArrayList<Perfil>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_main);

        setTitle("Retrofit 2 CRUD Perfil");

        btnAddPerfil = (Button) findViewById(R.id.btnAddPerfil);
        btnGetPerfilesList = (Button) findViewById(R.id.btnGetPerfilesList);
        listViewPerfiles = (ListView) findViewById(R.id.listViewPerfiles);
        perfilService = APIUtils.getPerfilService();

        btnGetPerfilesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPerfilesList();
            }
        });

        btnAddPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilMainActivity.this, PerfilActivity.class);
                intent.putExtra("perfil_tipoperfil", "");
                startActivity(intent);
            }
        });
    }

    public void getPerfilesList(){
        Call<List<Perfil>> call = perfilService.getPerfiles();
        call.enqueue(new Callback<List<Perfil>>() {
            @Override
            public void onResponse(Call<List<Perfil>> call, Response<List<Perfil>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewPerfiles.setAdapter(new PerfilAdapter(PerfilMainActivity.this, R.layout.list_perfil, list));
                }
            }

            @Override
            public void onFailure(Call<List<Perfil>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
