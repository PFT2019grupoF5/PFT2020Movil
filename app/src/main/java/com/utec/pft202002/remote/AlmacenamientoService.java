package com.utec.pft202002.remote;

import com.utec.pft202002.model.Almacenamiento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AlmacenamientoService {

    @GET("almacenamientos/getAll/")
    Call<List<Almacenamiento>> getAlmacenamientos();

    @GET("almacenamientos/getById/{id}")
    Call<Almacenamiento> getByIdAlmacenamiento(@Path("id") Long id);

    @POST("almacenamientos/add/")
    Call<Almacenamiento> addAlmacenamiento(@Body Almacenamiento almacenamiento);

    @PUT("almacenamientos/update/{id}")
    Call<Almacenamiento> updateAlmacenamiento(@Path("id") Long id, @Body Almacenamiento almacenamiento);

    @DELETE("almacenamientos/delete/{id}")
    Call<Almacenamiento> deleteAlmacenamiento(@Path("id") Long id);
    
}
