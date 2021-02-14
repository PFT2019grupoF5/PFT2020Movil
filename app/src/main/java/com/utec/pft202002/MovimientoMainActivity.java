package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    Button btnVolverMovMain;
    ListView listViewMovimientos;

    MovimientoService movimientoService;
    List<Movimiento> list = new ArrayList<Movimiento>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento_main);

        setTitle("CRUD Movimientos");

        btnAddMovimiento = (Button) findViewById(R.id.btnAddMovimiento);
        btnGetMovimientosList = (Button) findViewById(R.id.btnGetMovimientosList);
        listViewMovimientos = (ListView) findViewById(R.id.listViewMovimientos);
        btnVolverMovMain = (Button) findViewById(R.id.btnVolverMovMain);

        movimientoService = APIUtils.getMovimientoService();

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
                    list = response.body();
                    listViewMovimientos.setAdapter(new MovimientoAdapter(MovimientoMainActivity.this, R.layout.list_movimiento, list));
                } else  {
                    Toast.makeText(MovimientoMainActivity.this, "getMovimientos: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movimiento>> call, Throwable t) {
                Toast.makeText(MovimientoMainActivity.this, "*** No se pudo obtener lista de  Movimientos", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
