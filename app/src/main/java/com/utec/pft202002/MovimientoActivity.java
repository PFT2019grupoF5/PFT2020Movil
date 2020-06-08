package com.utec.pft202002;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.MovimientoService;
import com.utec.pft202002.remote.AlmacenamientoService;
import com.utec.pft202002.remote.ProductoService;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovimientoActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFecha;
    String fecha="";

    MovimientoService movimientoService;
    ProductoService productoService;
    AlmacenamientoService almacenamientoService;

    EditText edtMovimientoId;
    EditText edtMovimientoFecha;
    EditText edtMovimientoCantidad;
    EditText edtMovimientoDescripcion;
    EditText edtMovimientoCosto;
    EditText edtMovimientoProductoId;
    EditText edtMovimientoAlmacenamientoId;
    Button btnSave;
    Button btnDel;
    TextView txtMovimientoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);

        setTitle("Movimientos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtMovimientoId = (TextView) findViewById(R.id.txtMovimientoId);
        edtMovimientoId = (EditText) findViewById(R.id.edtMovimientoId);
        edtMovimientoFecha = (EditText) findViewById(R.id.edtMovimientoFecha);
        edtMovimientoCantidad = (EditText) findViewById(R.id.edtMovimientoCantidad);
        edtMovimientoDescripcion = (EditText) findViewById(R.id.edtMovimientoDescripcion);
        edtMovimientoCosto = (EditText) findViewById(R.id.edtMovimientoCosto);
        edtMovimientoProductoId = (EditText) findViewById(R.id.edtMovimientoProductoId);
        edtMovimientoAlmacenamientoId = (EditText) findViewById(R.id.edtMovimientoAlmacenamientoId);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        movimientoService = APIUtils.getMovimientoService();

        Bundle extras = getIntent().getExtras();
        final String movimientoId = extras.getString("movimiento_id");
        String movimientoFecha = extras.getString("movimiento_fecha");
        String movimientoCantidad = extras.getString("movimiento_cantidad");
        String movimientoDescripcion = extras.getString("movimiento_descripcion");
        String movimientoCosto = extras.getString("movimiento_costo");
        String movimientoProductoId = extras.getString("movimiento_productoid");
        String movimientoAlmacenamientoId = extras.getString("movimiento_almacenamientoid");

        edtMovimientoId.setText(movimientoId);
        edtMovimientoFecha.setText(movimientoFecha);
        edtMovimientoCantidad.setText(movimientoCantidad);
        edtMovimientoDescripcion.setText(movimientoDescripcion);
        edtMovimientoCosto.setText(movimientoCosto);
        edtMovimientoProductoId.setText(movimientoProductoId);
        edtMovimientoAlmacenamientoId.setText(movimientoAlmacenamientoId);

        edtMovimientoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MovimientoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFecha,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                fecha = year + "-" + month + "-" + dayOfMonth;
                edtMovimientoFecha.setText(fecha);
            }
        };


        if(movimientoId != null && movimientoId.trim().length() > 0 ){
            edtMovimientoId.setFocusable(false);
        } else {
            txtMovimientoId.setVisibility(View.INVISIBLE);
            edtMovimientoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movimiento u = new Movimiento();

                try {
                    u.setFecha(fecha);
                    Log.i("fecha :", fecha);
                }catch (Exception e){
                    e.printStackTrace();
                }

                u.setCantidad(Integer.parseInt(edtMovimientoCantidad.getText().toString()));
                u.setDescripcion(edtMovimientoDescripcion.getText().toString());
                u.setCosto(Double.parseDouble(edtMovimientoCosto.getText().toString()));

                Long productoId = Long.parseLong(edtMovimientoProductoId.getText().toString());
                productoService = APIUtils.getProductoService();
                try {
                    u.setProducto(productoService.getByIdProducto(productoId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Long almacenamientoId = Long.parseLong(edtMovimientoAlmacenamientoId.getText().toString());
                almacenamientoService = APIUtils.getAlmacenamientoService();
                try {
                    u.setAlmacenamiento(almacenamientoService.getByIdAlmacenamiento(almacenamientoId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(movimientoId != null && movimientoId.trim().length() > 0){
                    //update movimiento
                    updateMovimiento(Long.parseLong(movimientoId), u);
                } else {
                    //add movimiento
                    addMovimiento(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMovimiento(Long.parseLong(movimientoId));

                Intent intent = new Intent(MovimientoActivity.this, MovimientoMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void addMovimiento(Movimiento u){
        Call<Movimiento> call = movimientoService.addMovimiento(u);
        call.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MovimientoActivity.this, "Movimiento creado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movimiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateMovimiento(Long id, Movimiento u){
        Call<Movimiento> call = movimientoService.updateMovimiento(id, u);
        call.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MovimientoActivity.this, "Movimiento modificado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movimiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteMovimiento(Long id){
        Call<Movimiento> call = movimientoService.deleteMovimiento(id);
        call.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MovimientoActivity.this, "Movimiento borrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movimiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdMovimiento(Long id){
        Call<Movimiento> call = movimientoService.getByIdMovimiento(id);
        call.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MovimientoActivity.this, "Movimiento encontrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movimiento> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
