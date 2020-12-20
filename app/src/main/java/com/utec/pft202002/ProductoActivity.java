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

import com.utec.pft202002.Enum.Segmentacion;
import com.utec.pft202002.model.Familia;
import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.model.Producto;
import com.utec.pft202002.model.Usuario;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.FamiliaService;
import com.utec.pft202002.remote.MovimientoService;
import com.utec.pft202002.remote.UsuarioService;
import com.utec.pft202002.remote.ProductoService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFelab;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFven;
    String dateFelab = "", dateFven = "";

    ProductoService productoService;
    UsuarioService usuarioService;
    FamiliaService familiaService;
    MovimientoService movimientoService;

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
    TextView txtProductoId;
    Spinner spinnerSegmentacion;
    Spinner spinnerUsuario;
    Spinner spinnerFamilia;
    Button btnSave;
    Button btnDel;
    Button btVolverProduc;
    ArrayList<String> listaUsuarios;
    HashMap<String, Long> hashUsuarios;
    ArrayList<String> listaFamilias;
    HashMap<String, Long> hashFamilias;



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
        spinnerSegmentacion = (Spinner) findViewById(R.id.spinnerSegmentacion);
        spinnerUsuario = (Spinner) findViewById(R.id.spinnerUsuarioProd);
        spinnerFamilia = (Spinner) findViewById(R.id.spinnerFamilia);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);
        btVolverProduc = (Button) findViewById(R.id.btVolverProduc);

        productoService = APIUtils.getProductoService();
        usuarioService = APIUtils.getUsuarioService();
        familiaService = APIUtils.getFamiliaService();
        movimientoService = APIUtils.getMovimientoService();

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

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

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
        edtProductoFelab.setText(String.format("%s", Sfelab));

        long NproductoFven = hoy.getTime();
        try {
            NproductoFven = Long.parseLong(productoFven);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        final Date fven = new Date(NproductoFven);
        String Sfven = sdf.format(fven);
        edtProductoFven.setText(String.format("%s", Sfven));

        edtProductoPeso.setText(productoPeso);
        edtProductoVolumen.setText(productoVolumen);
        edtProductoEstiba.setText(productoEstiba);
        edtProductoStkMin.setText(productoStkMin);
        edtProductoStkTotal.setText(productoStkTotal);
        edtProductoSegmentac.setText(productoSegmentac);
        edtProductoUsuarioId.setText(productoUsuarioId);
        edtProductoFamiliaId.setText(productoFamiliaId);

/*
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
                        year, month, day);
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
                        year, month, day);
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

        mDateSetListenerFven = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateFven = year + "-" + month + "-" + dayOfMonth;
                edtProductoFven.setText(dateFven);
            }
        };
*/
        obtenerListasParaSpinnerUsuarios();
        obtenerListasParaSpinnerFamilias();

        if (productoId != null && productoId.trim().length() > 0) {
            // Validaciones MODIFICACION
            // No se permitira cambiar el Codigo o la Descripcion
            edtProductoId.setFocusable(false);
            edtProductoNombre.setFocusable(false);
        } else {
            txtProductoId.setVisibility(View.INVISIBLE);
            edtProductoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateValidator validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");

                if (edtProductoNombre.getText().toString().trim().equals("")) {
                    edtProductoNombre.requestFocus();
                    edtProductoNombre.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoNombre.getText().toString().length() > 50) {
                    System.out.println("edtProductoNombre : " + edtProductoNombre.toString());
                    edtProductoNombre.requestFocus();
                    edtProductoNombre.setError("Los datos ingresados superan el largo permitido. Por favor revise sus datos.");
                } else if (edtProductoLote.getText().toString().trim().equals("")) {
                    edtProductoLote.requestFocus();
                    edtProductoLote.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoPrecio.getText().toString().trim().equals("")) {
                    edtProductoPrecio.requestFocus();
                    edtProductoPrecio.setError("Es necesario ingresar todo los datos requeridos");
                } else if (!validator.isValid(edtProductoFelab.getText().toString())) {
                    edtProductoFelab.requestFocus();
                    edtProductoFelab.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtProductoFelab.getText().toString());
                    System.out.println("EN EL IF ::: edtProductoFelab: " + edtProductoFelab + "dateFelab: " + dateFelab);
                } else if (edtProductoFelab.getText().toString().trim().equals("")) {
                    edtProductoFelab.requestFocus();
                    edtProductoFelab.setError("Es necesario ingresar todo los datos requeridos");
                } else if (!validator.isValid(edtProductoFven.getText().toString())) {
                    edtProductoFven.requestFocus();
                    edtProductoFven.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtProductoFven.getText().toString());
                    System.out.println("EN EL IF ::: edtProductoFven: " + edtProductoFven + "dateFven: " + dateFven);
                } else if (edtProductoFven.getText().toString().trim().equals("")) {
                    edtProductoFven.requestFocus();
                    edtProductoFven.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoPeso.getText().toString().trim().equals("")) {
                    edtProductoPeso.requestFocus();
                    edtProductoPeso.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoVolumen.getText().toString().trim().equals("")) {
                    edtProductoVolumen.requestFocus();
                    edtProductoVolumen.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoEstiba.getText().toString().trim().equals("")) {
                    edtProductoEstiba.requestFocus();
                    edtProductoEstiba.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoStkMin.getText().toString().trim().equals("")) {
                    edtProductoStkMin.requestFocus();
                    edtProductoStkMin.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtProductoStkTotal.getText().toString().trim().equals("")) {
                    edtProductoStkTotal.requestFocus();
                    edtProductoStkTotal.setError("Es necesario ingresar todo los datos requeridos");
                } else if (spinnerSegmentacion.getSelectedItem().toString().equals("---Por favor seleccione Segmentacion---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione la Segmentacion. Gracias", Toast.LENGTH_LONG).show();
                } else if (spinnerUsuario.getSelectedItem().toString().equals("---Por favor seleccione Usuario---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el Usuario. Gracias", Toast.LENGTH_LONG).show();
                } else if (spinnerFamilia.getSelectedItem().toString().equals("---Por favor seleccione Familia---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione la Familia. Gracias", Toast.LENGTH_LONG).show();
                } else {

                    Producto u = new Producto();
                    u.setNombre(edtProductoNombre.getText().toString());
                    u.setLote(edtProductoLote.getText().toString());
                    u.setPrecio(Double.parseDouble(edtProductoPrecio.getText().toString()));

                    try {
                        u.setFelab(edtProductoFelab.getText().toString());
                        Log.i("edtProductoFelab.getText().toString() :", edtProductoFelab.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        u.setFven(edtProductoFven.getText().toString());
                        Log.i("edtProductoFven.getText().toString() :", edtProductoFven.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    u.setPeso(Double.parseDouble(edtProductoPeso.getText().toString()));
                    u.setVolumen(Double.parseDouble(edtProductoVolumen.getText().toString()));
                    u.setEstiba(Integer.parseInt(edtProductoEstiba.getText().toString()));
                    u.setStkMin(Double.parseDouble(edtProductoStkMin.getText().toString()));
                    u.setStkTotal(Double.parseDouble(edtProductoStkTotal.getText().toString()));

                    edtProductoSegmentac.setText((spinnerSegmentacion.getSelectedItem().toString()));
                    u.setSegmentac(Segmentacion.valueOf(edtProductoSegmentac.getText().toString()));

                    edtProductoUsuarioId.setText(Long.toString(hashUsuarios.get(spinnerUsuario.getSelectedItem().toString())));
                    Long usuarioId = Long.parseLong(edtProductoUsuarioId.getText().toString());
                    try {
                        u.setUsuario(usuarioService.getByIdUsuario(usuarioId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    edtProductoFamiliaId.setText(Long.toString(hashFamilias.get(spinnerFamilia.getSelectedItem().toString())));
                    Long familiaId = Long.parseLong(edtProductoFamiliaId.getText().toString());
                    try {
                        u.setFamilia(familiaService.getByIdFamilia(familiaId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (productoId != null && productoId.trim().length() > 0) {
                        //update producto
                        if (validaUpdateProducto(u)) {
                            updateProducto(Long.parseLong(productoId), u);
                        }
                    } else {
                        //add producto
                        if (validaAddProducto(u)) {
                            addProducto(u);
                        }
                    }
                }

            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ProductoActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Producto? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (validaDeleteProducto(Long.parseLong(productoId))) {
                                    deleteProducto(Long.parseLong(productoId));
                                    Intent intent = new Intent(ProductoActivity.this, ProductoMainActivity.class);
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
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
        btVolverProduc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }





    public void obtenerListasParaSpinnerUsuarios() {

        Call<List<Usuario>> call = usuarioService.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    List<Usuario> usuarioList = response.body();

                    listaUsuarios = new ArrayList<>();
                    hashUsuarios = new HashMap<String, Long>();
                    listaUsuarios.add("---Por favor seleccione Usuario---");
                    for (int i = 0; i < usuarioList.size(); i++) {
                        hashUsuarios.put(usuarioList.get(i).getNombre() + " " + usuarioList.get(i).getApellido(), usuarioList.get(i).getId());
                        listaUsuarios.add(usuarioList.get(i).getNombre() + " " + usuarioList.get(i).getApellido());
                    }
                    ArrayAdapter<String> adapterSpinnerUsuarios = new ArrayAdapter<String>(ProductoActivity.this, android.R.layout.simple_spinner_item, listaUsuarios);
                    spinnerUsuario.setAdapter(adapterSpinnerUsuarios);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public void obtenerListasParaSpinnerFamilias() {

        Call<List<Familia>> call = familiaService.getFamilias();
        call.enqueue(new Callback<List<Familia>>() {
            @Override
            public void onResponse(Call<List<Familia>> call, Response<List<Familia>> response) {
                if (response.isSuccessful()) {
                    List<Familia> familiaList = response.body();

                    listaFamilias = new ArrayList<>();
                    hashFamilias = new HashMap<String, Long>();
                    listaFamilias.add("---Por favor seleccione Familia---");
                    for (int i = 0; i < familiaList.size(); i++) {
                        hashFamilias.put(familiaList.get(i).getNombre(), familiaList.get(i).getId());
                        listaFamilias.add(familiaList.get(i).getNombre());
                    }
                    ArrayAdapter<String> adapterSpinnerFamilias = new ArrayAdapter<String>(ProductoActivity.this, android.R.layout.simple_spinner_item, listaFamilias);
                    spinnerFamilia.setAdapter(adapterSpinnerFamilias);
                }
            }

            @Override
            public void onFailure(Call<List<Familia>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }


    public boolean validaAddProducto(Producto producto) {
        try {
            String nombreProductoAbuscar = producto.getNombre();
            Producto productoEnBD = productoService.getByNombreProducto(nombreProductoAbuscar).execute().body();
            boolean productoyaexiste = (productoEnBD != null);
            if (productoyaexiste) {
                Toast.makeText(getBaseContext(), "Prodcuto ya existe, por favor revise sus datos.", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //felab.compareTo(fven) > 0
        if (producto.getFelab().compareTo(producto.getFven()) > 0) {
            Toast.makeText(getBaseContext(), "La fecha de Fabricacion no puede ser posterior a la de Vencimiento", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validaUpdateProducto(Producto producto) {

        //felab.compareTo(fven) > 0
        if (producto.getFelab().compareTo(producto.getFven()) > 0) {
            Toast.makeText(getBaseContext(), "La fecha de Fabricacion no puede ser posterior a la de Vencimiento", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean validaDeleteProducto(Long idProducto) {
        try {
            Movimiento movimiento = movimientoService.validoBajaProducto(idProducto).execute().body();
            if (movimiento != null) {
                Toast.makeText(getBaseContext(), "Producto no se puede Eliminar porque existe en Movimiento, elimínelo de Movimiento para luego Eliminar el mismo", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addProducto(Producto u) {
        Call<Producto> call = productoService.addProducto(u);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductoActivity.this, "Producto creado ok!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updateProducto(Long id, Producto producto) {
        Call<Producto> call = productoService.updateProducto(id, producto);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductoActivity.this, "Producto modificado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deleteProducto(Long id) {
        Call<Producto> call = productoService.deleteProducto(id);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductoActivity.this, "Producto borrado ok!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductoActivity.this, "No fue posible borrar el Producto. Verifique si está en otro registro.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdProducto(Long id) {
        Call<Producto> call = productoService.getByIdProducto(id);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()) {
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
