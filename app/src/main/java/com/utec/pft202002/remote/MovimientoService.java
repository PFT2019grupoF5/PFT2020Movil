package com.utec.pft202002.remote;

import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MovimientoService {

    @GET("movimientos/getAll/")
    Call<List<Movimiento>> getMovimientos();

    @GET("movimientos/getReporte/{fechaDesde}/{fechaHasta}")
    Call<List<Movimiento>> getReporte(@Path("fechaDesde") String fechaDesde, @Path("fechaHasta") String fechaHasta);

    @GET("movimientos/getById/{id}")
    Call<Movimiento> getByIdMovimiento(@Path("id") Long id);

    @POST("movimientos/add/")
    Call<Movimiento> addMovimiento(@Body Movimiento movimiento);

    @PUT("movimientos/update/{id}")
    Call<Movimiento> updateMovimiento(@Path("id") Long id, @Body Movimiento movimiento);

    @DELETE("movimientos/delete/{id}")
    Call<Movimiento> deleteMovimiento(@Path("id") Long id);
    
}
