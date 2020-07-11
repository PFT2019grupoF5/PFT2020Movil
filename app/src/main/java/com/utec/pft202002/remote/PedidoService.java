package com.utec.pft202002.remote;

import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.model.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoService {

    @GET("pedidos/getAll/")
    Call<List<Pedido>> getPedidos();

    @GET("pedidos/getReporte/{fechaDesde}/{fechaHasta}")
    Call<List<Pedido>> getReporte(@Path("fechaDesde") String fechaDesde, @Path("fechaHasta") String fechaHasta);

    @GET("pedidos/getById/{id}")
    Call<Pedido> getByIdPedido(@Path("id") Long id);

    @POST("pedidos/add/")
    Call<Pedido> addPedido(@Body Pedido pedido);

    @PUT("pedidos/update/{id}")
    Call<Pedido> updatePedido(@Path("id") Long id, @Body Pedido pedido);

    @DELETE("pedidos/delete/{id}")
    Call<Pedido> deletePedido(@Path("id") Long id);
    
}
