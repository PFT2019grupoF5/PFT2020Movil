package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utec.pft202002.model.Ciudad;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.CiudadService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CiudadMainActivity3 extends AppCompatActivity implements CiudadAdapter3.SelectedCiudad{

    Button btnAddCiudad;
    Button btnGetCiudadesList;
    Button btVolverCiudMain;

    RecyclerView recyclerView;
    CiudadAdapter3 ciudadAdapter3;

    CiudadService ciudadService;
    List<Ciudad> listaCiudades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ciudad_main3);

        setTitle("CRUD Ciudades");

        recyclerView = findViewById(R.id.recyclerViewCiudades);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        btnAddCiudad = (Button) findViewById(R.id.btnAddCiudad);
        btnGetCiudadesList = (Button) findViewById(R.id.btnGetCiudadesList);
        btVolverCiudMain = (Button) findViewById(R.id.btVolverCiudMain);

        ciudadService = APIUtils.getCiudadService();

        ciudadAdapter3 = new CiudadAdapter3(listaCiudades, this);
        recyclerView.setAdapter(ciudadAdapter3);

        btnGetCiudadesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCiudadesList();
            }
        });

        btnAddCiudad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CiudadMainActivity3.this, CiudadActivity.class);
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
                    listaCiudades = response.body();

                    ciudadAdapter3 = new CiudadAdapter3(listaCiudades, CiudadMainActivity3.this);
                    recyclerView.setAdapter(ciudadAdapter3);

                } else  {
                    Toast.makeText(CiudadMainActivity3.this, "getCiudades: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ciudad>> call, Throwable t) {
                Toast.makeText(CiudadMainActivity3.this, "*** No se pudo obtener la lista de Ciudades", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void selectedCiudad(Ciudad ciudad) {

        //start Activity Ciudad Form
        Intent intent = new Intent(CiudadMainActivity3.this, CiudadActivity.class);
        intent.putExtra("ciudad_id", String.valueOf(ciudad.getId()));
        intent.putExtra("ciudad_nombre", String.valueOf(ciudad.getNombre()));
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ciudadAdapter3.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_view){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
