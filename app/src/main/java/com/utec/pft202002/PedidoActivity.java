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

import com.utec.pft202002.Enum.estadoPedido;
import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.FamiliaService;
import com.utec.pft202002.remote.PedidoService;
import com.utec.pft202002.remote.UsuarioService;

import java.io.IOException;
import java.util.Calendar;

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
    EditText edtPedidoPedFecEstim;
    EditText edtPedidoFecha;
    EditText edtPedidoPedRecCodigo;
    EditText edtPedidoPedRecFecha;
    EditText edtPedidoPedRecComentario;
    EditText edtPedidoUsuarioId;
    Button btnSave;
    Button btnDel;
    TextView txtPedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        setTitle("Pedidos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtPedidoId = (TextView) findViewById(R.id.txtPedidoId);
        edtPedidoId = (EditText) findViewById(R.id.edtPedidoId);
        edtPedidoPedFecEstim = (EditText) findViewById(R.id.edtPedidoPedFecEstim);
        edtPedidoFecha = (EditText) findViewById(R.id.edtPedidoFecha);
        edtPedidoPedRecCodigo = (EditText) findViewById(R.id.edtPedidoPedRecCodigo);
        edtPedidoPedRecFecha = (EditText) findViewById(R.id.edtPedidoPedRecFecha);
        edtPedidoPedRecComentario = (EditText) findViewById(R.id.edtPedidoRecComentario);
        edtPedidoUsuarioId = (EditText) findViewById(R.id.edtPedidoUsuarioId);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDel = (Button) findViewById(R.id.btnDel);

        pedidoService = APIUtils.getPedidoService();

        Bundle extras = getIntent().getExtras();
        final String pedidoId = extras.getString("pedido_id");
        String pedidoPedFecEstim = extras.getString("pedido_pedfecestim");
        String pedidoFecha = extras.getString("pedido_fecha");
        String pedidoPedRecCodigo = extras.getString("pedido_pedreccodigo");
        String pedidoPedRecFecha = extras.getString("pedido_predrecfecha");
        String pedidoPedRecComentario = extras.getString("pedido_pedreccomentario");
        String pedidoUsuarioId = extras.getString("pedido_usuarioid");

        edtPedidoId.setText(pedidoId);
        edtPedidoPedFecEstim.setText(pedidoPedFecEstim);
        edtPedidoFecha.setText(pedidoFecha);
        edtPedidoPedRecCodigo.setText(pedidoPedRecCodigo);
        edtPedidoPedRecFecha.setText(pedidoPedRecFecha);
        edtPedidoPedRecComentario.setText(pedidoPedRecComentario);
        edtPedidoUsuarioId.setText(pedidoUsuarioId);

        edtPedidoPedFecEstim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFecEstim,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        edtPedidoFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerFecha,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        edtPedidoPedRecFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        PedidoActivity.this,
                        android.R.style.Theme_Light,
                        mDateSetListenerRecFecha,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFecEstim = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateFecEstim = year + "-" + month + "-" + dayOfMonth;
                edtPedidoPedFecEstim.setText(dateFecEstim);
            }
        };

        mDateSetListenerFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateFecha = year + "-" + month + "-" + dayOfMonth;
                edtPedidoFecha.setText(dateFecha);
            }
        };

        mDateSetListenerRecFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                dateRecFecha = year + "-" + month + "-" + dayOfMonth;
                edtPedidoPedRecFecha.setText(dateRecFecha);
            }
        };


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
                Pedido u = new Pedido();

                u.setPedreccomentario(edtPedidoPedRecComentario.getText().toString());

                try {
                    u.setPedfecestim(dateFecEstim);
                    Log.i("pedfecestim:", dateFecEstim);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    u.setFecha(dateFecha);
                    Log.i("fecha:", dateFecha);
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    u.setPedfecestim(dateRecFecha);
                    Log.i("pedrecfecha:", dateRecFecha);
                }catch (Exception e){
                    e.printStackTrace();
                }

                u.setPedreccodigo(Integer.parseInt(edtPedidoPedRecCodigo.getText().toString()));

                Long usuarioId = Long.parseLong(edtPedidoUsuarioId.getText().toString());
                usuarioService = APIUtils.getUsuarioService();
                try {
                    u.setUsuario(usuarioService.getByIdUsuario(usuarioId).execute().body());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(pedidoId != null && pedidoId.trim().length() > 0){
                    //update pedido
                    updatePedido(Long.parseLong(pedidoId), u);
                } else {
                    //add pedido
                    addPedido(u);
                }
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePedido(Long.parseLong(pedidoId));

                Intent intent = new Intent(PedidoActivity.this, PedidoMainActivity.class);
                startActivity(intent);
            }
        });

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
