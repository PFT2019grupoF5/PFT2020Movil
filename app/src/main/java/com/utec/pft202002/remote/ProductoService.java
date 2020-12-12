package com.utec.pft202002.remote;

import com.utec.pft202002.model.Producto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductoService {

    @GET("productos/getAll/")
    Call<List<Producto>> getProductos();

    @GET("productos/getById/{id}")
    Call<Producto> getByIdProducto(@Path("id") Long id);

    @GET("productos/getNombre/{nombre}")
    Call<Producto> getByNombreProducto(@Path("nombre") String nombre);

    @POST("productos/add/")
    Call<Producto> addProducto(@Body Producto producto);

    @PUT("productos/update/{id}")
    Call<Producto> updateProducto(@Path("id") Long id, @Body Producto producto);

    @DELETE("productos/delete/{id}")
    Call<Producto> deleteProducto(@Path("id") Long id);
    
}
