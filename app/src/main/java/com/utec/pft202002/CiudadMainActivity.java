package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Ciudad;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.CiudadService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CiudadMainActivity extends AppCompatActivity {

    Button btnAddCiudad;
    Button btnGetCiudadesList;
    Button btVolverCiudMain;
    ListView listViewCiudades;

    CiudadService ciudadService;
    List<Ciudad> list = new ArrayList<Ciudad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad_main);

        setTitle("CRUD Ciudades");

        btnAddCiudad = (Button) findViewById(R.id.btnAddCiudad);
        btnGetCiudadesList = (Button) findViewById(R.id.btnGetCiudadesList);
        btVolverCiudMain = (Button) findViewById(R.id.btVolverCiudMain);
        listViewCiudades = (ListView) findViewById(R.id.listViewCiudades);
        ciudadService = APIUtils.getCiudadService();

        btnGetCiudadesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCiudadesList();
            }
        });

        btnAddCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CiudadMainActivity.this, CiudadActivity.class);
                intent.putExtra("ciudad_nombre", "");
                startActivity(intent);
            }
        });
        btVolverCiudMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getCiudadesList(){
        Call<List<Ciudad>> call = ciudadService.getCiudades();
        call.enqueue(new Callback<List<Ciudad>>() {
            @Override
            public void onResponse(Call<List<Ciudad>> call, Response<List<Ciudad>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewCiudades.setAdapter(new CiudadAdapter(CiudadMainActivity.this, R.layout.list_ciudad2, list));
                } else  {
                    Toast.makeText(CiudadMainActivity.this, "getCiudades: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ciudad>> call, Throwable t) {
                Toast.makeText(CiudadMainActivity.this, "*** No se pudo obtener lista de Ciudades", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
