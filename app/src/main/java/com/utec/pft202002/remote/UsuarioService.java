package com.utec.pft202002.remote;

import com.utec.pft202002.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("usuarios/getAll/")
    Call<List<Usuario>> getUsuarios();

    @GET("usuarios/getById/{id}")
    Call<Usuario> getByIdUsuario(@Path("id") Long id);

    @POST("usuarios/add/")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    @PUT("usuarios/update/{id}")
    Call<Usuario> updateUsuario(@Path("id") Long id, @Body Usuario usuario);

    @DELETE("usuarios/delete/{id}")
    Call<Usuario> deleteUsuario(@Path("id") Long id);
    
}
