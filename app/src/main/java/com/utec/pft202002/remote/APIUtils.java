package com.utec.pft202002.remote;

import com.utec.pft202002.model.Almacenamiento;
import com.utec.pft202002.model.Familia;
import com.utec.pft202002.model.Movimiento;
import com.utec.pft202002.model.Pedido;
import com.utec.pft202002.model.Perfil;
import com.utec.pft202002.model.Producto;
import com.utec.pft202002.model.RenglonPedido;
import com.utec.pft202002.model.Usuario;

public class APIUtils {

    private APIUtils(){
    }

    // public static final String API_URL = "http://192.168.122.1:8180/PFT_JSFyREST/rest/";
    public static final String API_URL = "http://192.168.56.1:8080/PFT2020JSFyREST/rest/";

    public static AlmacenamientoService getAlmacenamientoService(){
        return RetrofitClient.getClient(API_URL).create(AlmacenamientoService.class);
    }

    public static CiudadService getCiudadService(){
        return RetrofitClient.getClient(API_URL).create(CiudadService.class);
    }

    public static EntidadLocService getEntidadLocService(){
        return RetrofitClient.getClient(API_URL).create(EntidadLocService.class);
    }

    public static FamiliaService getFamiliaService(){
        return RetrofitClient.getClient(API_URL).create(FamiliaService.class);
    }

    public static MovimientoService getMovimientoService(){
        return RetrofitClient.getClient(API_URL).create(MovimientoService.class);
    }

    public static PedidoService getPedidoService(){
        return RetrofitClient.getClient(API_URL).create(PedidoService.class);
    }

    public static PerfilService getPerfilService(){
        return RetrofitClient.getClient(API_URL).create(PerfilService.class);
    }

    public static ProductoService getProductoService(){
        return RetrofitClient.getClient(API_URL).create(ProductoService.class);
    }

    public static RenglonPedidoService getRenglonPedidoService(){
        return RetrofitClient.getClient(API_URL).create(RenglonPedidoService.class);
    }

    public static UsuarioService getUsuarioService(){
        return RetrofitClient.getClient(API_URL).create(UsuarioService.class);
    }

}
