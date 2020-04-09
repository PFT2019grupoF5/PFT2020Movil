package com.utec.pft202002.remote;

import com.utec.pft202002.model.Perfil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PerfilService {

    @GET("perfiles/getAll/")
    Call<List<Perfil>> getPerfiles();

    @GET("perfiles/getById/{id}")
    Call<Perfil> getByIdPerfil(@Path("id") Long id);

    @POST("perfiles/add/")
    Call<Perfil> addPerfil(@Body Perfil perfil);

    @PUT("perfiles/update/{id}")
    Call<Perfil> updatePerfil(@Path("id") Long id, @Body Perfil perfil);

    @DELETE("perfiles/delete/{id}")
    Call<Perfil> deletePerfil(@Path("id") Long id);
    
}
