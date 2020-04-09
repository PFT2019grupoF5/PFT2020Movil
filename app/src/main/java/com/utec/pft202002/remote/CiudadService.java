package com.utec.pft202002.remote;

import com.utec.pft202002.model.Ciudad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CiudadService {

    @GET("ciudades/getAll/")
    Call<List<Ciudad>> getCiudades();

    @GET("ciudades/getById/{id}")
    Call<Ciudad> getByIdCiudad(@Path("id") Long id);

    @POST("ciudades/add/")
    Call<Ciudad> addCiudad(@Body Ciudad ciudad);

    @PUT("ciudades/update/{id}")
    Call<Ciudad> updateCiudad(@Path("id") Long id, @Body Ciudad ciudad);

    @DELETE("ciudades/delete/{id}")
    Call<Ciudad> deleteCiudad(@Path("id") Long id);
    
}
