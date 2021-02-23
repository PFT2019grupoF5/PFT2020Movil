package com.utec.pft202002;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utec.pft202002.model.Producto;
import com.utec.pft202002.remote.APIUtils;
import com.utec.pft202002.remote.ProductoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoMainActivity3 extends AppCompatActivity implements ProductoAdapter3.SelectedProducto{

    Button btnAddProducto;
    Button btnGetProductosList;
    Button btVolverProdMain;

    RecyclerView recyclerView;
    ProductoAdapter3 productoAdapter3;

    ProductoService productoService;
    List<Producto> listaProductos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_main3);

        setTitle("CRUD Productos");

        recyclerView = findViewById(R.id.recyclerViewProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        btnAddProducto = (Button) findViewById(R.id.btnAddProducto);
        btnGetProductosList = (Button) findViewById(R.id.btnGetProductosList);
        btVolverProdMain = (Button) findViewById(R.id.btVolverProdMain);

        productoService = APIUtils.getProductoService();

        productoAdapter3 = new ProductoAdapter3(listaProductos, this);
        recyclerView.setAdapter(productoAdapter3);

        btnGetProductosList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProductosList();
            }
        });

        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoMainActivity3.this, ProductoActivity.class);
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
                    listaProductos = response.body();

                    productoAdapter3 = new ProductoAdapter3(listaProductos, ProductoMainActivity3.this);
                    recyclerView.setAdapter(productoAdapter3);

                } else  {
                    Toast.makeText(ProductoMainActivity3.this, "getProductos: Servicio no disponible. Por favor comuniquese con su Administrador.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(ProductoMainActivity3.this, "*** No se pudo obtener la lista de Productos", Toast.LENGTH_SHORT).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }

    @Override
    public void selectedProducto(Producto producto) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));

        //start Activity Producto Form
        Intent intent = new Intent(ProductoMainActivity3.this, ProductoActivity.class);
        intent.putExtra("producto_id", String.valueOf(producto.getId()));
        intent.putExtra("producto_nombre", String.valueOf(producto.getNombre()));
        intent.putExtra("producto_lote", String.valueOf(producto.getLote()));
        intent.putExtra("producto_precio", String.valueOf(producto.getPrecio()));
        intent.putExtra("producto_felab", String.valueOf(producto.getFelab()));
        intent.putExtra("producto_fven", String.valueOf(producto.getFven()));
        intent.putExtra("producto_peso", String.valueOf(producto.getPeso()));
        intent.putExtra("producto_volumen", String.valueOf(producto.getVolumen()));
        intent.putExtra("producto_estiba", String.valueOf(producto.getEstiba()));
        intent.putExtra("producto_stkmin", String.valueOf(producto.getStkMin()));
        intent.putExtra("producto_stktotal", String.valueOf(producto.getStkTotal()));
        intent.putExtra("producto_segmentac", String.valueOf(producto.getSegmentac()));
        intent.putExtra("producto_usuarioid", String.valueOf(producto.getUsuario().getId()));
        intent.putExtra("producto_familiaid", String.valueOf(producto.getFamilia().getId()));
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                productoAdapter3.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_view){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
