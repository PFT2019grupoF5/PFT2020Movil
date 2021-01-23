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

import com.utec.pft202002.Enum.estadoPedido;
import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.model.Usuario;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PedidoService;
import com.utec.pft202002.remote.UsuarioService;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
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

public class PedidoActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFecEstim;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFecha;
    private DatePickerDialog.OnDateSetListener mDateSetListenerRecFecha;
    String dateFecEstim="", dateFecha="", dateRecFecha="";

    PedidoService pedidoService;
    UsuarioService usuarioService;

    EditText edtPedidoId;
    EditText edtPedidoPedEstado;
    EditText edtPedidoPedFecEstim;
    EditText edtPedidoFecha;
    EditText edtPedidoPedRecCodigo;
    EditText edtPedidoPedRecFecha;
    EditText edtPedidoPedRecComentario;
    EditText edtPedidoUsuarioId;
    TextView txtPedidoId;
    Spinner  spinnerEstadoPedido;
    Spinner  spinnerUsuario;
    Button   btnSave;
    Button   btnDel;
    Button   btnVolverPed;
    ArrayList<String> listaUsuarios;
    HashMap<String,Long> hashUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        setTitle("Pedidos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtPedidoId = (TextView) findViewById(R.id.txtPedidoId);
        edtPedidoId = (EditText) findViewById(R.id.edtPedidoId);
        edtPedidoPedEstado = (EditText) findViewById(R.id.edtPedidoPedEstado);
        edtPedidoPedFecEstim = (EditText) findViewById(R.id.edtPedidoPedFecEstim);
        edtPedidoFecha = (EditText) findViewById(R.id.edtPedidoFecha);
        edtPedidoPedRecCodigo = (EditText) findViewById(R.id.edtPedidoPedRecCodigo);
        edtPedidoPedRecFecha = (EditText) findViewById(R.id.edtPedidoPedRecFecha);
        edtPedidoPedRecComentario = (EditText) findViewById(R.id.edtPedidoRecComentario);
        edtPedidoUsuarioId = (EditText) findViewById(R.id.edtPedidoUsuarioId);
        spinnerEstadoPedido = (Spinner) findViewById(R.id.spinnerEstadoPedido);
        spinnerUsuario = (Spinner) findViewById(R.id.spinnerUsuario);

        btnSave = (Button) findViewById(R.id.btnSavePed);
        btnDel = (Button) findViewById(R.id.btnDelPed);
        btnVolverPed = (Button) findViewById(R.id.btnVolverPed);


        pedidoService = APIUtils.getPedidoService();
        usuarioService = APIUtils.getUsuarioService();

        Bundle extras = getIntent().getExtras();
        final String pedidoId = extras.getString("pedido_id");
        String pedidoPedEstado = extras.getString("pedido_pedestado");
        String pedidoPedFecEstim = extras.getString("pedido_pedfecestim");
        String pedidoFecha = extras.getString("pedido_fecha");
        String pedidoPedRecCodigo = extras.getString("pedido_pedreccodigo");
        String pedidoPedRecFecha = extras.getString("pedido_pedrecfecha");
        String pedidoPedRecComentario = extras.getString("pedido_pedreccomentario");
        String pedidoUsuarioId = extras.getString("pedido_usuarioid");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        edtPedidoId.setText(pedidoId);
        edtPedidoPedEstado.setText(pedidoPedEstado);

        Date PedFecEstim = new Date();
        Date Fecha = new Date();
        Date PedRecFecha = new Date();

        if (pedidoId != null) {
            Timestamp tPedFecEstim = new Timestamp(Long.parseLong(pedidoPedFecEstim));
            PedFecEstim = new Date(tPedFecEstim.getTime());
            edtPedidoPedFecEstim.setText(sdf.format(new Date(tPedFecEstim.getTime())));
            Timestamp tFecha  = new Timestamp(Long.parseLong(pedidoFecha));
            Fecha = new Date(tFecha.getTime());
            edtPedidoFecha.setText(sdf.format(new Date(tFecha.getTime())));
            Timestamp tPedRecFecha  = new Timestamp(Long.parseLong(pedidoPedRecFecha));
            PedRecFecha = new Date(tPedRecFecha.getTime());
            edtPedidoPedRecFecha.setText(sdf.format(new Date(tPedRecFecha.getTime())));
        } else {
            edtPedidoPedFecEstim.setText(sdf.format(PedFecEstim));
            edtPedidoFecha.setText(sdf.format(Fecha));
            edtPedidoPedRecFecha.setText(sdf.format(PedRecFecha));
        }


        final Date PedFecEstim1 = PedFecEstim;
        edtPedidoPedFecEstim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                cal.setTime(PedFecEstim1);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFecEstim,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        final Date Fecha1 = Fecha;
        edtPedidoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                cal.setTime(Fecha1);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFecha,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        final Date PedRecFecha1 = PedRecFecha;
        edtPedidoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                cal.setTime(PedRecFecha1);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerRecFecha,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFecEstim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateFecEstim = dayOfMonth + "/" + month + "/" + year;
                edtPedidoPedFecEstim.setText(dateFecEstim);
            }
        };


        mDateSetListenerFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateFecha = dayOfMonth + "/" + month + "/" + year;
                edtPedidoFecha.setText(dateFecha);
            }
        };

        mDateSetListenerRecFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                dateRecFecha = dayOfMonth + "/" + month + "/" + year;
                edtPedidoPedRecFecha.setText(dateRecFecha);
            }
        };

        edtPedidoPedRecCodigo.setText(pedidoPedRecCodigo);


        edtPedidoPedRecComentario.setText(pedidoPedRecComentario);
        edtPedidoUsuarioId.setText(pedidoUsuarioId);

        obtenerListasParaSpinnerUsuarios();

        if(pedidoId != null && pedidoId.trim().length() > 0 ){
            edtPedidoId.setFocusable(false);
        } else {
            txtPedidoId.setVisibility(View.INVISIBLE);
            edtPedidoId.setVisibility(View.INVISIBLE);
            btnDel.setVisibility(View.INVISIBLE);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateValidator validator = new DateValidatorUsingDateFormat("dd/MM/yyyy");

                if (!validator.isValid(edtPedidoFecha.getText().toString())) {
                    edtPedidoFecha.requestFocus();
                    edtPedidoFecha.setError("Pf ingrese fecha válida en formato dd/MM/yyyy : " + edtPedidoFecha.getText().toString());
                    System.out.println("EN EL IF ::: edtPedidoFecha: " + edtPedidoFecha);
                } else if  (!validator.isValid(edtPedidoPedFecEstim.getText().toString())) {
                    edtPedidoPedFecEstim.requestFocus();
                    edtPedidoPedFecEstim.setError("Pf ingrese fecha válida en formato dd/MM/yyyy : " + edtPedidoPedFecEstim.getText().toString());
                    System.out.println("EN EL IF ::: edtPedidoFecha: " + edtPedidoPedFecEstim);
                } else if (!validator.isValid(edtPedidoPedRecFecha.getText().toString())) {
                    edtPedidoPedRecFecha.requestFocus();
                    edtPedidoPedRecFecha.setError("Pf ingrese fecha válida en formato dd/MM/yyyy : " + edtPedidoPedRecFecha.getText().toString());
                    System.out.println("EN EL IF ::: edtPedidoFecha: " + edtPedidoPedRecFecha);
                } else if   (edtPedidoPedRecCodigo.getText().toString().trim().equals("")) {
                    edtPedidoPedRecCodigo.requestFocus();
                    edtPedidoPedRecCodigo.setError("Es necesario ingresar todo los datos requeridos");
                } else if  (edtPedidoPedRecComentario.getText().toString().trim().equals("")) {
                    edtPedidoPedRecComentario.requestFocus();
                    edtPedidoPedRecComentario.setError("Es necesario ingresar todo los datos requeridos");
                }else if  (spinnerEstadoPedido.getSelectedItem().toString().equals("---Por favor seleccione Estado del Pedido---")){
                    Toast.makeText(getBaseContext(),"Por favor seleccione el Estado del Pedido. Gracias",Toast.LENGTH_LONG).show();
                } else if (spinnerUsuario.getSelectedItem().toString().equals("---Por favor seleccione Usuario---")) {
                    Toast.makeText(getBaseContext(), "Por favor seleccione el Usuario. Gracias", Toast.LENGTH_LONG).show();
                } else {

                    Pedido u = new Pedido();

                    edtPedidoPedEstado.setText((spinnerEstadoPedido.getSelectedItem().toString()));
                    u.setPedestado(estadoPedido.valueOf(edtPedidoPedEstado.getText().toString()));

                    u.setPedreccomentario(edtPedidoPedRecComentario.getText().toString());

                    try {
                        u.setPedfecestim(edtPedidoPedFecEstim.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        u.setFecha(edtPedidoFecha.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        u.setPedrecfecha(edtPedidoPedRecFecha.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    u.setPedreccodigo(Integer.parseInt(edtPedidoPedRecCodigo.getText().toString()));

                    edtPedidoUsuarioId.setText(Long.toString(hashUsuarios.get(spinnerUsuario.getSelectedItem().toString())));
                    Long usuarioId = Long.parseLong(edtPedidoUsuarioId.getText().toString());
                    try {
                        u.setUsuario(usuarioService.getByIdUsuario(usuarioId).execute().body());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (pedidoId != null && pedidoId.trim().length() > 0) {
                        //update pedido
                        if (validaUpdatePedido(u)) {

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

                            try {
                                u.setPedfecestim(Long.toString(sdf.parse(edtPedidoPedFecEstim.getText().toString()).getTime()));
                                u.setFecha(Long.toString(sdf.parse(edtPedidoFecha.getText().toString()).getTime()));
                                u.setPedrecfecha(Long.toString(sdf.parse(edtPedidoPedRecFecha.getText().toString()).getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            updatePedido(Long.parseLong(pedidoId), u);
                            finish();

                        }
                    } else {
                        //add pedido
                        if (validaAddPedido(u)) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

                            try {
                                u.setPedfecestim(Long.toString(sdf.parse(edtPedidoPedFecEstim.getText().toString()).getTime()));
                                u.setFecha(Long.toString(sdf.parse(edtPedidoFecha.getText().toString()).getTime()));
                                u.setPedrecfecha(Long.toString(sdf.parse(edtPedidoPedRecFecha.getText().toString()).getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            addPedido(u);
                            finish();
                        }
                    }
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(PedidoActivity.this);
                builder.setMessage("¿Por favor confirme que quiere borrar este Pedido? Gracias").
                        setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (validaDeletePedido(Long.parseLong(pedidoId))) {
                                    deletePedido(Long.parseLong(pedidoId));
                                    finish();

                                    //Intent intent = new Intent(PedidoActivity.this, PedidoMainActivity.class);
                                    //startActivity(intent);
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

        btnVolverPed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void obtenerListasParaSpinnerUsuarios(){

        Call<List<Usuario>> call = usuarioService.getUsuarios();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.isSuccessful()){
                    List<Usuario> usuarioList = response.body();

                    listaUsuarios = new ArrayList<>();
                    hashUsuarios = new HashMap<String,Long>();
                    listaUsuarios.add("---Por favor seleccione Usuario---");
                    for (int i=0;i<usuarioList.size();i++){
                        hashUsuarios.put(usuarioList.get(i).getId()+" "+usuarioList.get(i).getApellido(),usuarioList.get(i).getId());
                        listaUsuarios.add(usuarioList.get(i).getId()+" "+usuarioList.get(i).getApellido());
                    }
                    ArrayAdapter<String> adapterSpinnerUsuarios = new ArrayAdapter<String>(PedidoActivity.this, android.R.layout.simple_spinner_item, listaUsuarios);
                    spinnerUsuario.setAdapter(adapterSpinnerUsuarios);
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public boolean validaAddPedido(Pedido pedido) {
        return true;
    }

    public boolean validaUpdatePedido(Pedido pedido) {
        return true;
    }

    public boolean validaDeletePedido(Long idPedido) {
        return true;
    }



    public void addPedido(Pedido u){
        Call<Pedido> call = pedidoService.addPedido(u);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PedidoActivity.this, "Pedido creado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void updatePedido(Long id, Pedido u){
        Call<Pedido> call = pedidoService.updatePedido(id, u);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PedidoActivity.this, "Pedido modificado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void deletePedido(Long id){
        Call<Pedido> call = pedidoService.deletePedido(id);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PedidoActivity.this, "Pedido borrado ok!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PedidoActivity.this, "No fue posible borrar el Pedido. Verifique si está en otro registro.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    public void getByIdPedido(Long id){
        Call<Pedido> call = pedidoService.getByIdPedido(id);
        call.enqueue(new Callback<Pedido>() {
            @Override
            public void onResponse(Call<Pedido> call, Response<Pedido> response) {
                if(response.isSuccessful()){
                    Toast.makeText(PedidoActivity.this, "Pedido encontrado ok!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pedido> call, Throwable t) {
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
