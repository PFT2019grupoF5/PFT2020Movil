package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.model.RenglonReporte;
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
    Button btnVolverAlmacMain;
    ListView listViewAlmacenamientos;

    AlmacenamientoService almacenamientoService;
    List<Almacenamiento> list = new ArrayList<Almacenamiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacenamiento_main);

        setTitle("CRUD Almacenamientos");

        btnAddAlmacenamiento = (Button) findViewById(R.id.btnAddAlmacenamiento);
        btnGetAlmacenamientosList = (Button) findViewById(R.id.btnGetAlmacenamientosList);
        btnVolverAlmacMain = (Button) findViewById(R.id.btVolverAlmacMain);
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
                intent.putExtra("almacenamiento_nombre", "");
                intent.putExtra("almacenamiento_costoop", "");
                intent.putExtra("almacenamiento_capestiba", "");
                intent.putExtra("almacenamiento_cappeso", "");
                intent.putExtra("almacenamiento_volumen", "");
                intent.putExtra("almacenamiento_entidadlocid", "");
                startActivity(intent);
            }
        });

        btnVolverAlmacMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                } else  {
                    Toast.makeText(AlmacenamientoMainActivity.this, "getAlmacenamientos: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Almacenamiento>> call, Throwable t) {
                Toast.makeText(AlmacenamientoMainActivity.this, "*** No se pudo obtener lista de  Almacenamientos", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
