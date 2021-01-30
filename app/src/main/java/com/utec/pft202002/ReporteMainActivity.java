package com.utec.pft202002;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.model.Producto;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PedidoService;

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

public class ReporteMainActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListenerFechaDesde;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFechaHasta;
    String fechaDesde="", fechaHasta="";

    EditText edtReporteFechaDesde;
    EditText edtReporteFechaHasta;
    Button   btnGetReporteList;
    Button   btnVolverRep;

    ListView listViewReporte;

    PedidoService PedidoService;
    List<Pedido> list = new ArrayList<Pedido>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_main);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        setTitle("Reporte Pedidos : " + dtf.format(now) );

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        edtReporteFechaDesde = (EditText) findViewById(R.id.edtReporteFechaDesde);
        edtReporteFechaHasta = (EditText) findViewById(R.id.edtReporteFechaHasta);
        btnGetReporteList = (Button) findViewById(R.id.btnGetReporteList);
        btnVolverRep = (Button) findViewById(R.id.btnVolverRepdMain);

        listViewReporte = (ListView) findViewById(R.id.listViewReporte);
        
        PedidoService = APIUtils.getPedidoService();

        Date ReporteFechaDesde = new Date();
        Date ReporteFechaHasta = new Date();

        edtReporteFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteMainActivity.this,
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
                edtReporteFechaDesde.setText(fechaDesde);
            }
        };

        edtReporteFechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GMT-3"));
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReporteMainActivity.this,
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
                edtReporteFechaHasta.setText(fechaHasta);
            }
        };
        


        btnGetReporteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateValidator validator = new DateValidatorUsingDateFormat("yyyy-MM-dd");

                if (!validator.isValid(edtReporteFechaDesde.getText().toString())) {
                    edtReporteFechaDesde.requestFocus();
                    edtReporteFechaDesde.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtReporteFechaDesde.getText().toString());
                } else if  (!validator.isValid(edtReporteFechaHasta.getText().toString())) {
                    edtReporteFechaHasta.requestFocus();
                    edtReporteFechaHasta.setError("Pf ingrese fecha válida en formato yyyy-MM-dd : " + edtReporteFechaHasta.getText().toString());
                } else if (edtReporteFechaDesde.getText().toString().trim().equals("")) {
                    edtReporteFechaDesde.requestFocus();
                    edtReporteFechaDesde.setError("Es necesario ingresar todo los datos requeridos");
                } else if (edtReporteFechaHasta.getText().toString().trim().equals("")) {
                    edtReporteFechaHasta.requestFocus();
                    edtReporteFechaHasta.setError("Es necesario ingresar todo los datos requeridos");
                }  else {
                    if (validaFechasReporte()){
                        getReporteList();
                    }
                }
            }
        });
        
        btnVolverRep.setOnClickListener(new View.OnClickListener() {
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
            fechaDesde = sdf.parse(edtReporteFechaDesde.getText().toString());
            fechaHasta = sdf.parse(edtReporteFechaHasta.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ( fechaDesde.compareTo(fechaHasta) > 0 ) {
            Toast.makeText(getBaseContext(), "La fecha desde (" + edtReporteFechaDesde.getText().toString() + ") no puede ser posterior a la fecha hasta (" + edtReporteFechaHasta.getText().toString() + ")", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void getReporteList(){

        Call<List<Pedido>> call = PedidoService.getPedidosEntreFechas(edtReporteFechaDesde.getText().toString(), edtReporteFechaHasta.getText().toString());
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewReporte.setAdapter(new ReporteAdapter(ReporteMainActivity.this, R.layout.list_reporte, list));
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}