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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.Enum.Segmentacion;
import com.utec.pft202002.model.Producto;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.FamiliaService;
import com.utec.pft202002.remote.UsuarioService;
import com.utec.pft202002.remote.ProductoService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFelab;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFven;
    String dateFelab="", dateFven="";

    ProductoService productoService;
    UsuarioService usuarioService;
    FamiliaService familiaService;

    EditText edtProductoId;
    EditText edtProductoNombre;
    EditText edtProductoLote;
    EditText edtProductoPrecio;
    EditText edtProductoFelab;
    EditText edtProductoFven;
    EditText edtProductoPeso;
    EditText edtProductoVolumen;
    EditText edtProductoEstiba;
    EditText edtProductoStkMin;
    EditText edtProductoStkTotal;
    EditText edtProductoSegmentac;
    EditText edtProductoUsuarioId;
    EditText edtProductoFamiliaId;
    Button btnSave;
    Button btnDel;
    TextView txtProductoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        setTitle("Productos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtProductoId = (TextView) findViewById(R.id.txtProductoId);
        edtProductoId = (EditText) findViewById(R.id.edtProductoId);
        edtProductoNombre = (EditText) findViewById(R.id.edtProductoNombre);
        edtProductoLote = (EditText) findViewById(R.id.edtProductoLote);
        edtProductoPrecio = (EditText) findViewById(R.id.edtProductoPrecio);
        edtProductoFelab = (EditText) findViewById(R.id.edtProductoFelab);
        edtProductoFven = (EditText) findViewById(R.id.edtProductoFven);
        edtProductoPeso = (EditText) findViewById(R.id.edtProductoPeso);
        edtProductoVolumen = (EditText) findViewById(R.id.edtProductoVolumen);
        edtProductoEstiba = (EditText) findViewById(R.id.edtProductoEstiba);
        edtProductoStkMin = (EditText) findViewById(R.id.edtProductoStkMin);
        edtProductoStkTotal = (EditText) findViewById(R.id.edtProductoStkTotal);
        edtProductoSegmentac = (EditText) findViewById(R.id.edtProductoSegmentac);
        edtProductoUsuarioId = (EditText) findViewById(R.id.edtProductoUsuarioId);
        edtProductoFamiliaId = (EditText) findViewById(R.id.edtProductoFamiliaId);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        productoService = APIUtils.getProductoService();

        Bundle extras = getIntent().getExtras();
        final String productoId = extras.getString("producto_id");
        String productoNombre = extras.getString("producto_nombre");
        String productoLote = extras.getString("producto_lote");
        String productoPrecio = extras.getString("producto_precio");
        String productoFelab = extras.getString("producto_felab");
        String productoFven = extras.getString("producto_fven");
        String productoPeso = extras.getString("producto_peso");
        String productoVolumen = extras.getString("producto_volumen");
        String productoEstiba = extras.getString("producto_estiba");
        String productoStkMin = extras.getString("producto_stkmin");
        String productoStkTotal = extras.getString("producto_stktotal");
        String productoSegmentac = extras.getString("producto_segmentac");
        String productoUsuarioId = extras.getString("producto_usuarioid");
        String productoFamiliaId = extras.getString("producto_familiaid");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        edtProductoId.setText(productoId);
        edtProductoNombre.setText(productoNombre);
        edtProductoLote.setText(productoLote);
        edtProductoPrecio.setText(productoPrecio);

        Date hoy = new Date();
        long NproductoFelab = hoy.getTime();
        try {
            NproductoFelab = Long.parseLong(productoFelab);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        final Date felab = new Date(NproductoFelab);
        String Sfelab = sdf.format(felab);
        edtProductoFelab.setText(String.format("Felab: %s", Sfelab));

        long NproductoFven = hoy.getTime();
        try {
            NproductoFven = Long.parseLong(productoFven);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        final Date fven = new Date(NproductoFven);
        String Sfven = sdf.format(fven);
        edtProductoFven.setText(String.format("Fven: %s", Sfven));

        edtProductoPeso.setText(productoPeso);
        edtProductoVolumen.setText(productoVolumen);
        edtProductoEstiba.setText(productoEstiba);
        edtProductoStkMin.setText(productoStkMin);
        edtProductoStkTotal.setText(productoStkTotal);
        edtProductoSegmentac.setText(productoSegmentac);
        edtProductoUsuarioId.setText(productoUsuarioId);
        edtProductoFamiliaId.setText(productoFamiliaId);

        edtProductoFelab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(felab);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProductoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFelab,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        edtProductoFven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(fven);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ProductoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFven,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFelab = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateFelab = year + "-" + month + "-" + dayOfMonth;
                edtProductoFelab.setText(dateFelab);
            }
        };

        mDateSetListenerFven=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateFven = year + "-" + month + "-" + dayOfMonth;
                edtProductoFven.setText(dateFven);
            }
        };


        if(productoId != null && productoId.trim().length() > 0 ){
            edtProductoId.setFocusable(false);
        } else {
            txtProductoId.setVisibility(View.INVISIBLE);
            edtProductoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Producto u = new Producto();

                u.setNombre(edtProductoNombre.getText().toString());
                u.setLote(edtProductoLote.getText().toString());
                u.setPrecio(Double.parseDouble(edtProductoPrecio.getText().toString()));

                try {
                    u.setFelab(dateFelab);
                    Log.i("dateFelab :", dateFelab);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    u.setFven(dateFven);
                    Log.i("dateFven :", dateFven);
                }catch (Exception e){
                    e.printStackTrace();
                }

                u.setPeso(Double.parseDouble(edtProductoPeso.getText().toString()));
                u.setVolumen(Double.parseDouble(edtProductoVolumen.getText().toString()));
                u.setEstiba(Integer.parseInt(edtProductoEstiba.getText().toString()));
                u.setStkMin(Double.parseDouble(edtProductoStkMin.getText().toString()));
                u.setStkTotal(Double.parseDouble(edtProductoStkTotal.getText().toString()));
                u.setSegmentac(Segmentacion.valueOf(edtProductoSegmentac.getText().toString()));

                Long usuarioId = Long.parseLong(edtProductoUsuarioId.getText().toString());
                usuarioService = APIUtils.getUsuarioService();
                try {
                    u.setUsuario(usuarioService.getByIdUsuario(usuarioId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Long familiaId = Long.parseLong(edtProductoFamiliaId.getText().toString());
                familiaService = APIUtils.getFamiliaService();
                try {
                    u.setFamilia(familiaService.getByIdFamilia(familiaId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(productoId != null && productoId.trim().length() > 0){
                    //update producto
                    updateProducto(Long.parseLong(productoId), u);
                } else {
                    //add producto
                    addProducto(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(ProductoActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Producto? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteProducto(Long.parseLong(productoId));
                                Intent intent = new Intent(ProductoActivity.this, ProductoMainActivity.class);
                                startActivity(intent);

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

    }

    public void addProducto(Producto u){
        Call<Producto> call = productoService.addProducto(u);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductoActivity.this, "Producto creado ok!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateProducto(Long id, Producto u){
        Call<Producto> call = productoService.updateProducto(id, u);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductoActivity.this, "Producto modificado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteProducto(Long id){
        Call<Producto> call = productoService.deleteProducto(id);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductoActivity.this, "Producto borrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdProducto(Long id){
        Call<Producto> call = productoService.getByIdProducto(id);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if(response.isSuccessful()){
                    Toast.makeText(ProductoActivity.this, "Producto encontrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
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
