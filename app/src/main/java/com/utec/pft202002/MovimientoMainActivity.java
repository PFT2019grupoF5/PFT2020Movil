package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.MovimientoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimientoMainActivity extends AppCompatActivity {

    Button btnAddMovimiento;
    Button btnGetMovimientosList;
    ListView listViewMovimientos;
    Button btVolverMovMain;


    MovimientoService movimientoService;
    List<Movimiento> list = new ArrayList<Movimiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_main);

        setTitle("Retrofit 2 CRUD Movimiento");

        btnAddMovimiento = (Button) findViewById(R.id.btnAddMovimiento);
        btnGetMovimientosList = (Button) findViewById(R.id.btnGetMovimientosList);
        listViewMovimientos = (ListView) findViewById(R.id.listViewMovimientos);
        movimientoService = APIUtils.getMovimientoService();
        btVolverMovMain = (Button) findViewById(R.id.btVolverMovMain);

        btnGetMovimientosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovimientosList();
            }
        });

        btnAddMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovimientoMainActivity.this, MovimientoActivity.class);
                intent.putExtra("movimiento_fecha", "");
                intent.putExtra("movimiento_cantidad", "");
                intent.putExtra("movimiento_descripcion", "");
                intent.putExtra("movimiento_costo", "");
                intent.putExtra("movimiento_productoid", "");
                intent.putExtra("movimiento_almacenamientoid", "");
                startActivity(intent);
            }
        });
        btVolverMovMain.setOnClickListener(new View.OnClickListener() {
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
                    list = response.body();
                    listViewMovimientos.setAdapter(new MovimientoAdapter(MovimientoMainActivity.this, R.layout.list_movimiento, list));
                }
            }

            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
