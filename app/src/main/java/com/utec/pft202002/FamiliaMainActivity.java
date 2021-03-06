package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Familia;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.FamiliaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FamiliaMainActivity extends AppCompatActivity {

    Button btnAddFamilia;
    Button btnGetFamiliasList;
    Button btnVolverFamiMain;
    ListView listViewFamilias;

    FamiliaService familiaService;
    List<Familia> list = new ArrayList<Familia>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familia_main);

        setTitle("CRUD Familias");

        btnAddFamilia = (Button) findViewById(R.id.btnAddFamilia);
        btnGetFamiliasList = (Button) findViewById(R.id.btnGetFamiliasList);
        btnVolverFamiMain = (Button) findViewById(R.id.btnVolverFamiMain);
        listViewFamilias = (ListView) findViewById(R.id.listViewFamilias);
        familiaService = APIUtils.getFamiliaService();

        btnGetFamiliasList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFamiliasList();
            }
        });

        btnAddFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamiliaMainActivity.this, FamiliaActivity.class);
                intent.putExtra("familia_nombre", "");
                intent.putExtra("familia_descrip", "");
                intent.putExtra("familia_incompat", "");
                startActivity(intent);
            }
        });
        btnVolverFamiMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getFamiliasList(){
        Call<List<Familia>> call = familiaService.getFamilias();
        call.enqueue(new Callback<List<Familia>>() {
            @Override
            public void onResponse(Call<List<Familia>> call, Response<List<Familia>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewFamilias.setAdapter(new FamiliaAdapter(FamiliaMainActivity.this, R.layout.list_familia2, list));
                } else  {
                    Toast.makeText(FamiliaMainActivity.this, "getFamilias: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Familia>> call, Throwable t) {
                Toast.makeText(FamiliaMainActivity.this, "*** No se pudo obtener lista de  Familias", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
