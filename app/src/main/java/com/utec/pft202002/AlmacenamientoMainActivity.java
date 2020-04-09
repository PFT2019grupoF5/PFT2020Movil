package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.AlmacenamientoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlmacenamientoMainActivity extends AppCompatActivity {

    Button btnAddAlmacenamiento;
    Button btnGetAlmacenamientosList;
    ListView listViewAlmacenamientos;

    AlmacenamientoService almacenamientoService;
    List<Almacenamiento> list = new ArrayList<Almacenamiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_main);

        setTitle("Retrofit 2 CRUD Almacenamiento");

        btnAddAlmacenamiento = (Button) findViewById(R.id.btnAddAlmacenamiento);
        btnGetAlmacenamientosList = (Button) findViewById(R.id.btnGetAlmacenamientosList);
        listViewAlmacenamientos = (ListView) findViewById(R.id.listViewAlmacenamientos);
        almacenamientoService = APIUtils.getAlmacenamientoService();

        btnGetAlmacenamientosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAlmacenamientosList();
            }
        });

        btnAddAlmacenamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlmacenamientoMainActivity.this, AlmacenamientoActivity.class);
                intent.putExtra("almacenamiento_volumen", "");
                intent.putExtra("almacenamiento_nombre", "");
                intent.putExtra("almacenamiento_costoop", "");
                intent.putExtra("almacenamiento_capestiba", "");
                intent.putExtra("almacenamiento_cappeso", "");
                intent.putExtra("almacenamiento_entidadlocid", "");
                startActivity(intent);
            }
        });
    }

    public void getAlmacenamientosList(){
        Call<List<Almacenamiento>> call = almacenamientoService.getAlmacenamientos();
        call.enqueue(new Callback<List<Almacenamiento>>() {
            @Override
            public void onResponse(Call<List<Almacenamiento>> call, Response<List<Almacenamiento>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewAlmacenamientos.setAdapter(new AlmacenamientoAdapter(AlmacenamientoMainActivity.this, R.layout.list_almacenamiento, list));
                }
            }

            @Override
            public void onFailure(Call<List<Almacenamiento>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
