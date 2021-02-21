package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.EntidadLoc;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.EntidadLocService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntidadLocMainActivity extends AppCompatActivity {

    Button btnAddEntidadLoc;
    Button btnGetEntidadesLocList;
    Button btnVolverEntLocMain;
    ListView listViewEntidadesLoc;

    EntidadLocService entidadLocService;
    List<EntidadLoc> list = new ArrayList<EntidadLoc>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entidadloc_main);

        setTitle("CRUD Locales");

        btnAddEntidadLoc = (Button) findViewById(R.id.btnAddEntidadLoc);
        btnGetEntidadesLocList = (Button) findViewById(R.id.btnGetEntidadesLocList);
        btnVolverEntLocMain = (Button) findViewById(R.id.btnVolverEntLocMain);
        listViewEntidadesLoc = (ListView) findViewById(R.id.listViewEntidadesLoc);
        entidadLocService = APIUtils.getEntidadLocService();

        btnGetEntidadesLocList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEntidadesLocList();
            }
        });

        btnAddEntidadLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EntidadLocMainActivity.this, EntidadLocActivity.class);
                intent.putExtra("entidadloc_codigo", "");
                intent.putExtra("entidadloc_nombre", "");
                intent.putExtra("entidadloc_direccion", "");
                intent.putExtra("entidadloc_tipoloc", "");
                intent.putExtra("entidadloc_ciudadid", "");
                startActivity(intent);
            }
        });

        btnVolverEntLocMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getEntidadesLocList(){
        Call<List<EntidadLoc>> call = entidadLocService.getEntidadesLoc();
        call.enqueue(new Callback<List<EntidadLoc>>() {
            @Override
            public void onResponse(Call<List<EntidadLoc>> call, Response<List<EntidadLoc>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewEntidadesLoc.setAdapter(new EntidadLocAdapter(EntidadLocMainActivity.this, R.layout.list_entidadloc2, list));
                } else  {
                    Toast.makeText(EntidadLocMainActivity.this, "getEntidadesLoc: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EntidadLoc>> call, Throwable t) {
                Toast.makeText(EntidadLocMainActivity.this, "*** No se pudo obtener lista de Locales", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
