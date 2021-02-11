package com.utec.pft202002;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.model.RenglonPedido;
import com.utec.pft202002.model.RenglonReporte;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PedidoService;
import com.utec.pft202002.remote.RenglonPedidoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reporte2MainActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFechaDesde;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFechaHasta;
    String fechaDesde="", fechaHasta="";

    EditText edtReporte2FechaDesde;
    EditText edtReporte2FechaHasta;
    Button   btnGetReporte2List;
    Button   btnVolverRep2;

    ListView listViewReporte2;

    PedidoService pedidoService;
    RenglonPedidoService renglonPedidoService;

    List<Pedido> listPedidos = new ArrayList<Pedido>();
    List<RenglonPedido> listRenglonesPedido = new ArrayList<RenglonPedido>();
    List<RenglonReporte> listRenglonesReporte = new ArrayList<RenglonReporte>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte2_main);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        setTitle("Reporte Pedidos: " + dtf.format(now) );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        edtReporte2FechaDesde = (EditText) findViewById(R.id.edtReporte2FechaDesde);
        edtReporte2FechaHasta = (EditText) findViewById(R.id.edtReporte2FechaHasta);
        btnGetReporte2List = (Button) findViewById(R.id.btnGetReporte2List);
        btnVolverRep2 = (Button) findViewById(R.id.btnVolverRep2Main);

        listViewReporte2 = (ListView) findViewById(R.id.listViewReporte2);

        pedidoService = APIUtils.getPedidoService();
        renglonPedidoService = APIUtils.getRenglonPedidoService();


        edtReporte2FechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Reporte2MainActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFechaDesde,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFechaDesde = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                fechaDesde = year + "-" + month + "-" + dayOfMonth;
                edtReporte2FechaDesde.setText(fechaDesde);
            }
        };

        edtReporte2FechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Reporte2MainActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFechaHasta,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFechaHasta = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // +1 because January is zero
                month = month + 1;
                fechaHasta = year + "-" + month + "-" + dayOfMonth;
                edtReporte2FechaHasta.setText(fechaHasta);
            }
        };
        


        btnGetReporte2List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateValidator validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");

                if (!validator.isValid(edtReporte2FechaDesde.getText().toString())) {
                    edtReporte2FechaDesde.requestFocus();
                    edtReporte2FechaDesde.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtReporte2FechaDesde.getText().toString());
                } else if  (!validator.isValid(edtReporte2FechaHasta.getText().toString())) {
                    edtReporte2FechaHasta.requestFocus();
                    edtReporte2FechaHasta.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtReporte2FechaHasta.getText().toString());
                } else if (edtReporte2FechaDesde.getText().toString().trim().equals("")) {
                    edtReporte2FechaDesde.requestFocus();
                    edtReporte2FechaDesde.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtReporte2FechaHasta.getText().toString().trim().equals("")) {
                    edtReporte2FechaHasta.requestFocus();
                    edtReporte2FechaHasta.setError("Es necesario ingresar todo los datos requeridos");
                }  else {
                    if (validaFechasReporte()){
                        getReporteList();
                    }
                }
            }
        });
        
        btnVolverRep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public boolean validaFechasReporte() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        Date fechaDesde = null;
        Date fechaHasta = null;
        try {
            fechaDesde = sdf.parse(edtReporte2FechaDesde.getText().toString());
            fechaHasta = sdf.parse(edtReporte2FechaHasta.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(Reporte2MainActivity.this, "*** No se pudo parsear fecha", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if ( fechaDesde.compareTo(fechaHasta) > 0 ) {
            Toast.makeText(getBaseContext(), "La fecha desde (" + edtReporte2FechaDesde.getText().toString() + ") no puede ser posterior a la fecha hasta (" + edtReporte2FechaHasta.getText().toString() + ")", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void getReporteList(){

        Call<List<Pedido>> call = pedidoService.getPedidosEntreFechas(edtReporte2FechaDesde.getText().toString(), edtReporte2FechaHasta.getText().toString());
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()){
                    listPedidos = response.body();

                    for(Pedido pedido : listPedidos) {
                        Call<List<RenglonPedido>> call2 = renglonPedidoService.getRenglonesDelPedido(pedido.getId());
                        call2.enqueue(new Callback<List<RenglonPedido>>() {
                            @Override
                            public void onResponse(Call<List<RenglonPedido>> call2, Response<List<RenglonPedido>> response2) {
                                if(response2.isSuccessful()){
                                    listRenglonesPedido = response2.body();
                                    for (RenglonPedido renglonPedido : listRenglonesPedido) {
                                        RenglonReporte rr = new RenglonReporte();
                                        rr.setPedreccodigo(pedido.getPedreccodigo());
                                        rr.setFecha(pedido.getFecha());
                                        rr.setPedfecestim(pedido.getPedfecestim());
                                        rr.setPedestado(pedido.getPedestado());
                                        rr.setProducto(renglonPedido.getProducto());
                                        rr.setRencant(renglonPedido.getRencant());
                                        listRenglonesReporte.add(rr);
                                    }
                                } else  {
                                    Toast.makeText(Reporte2MainActivity.this, "getRenglonesDelPedido: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                            @Override
                            public void onFailure(Call<List<RenglonPedido>> call2, Throwable t) {
                                Toast.makeText(Reporte2MainActivity.this, "*** No se pudo obtener Lista de Renglones del Pedido", Toast.LENGTH_SHORT).show();
                                Log.e("ERROR: ", t.getMessage());
                                finish();
                            }
                        });
                    }

                    ArrayAdapter<RenglonReporte> reporte2Adapter = new Reporte2Adapter(Reporte2MainActivity.this, R.layout.list_reporte2, listRenglonesReporte);
                    reporte2Adapter.notifyDataSetChanged();
                    listViewReporte2.setAdapter(reporte2Adapter);

//                    listViewReporte2.setAdapter(new Reporte2Adapter(Reporte2MainActivity.this, R.layout.list_reporte2, listRenglonesReporte));
                } else  {
                    Toast.makeText(Reporte2MainActivity.this, "getPedidosEntreFechas: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Toast.makeText(Reporte2MainActivity.this, "*** No se pudo obtener Lista de Pedidos", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}