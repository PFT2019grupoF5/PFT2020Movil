package com.utec.pft202002.remote;

import com.utec.pft202002.model.EntidadLoc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EntidadLocService {

    @GET("entidadesLoc/getAll/")
    Call<List<EntidadLoc>> getEntidadesLoc();

    @GET("entidadesLoc/getById/{id}")
    Call<EntidadLoc> getByIdEntidadLoc(@Path("id") Long id);

    @POST("entidadesLoc/add/")
    Call<EntidadLoc> addEntidadLoc(@Body EntidadLoc entidadLoc);

    @PUT("entidadesLoc/update/{id}")
    Call<EntidadLoc> updateEntidadLoc(@Path("id") Long id, @Body EntidadLoc entidadLoc);

    @DELETE("entidadesLoc/delete/{id}")
    Call<EntidadLoc> deleteEntidadLoc(@Path("id") Long id);
    
}
