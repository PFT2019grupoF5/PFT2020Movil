package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoActivity extends AppCompatActivity {

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
                    String dateAsString = edtPedidoPedFecEstim.getText().toString();
                    DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = sourceFormat.parse(dateAsString);
                    u.setPedfecestim(date);
                    Log.i("pedfecestim:", date.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    String dateAsString2 = edtPedidoFecha.getText().toString();
                    DateFormat sourceFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                    Date date2 = sourceFormat2.parse(dateAsString2);
                    u.setFecha(date2);
                    Log.i("fecha:", date2.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    String dateAsString3 = edtPedidoPedRecFecha.getText().toString();
                    DateFormat sourceFormat3 = new SimpleDateFormat("dd/MM/yyyy");
                    Date date3 = sourceFormat3.parse(dateAsString3);
                    u.setPedfecestim(date3);
                    Log.i("pedrecfecha:", date3.toString());
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

    //20200131 agregado Adrian
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
