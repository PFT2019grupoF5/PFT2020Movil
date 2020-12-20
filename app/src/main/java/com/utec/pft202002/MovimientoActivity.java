package com.utec.pft202002;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.Enum.tipoMovimiento;
import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.model.Producto;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.MovimientoService;
import com.utec.pft202002.remote.AlmacenamientoService;
import com.utec.pft202002.remote.ProductoService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
    EditText edtMovimientoTipoMov;
    EditText edtMovimientoProductoId;
    EditText edtMovimientoAlmacenamientoId;
    TextView txtMovimientoId;
    Spinner  spinnerTipoMovimiento;
    Spinner  spinnerProducto;
    Spinner  spinnerAlmacenamiento;
    Button   btnSave;
    Button   btnDel;
    ArrayList<String> listaProductos;
    HashMap<String,Long> hashProductos;
    ArrayList<String> listaAlmacenamientos;
    HashMap<String,Long> hashAlmacenamientos;
    Button btVolverMovi;


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
        edtMovimientoTipoMov = (EditText) findViewById(R.id.edtMovimientoTipoMov);
        edtMovimientoProductoId = (EditText) findViewById(R.id.edtMovimientoProductoId);
        edtMovimientoAlmacenamientoId = (EditText) findViewById(R.id.edtMovimientoAlmacenamientoId);
        spinnerTipoMovimiento = (Spinner) findViewById(R.id.spinnerTipoMovimiento);
        spinnerProducto = (Spinner) findViewById(R.id.spinnerProducto);
        spinnerAlmacenamiento = (Spinner) findViewById(R.id.spinnerAlmacenamiento);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);
        btVolverMovi = (Button) findViewById(R.id.btVolverMovi);

        movimientoService = APIUtils.getMovimientoService();
        productoService = APIUtils.getProductoService();
        almacenamientoService = APIUtils.getAlmacenamientoService();

        Bundle extras = getIntent().getExtras();
        final String movimientoId = extras.getString("movimiento_id");
        String movimientoFecha = extras.getString("movimiento_fecha");
        String movimientoCantidad = extras.getString("movimiento_cantidad");
        String movimientoDescripcion = extras.getString("movimiento_descripcion");
        String movimientoCosto = extras.getString("movimiento_costo");
        String movimientoTipoMov = extras.getString("movimiento_tipomov");
        String movimientoProductoId = extras.getString("movimiento_productoid");
        String movimientoAlmacenamientoId = extras.getString("movimiento_almacenamientoid");

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

        edtMovimientoId.setText(movimientoId);

        Date hoy = new Date();
        long NmovimientoFecha =  hoy.getTime();;
        try {
            NmovimientoFecha = Long.parseLong(movimientoFecha);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        final Date fecha2 = new Date(NmovimientoFecha);
        String Sfecha2 = sdf.format(fecha2);
        edtMovimientoFecha.setText(String.format("%s", Sfecha2));

        edtMovimientoCantidad.setText(movimientoCantidad);
        edtMovimientoDescripcion.setText(movimientoDescripcion);
        edtMovimientoCosto.setText(movimientoCosto);
        edtMovimientoTipoMov.setText(movimientoTipoMov);
        edtMovimientoProductoId.setText(movimientoProductoId);
        edtMovimientoAlmacenamientoId.setText(movimientoAlmacenamientoId);

        edtMovimientoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(fecha2);
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
                // +1 because January is zero
                month = month + 1;
                fecha = year + "-" + month + "-" + dayOfMonth;
                edtMovimientoFecha.setText(fecha);
            }
        };

        obtenerListasParaSpinnerProductos();
        obtenerListasParaSpinnerAlmacenamientos();

        if(movimientoId != null && movimientoId.trim().length() > 0 ){
            edtMovimientoId.setFocusable(false);
            //Por requerimiento RF007 no se permite modificar Movimientos de tipo Perdida
            if (movimientoTipoMov==tipoMovimiento.valueOf("P").toString()) {
                edtMovimientoFecha.setFocusable(false);
                edtMovimientoCantidad.setFocusable(false);
                edtMovimientoDescripcion.setFocusable(false);
                edtMovimientoCosto.setFocusable(false);
                edtMovimientoTipoMov.setFocusable(false);
                edtMovimientoProductoId.setFocusable(false);
                edtMovimientoAlmacenamientoId.setFocusable(false);
                btnSave.setVisibility(View.INVISIBLE);
            }

        } else {
            txtMovimientoId.setVisibility(View.INVISIBLE);
            edtMovimientoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtMovimientoFecha.getText().toString().equals("")) {
                    edtMovimientoFecha.requestFocus();
                    edtMovimientoFecha.setError("Es necesario ingresar todo los datos requeridos");
                } else if  (edtMovimientoCantidad.getText().toString().equals("")) {
                    edtMovimientoCantidad.requestFocus();
                    edtMovimientoCantidad.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtMovimientoDescripcion.getText().toString().equals("")) {
                    edtMovimientoDescripcion.requestFocus();
                    edtMovimientoDescripcion.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtMovimientoDescripcion.getText().toString().length() > 250) {
                    edtMovimientoDescripcion.requestFocus();
                    edtMovimientoDescripcion.setError("Los datos ingresados superan el largo permitido. Por favor revise sus datos.");
                } else if (edtMovimientoCosto.getText().toString().equals("")) {
                    edtMovimientoCosto.requestFocus();
                    edtMovimientoCosto.setError("Es necesario ingresar todo los datos requeridos");
                } else if (spinnerTipoMovimiento.getSelectedItem().toString().equals("---Por favor seleccione Tipo de Movimiento---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el tipo de movimiento. Gracias", Toast.LENGTH_LONG).show();
                } else if (spinnerProducto.getSelectedItem().toString().equals("---Por favor seleccione Producto---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el Producto. Gracias", Toast.LENGTH_LONG).show();
                } else if (spinnerAlmacenamiento.getSelectedItem().toString().equals("---Por favor seleccione Almacenamiento---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el Almacenamiento. Gracias", Toast.LENGTH_LONG).show();
                } else {

                    Movimiento u = new Movimiento();

                    try {
                        u.setFecha(fecha);
                        Log.i("fecha :", fecha);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    u.setCantidad(Integer.parseInt(edtMovimientoCantidad.getText().toString()));
                    u.setDescripcion(edtMovimientoDescripcion.getText().toString());
                    u.setCosto(Double.parseDouble(edtMovimientoCosto.getText().toString()));

                    edtMovimientoTipoMov.setText((spinnerTipoMovimiento.getSelectedItem().toString()));
                    u.setTipoMov(tipoMovimiento.valueOf(edtMovimientoTipoMov.getText().toString()));

                    edtMovimientoProductoId.setText(Long.toString(hashProductos.get(spinnerProducto.getSelectedItem().toString())));
                    Long productoId = Long.parseLong(edtMovimientoProductoId.getText().toString());
                    try {
                        u.setProducto(productoService.getByIdProducto(productoId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    edtMovimientoAlmacenamientoId.setText(Long.toString(hashAlmacenamientos.get(spinnerAlmacenamiento.getSelectedItem().toString())));
                    Long almacenamientoId = Long.parseLong(edtMovimientoAlmacenamientoId.getText().toString());
                    try {
                        u.setAlmacenamiento(almacenamientoService.getByIdAlmacenamiento(almacenamientoId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (movimientoId != null && movimientoId.trim().length() > 0) {
                        //update movimiento
                        if (validaUpdateMovimiento(u)) {
                            updateMovimiento(Long.parseLong(movimientoId), u);
                        }
                    } else {
                        //add movimiento
                        if (validaAddMovimiento(u)) {
                            addMovimiento(u);
                        }
                    }
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(MovimientoActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Movimiento? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (validaDeleteMovimiento(Long.parseLong(movimientoId))) {
                                    deleteMovimiento(Long.parseLong(movimientoId));
                                    Intent intent = new Intent(MovimientoActivity.this, MovimientoMainActivity.class);
                                    startActivity(intent);
                                }

                            }
                        }).
                        setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

        btVolverMovi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void obtenerListasParaSpinnerProductos(){

        Call<List<Producto>> call = productoService.getProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.isSuccessful()){
                    List<Producto> productoList = response.body();

                    listaProductos = new ArrayList<>();
                    hashProductos = new HashMap<String,Long>();
                    listaProductos.add("---Por favor seleccione Producto---");
                    for (int i=0;i<productoList.size();i++){
                        hashProductos.put(productoList.get(i).getNombre(),productoList.get(i).getId());
                        listaProductos.add(productoList.get(i).getNombre());
                    }
                    ArrayAdapter<String> adapterSpinnerProductos = new ArrayAdapter<String>(MovimientoActivity.this, android.R.layout.simple_spinner_item, listaProductos);
                    spinnerProducto.setAdapter(adapterSpinnerProductos);
                }
            }
            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void obtenerListasParaSpinnerAlmacenamientos(){

        Call<List<Almacenamiento>> call = almacenamientoService.getAlmacenamientos();
        call.enqueue(new Callback<List<Almacenamiento>>() {
            @Override
            public void onResponse(Call<List<Almacenamiento>> call, Response<List<Almacenamiento>> response) {
                if(response.isSuccessful()){
                    List<Almacenamiento> almacenamientoList = response.body();

                    listaAlmacenamientos = new ArrayList<>();
                    hashAlmacenamientos = new HashMap<String,Long>();
                    listaAlmacenamientos.add("---Por favor seleccione Almacenamiento---");
                    for (int i=0;i<almacenamientoList.size();i++){
                        hashAlmacenamientos.put(almacenamientoList.get(i).getNombre(),almacenamientoList.get(i).getId());
                        listaAlmacenamientos.add(almacenamientoList.get(i).getNombre());
                    }
                    ArrayAdapter<String> adapterSpinnerAlmacenamientos = new ArrayAdapter<String>(MovimientoActivity.this, android.R.layout.simple_spinner_item, listaAlmacenamientos);
                    spinnerAlmacenamiento.setAdapter(adapterSpinnerAlmacenamientos);
                }
            }
            @Override
            public void onFailure(Call<List<Almacenamiento>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public boolean validaAddMovimiento(Movimiento movimiento) {
        if (movimiento.getTipoMov() == tipoMovimiento.valueOf("P")) {
            try {
                Long idProductoAsociadoAlMovimiento = movimiento.getProducto().getId();
                Producto productoEnBD = productoService.getByIdProducto(idProductoAsociadoAlMovimiento).execute().body();
                if ((productoEnBD.getStkTotal() < movimiento.getCantidad())) {
                    Toast.makeText(getBaseContext(), "Stock insuficiente de Producto para registrar la Pérdida, por favor revise sus datos.", Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean validaUpdateMovimiento(Movimiento movimiento) {
        //Por requerimiento RF007 no se permite modificar Movimientos de tipo Perdida
        if (movimiento.getTipoMov() == tipoMovimiento.valueOf("P")) {
            return false;
        }
        return true;
    }

    public boolean validaDeleteMovimiento(Long idMovimiento) {
        try {
            Movimiento movimiento = movimientoService.getByIdMovimiento(idMovimiento).execute().body();
            if (movimiento != null) {
                if (movimiento.getTipoMov() == tipoMovimiento.valueOf("P")) {
                    try {
                        Long idAlmacenamientoAsociadoAlMovimiento = movimiento.getAlmacenamiento().getId();
                        Almacenamiento almacenamientoEnBD = almacenamientoService.getByIdAlmacenamiento(idAlmacenamientoAsociadoAlMovimiento).execute().body();
                        Long idProductoAsociadoAlMovimiento = movimiento.getProducto().getId();
                        Producto productoEnBD = productoService.getByIdProducto(idProductoAsociadoAlMovimiento).execute().body();
                        if (almacenamientoEnBD.getVolumen() < (movimiento.getCantidad()*productoEnBD.getVolumen())) {
                            Toast.makeText(getBaseContext(), "Espacio insuficiente en Almacenamiento para reponer producto al eliminar la Pérdida, por favor revise sus datos.", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(getBaseContext(), "No se encontró Movimiento", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



    public void addMovimiento(Movimiento movimiento){
        if (movimiento.getTipoMov() == tipoMovimiento.valueOf("P")) {
            try {
                // Significa que Registro una PERDIDA de un producto en un almacenamiento
                Long idAlmacenamientoAsociadoAlMovimiento = movimiento.getAlmacenamiento().getId();
                Almacenamiento almacenamientoEnBD = almacenamientoService.getByIdAlmacenamiento(idAlmacenamientoAsociadoAlMovimiento).execute().body();
                Long idProductoAsociadoAlMovimiento = movimiento.getProducto().getId();
                Producto productoEnBD = productoService.getByIdProducto(idProductoAsociadoAlMovimiento).execute().body();
                // descuenta stock del producto
                productoEnBD.setStkTotal(productoEnBD.getStkTotal() - movimiento.getCantidad());
                // controla si es necesario iniciar pedido de reposicion
                if (productoEnBD.getStkTotal() <= productoEnBD.getStkMin()) {
                    Toast.makeText(getBaseContext(), "Stock por debajo del mínimo requerido para este producto, por favor gestione un Pedido de reposición.", Toast.LENGTH_LONG).show();
                }
                // disponibiliza espacio en el almacenamiento correspondiente a la perdida ocasionada
                almacenamientoEnBD.setVolumen((int) (almacenamientoEnBD.getVolumen() + (movimiento.getCantidad()*productoEnBD.getVolumen())));
                // actualiza BD
                Almacenamiento almacenamientoActualizadoEnBD = almacenamientoService.updateAlmacenamiento(idAlmacenamientoAsociadoAlMovimiento, almacenamientoEnBD).execute().body();
                Producto productoActualizadoEnBD = productoService.updateProducto(idProductoAsociadoAlMovimiento, productoEnBD).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Call<Movimiento> call = movimientoService.addMovimiento(movimiento);
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

    public void deleteMovimiento(Long idMovimiento){
        try {
            // Significa que ELIMINO un registro de una PERDIDA previamente ingresada de un producto en un almacenamiento
            Movimiento movimiento = movimientoService.getByIdMovimiento(idMovimiento).execute().body();
            if (movimiento != null) {
                if (movimiento.getTipoMov() == tipoMovimiento.valueOf("P")) {
                    try {
                        Long idAlmacenamientoAsociadoAlMovimiento = movimiento.getAlmacenamiento().getId();
                        Almacenamiento almacenamientoEnBD = almacenamientoService.getByIdAlmacenamiento(idAlmacenamientoAsociadoAlMovimiento).execute().body();
                        Long idProductoAsociadoAlMovimiento = movimiento.getProducto().getId();
                        Producto productoEnBD = productoService.getByIdProducto(idProductoAsociadoAlMovimiento).execute().body();
                        //repone stock del producto
                        productoEnBD.setStkTotal(productoEnBD.getStkTotal() + movimiento.getCantidad());
                        // vuelve a ocupar espacio en el almacenamiento
                        almacenamientoEnBD.setVolumen((int) (almacenamientoEnBD.getVolumen() - (movimiento.getCantidad()*productoEnBD.getVolumen())));
                        // actualiza BD
                        Almacenamiento almacenamientoActualizadoEnBD = almacenamientoService.updateAlmacenamiento(idAlmacenamientoAsociadoAlMovimiento, almacenamientoEnBD).execute().body();
                        Producto productoActualizadoEnBD = productoService.updateProducto(idProductoAsociadoAlMovimiento, productoEnBD).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(getBaseContext(), "No se encontró Movimiento", Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call<Movimiento> call = movimientoService.deleteMovimiento(idMovimiento);
        call.enqueue(new Callback<Movimiento>() {
            @Override
            public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MovimientoActivity.this, "Movimiento borrado ok!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovimientoActivity.this, "No fue posible borrar el Movimiento. Verifique si está en otro registro.", Toast.LENGTH_LONG).show();
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
