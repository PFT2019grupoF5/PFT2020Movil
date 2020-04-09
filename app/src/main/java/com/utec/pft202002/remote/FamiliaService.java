package com.utec.pft202002.remote;

import com.utec.pft202002.model.Familia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FamiliaService {

    @GET("familias/getAll/")
    Call<List<Familia>> getFamilias();

    @GET("familias/getById/{id}")
    Call<Familia> getByIdFamilia(@Path("id") Long id);

    @POST("familias/add/")
    Call<Familia> addFamilia(@Body Familia familia);

    @PUT("familias/update/{id}")
    Call<Familia> updateFamilia(@Path("id") Long id, @Body Familia familia);

    @DELETE("familias/delete/{id}")
    Call<Familia> deleteFamilia(@Path("id") Long id);
    
}
