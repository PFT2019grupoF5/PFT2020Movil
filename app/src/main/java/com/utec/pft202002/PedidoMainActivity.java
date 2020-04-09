package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.PedidoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoMainActivity extends AppCompatActivity {

    Button btnAddPedido;
    Button btnGetPedidosList;
    ListView listViewPedidos;

    PedidoService pedidoService;
    List<Pedido> list = new ArrayList<Pedido>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_main);

        setTitle("Retrofit 2 CRUD Pedido");

        btnAddPedido = (Button) findViewById(R.id.btnAddPedido);
        btnGetPedidosList = (Button) findViewById(R.id.btnGetPedidosList);
        listViewPedidos = (ListView) findViewById(R.id.listViewPedidos);
        pedidoService = APIUtils.getPedidoService();

        btnGetPedidosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPedidosList();
            }
        });

        btnAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PedidoMainActivity.this, PedidoActivity.class);
                intent.putExtra("pedido_pedfecestim", "");
                intent.putExtra("pedido_fecha", "");
                intent.putExtra("pedido_pedreccodigo", "");
                intent.putExtra("pedido_pedrecfecha", "");
                intent.putExtra("pedido_comentario", "");
                intent.putExtra("pedido_usuarioid", "");
                startActivity(intent);
            }
        });
    }

    public void getPedidosList(){
        Call<List<Pedido>> call = pedidoService.getPedidos();
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewPedidos.setAdapter(new PedidoAdapter(PedidoMainActivity.this, R.layout.list_pedido, list));
                }
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
