package com.utec.pft202002.remote;

import com.utec.pft202002.model.RenglonPedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RenglonPedidoService {

    @GET("renglonPedidos/getAll/")
    Call<List<RenglonPedido>> getRenglonPedidos();

    @GET("renglonPedidos/getById/{id}")
    Call<RenglonPedido> getByIdRenglonPedido(@Path("id") Long id);

    @POST("renglonPedidos/add/")
    Call<RenglonPedido> addRenglonPedido(@Body RenglonPedido renglonPedido);

    @PUT("renglonPedidos/update/{id}")
    Call<RenglonPedido> updateRenglonPedido(@Path("id") Long id, @Body RenglonPedido renglonPedido);

    @DELETE("renglonPedidos/delete/{id}")
    Call<RenglonPedido> deleteRenglonPedido(@Path("id") Long id);
    
}
