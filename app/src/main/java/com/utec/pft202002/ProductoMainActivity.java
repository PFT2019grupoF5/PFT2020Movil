package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.utec.pft202002.model.Producto;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.ProductoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoMainActivity extends AppCompatActivity {

    Button btnAddProducto;
    Button btnGetProductosList;
    ListView listViewProductos;
    Button btVolverProdMain;

    ProductoService productoService;
    List<Producto> list = new ArrayList<Producto>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_main);

        setTitle("Retrofit 2 CRUD Producto");

        btnAddProducto = (Button) findViewById(R.id.btnAddProducto);
        btnGetProductosList = (Button) findViewById(R.id.btnGetProductosList);
        listViewProductos = (ListView) findViewById(R.id.listViewProductos);
        productoService = APIUtils.getProductoService();
        btVolverProdMain = (Button) findViewById(R.id.btVolverProdMain);

        btnGetProductosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductosList();
            }
        });

        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoMainActivity.this, ProductoActivity.class);
                intent.putExtra("producto_nombre", "");
                intent.putExtra("producto_lote", "");
                intent.putExtra("producto_precio", "");
                intent.putExtra("producto_felab", "");
                intent.putExtra("producto_fven", "");
                intent.putExtra("producto_peso", "");
                intent.putExtra("producto_volumen", "");
                intent.putExtra("producto_estiba", "");
                intent.putExtra("producto_stkmin", "");
                intent.putExtra("producto_stktotal", "");
                intent.putExtra("producto_segmentac", "");
                intent.putExtra("producto_usuarioid", "");
                intent.putExtra("producto_familiaid", "");
                startActivity(intent);
            }
        });
        btVolverProdMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getProductosList(){
        Call<List<Producto>> call = productoService.getProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    listViewProductos.setAdapter(new ProductoAdapter(ProductoMainActivity.this, R.layout.list_producto, list));
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

}
