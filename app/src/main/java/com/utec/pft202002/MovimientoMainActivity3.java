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

import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.MovimientoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimientoMainActivity3 extends AppCompatActivity implements MovimientoAdapter3.SelectedMovimiento{

    Button btnAddMovimiento;
    Button btnGetMovimientosList;
    Button btnVolverMovMain;

    RecyclerView recyclerView;
    MovimientoAdapter3 movimientoAdapter3;

    MovimientoService movimientoService;
    List<Movimiento> listaMovimientos = new ArrayList<Movimiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_main3);

        setTitle("CRUD Movimientos");

        recyclerView = findViewById(R.id.recyclerViewMovimientos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        btnAddMovimiento = (Button) findViewById(R.id.btnAddMovimiento);
        btnGetMovimientosList = (Button) findViewById(R.id.btnGetMovimientosList);
        btnVolverMovMain = (Button) findViewById(R.id.btnVolverMovMain);

        movimientoService = APIUtils.getMovimientoService();

        movimientoAdapter3 = new MovimientoAdapter3(listaMovimientos, this);
        recyclerView.setAdapter(movimientoAdapter3);

        btnGetMovimientosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovimientosList();
            }
        });

        btnAddMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovimientoMainActivity3.this, MovimientoActivity.class);
                intent.putExtra("movimiento_fecha", "");
                intent.putExtra("movimiento_cantidad", "");
                intent.putExtra("movimiento_descripcion", "");
                intent.putExtra("movimiento_costo", "");
                intent.putExtra("movimiento_productoid", "");
                intent.putExtra("movimiento_almacenamientoid", "");
                startActivity(intent);
            }
        });

        btnVolverMovMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getMovimientosList(){
        Call<List<Movimiento>> call = movimientoService.getMovimientos();
        call.enqueue(new Callback<List<Movimiento>>() {
            @Override
            public void onResponse(Call<List<Movimiento>> call, Response<List<Movimiento>> response) {
                if(response.isSuccessful()){
                    listaMovimientos = response.body();

                    movimientoAdapter3 = new MovimientoAdapter3(listaMovimientos, MovimientoMainActivity3.this);
                    recyclerView.setAdapter(movimientoAdapter3);


                } else  {
                    Toast.makeText(MovimientoMainActivity3.this, "getMovimientos: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Toast.makeText(MovimientoMainActivity3.this, "*** No se pudo obtener lista de  Movimientos", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void selectedMovimiento(Movimiento movimiento) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        //start Activity Movimiento Form
        Intent intent = new Intent(MovimientoMainActivity3.this, MovimientoActivity.class);
        intent.putExtra("movimiento_id", String.valueOf(movimiento.getId()));
        intent.putExtra("movimiento_fecha", String.valueOf(movimiento.getFecha()));
        intent.putExtra("movimiento_cantidad", String.valueOf(movimiento.getCantidad()));
        intent.putExtra("movimiento_descripcion", String.valueOf(movimiento.getDescripcion()));
        intent.putExtra("movimiento_costo", String.valueOf(movimiento.getCosto()));
        intent.putExtra("movimiento_tipomov", String.valueOf(movimiento.getTipoMov()));
        intent.putExtra("movimiento_productoid", String.valueOf(movimiento.getProducto().getId()));
        intent.putExtra("movimiento_almacenamientoid", String.valueOf(movimiento.getAlmacenamiento().getId()));
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

                movimientoAdapter3.getFilter().filter(newText);
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
