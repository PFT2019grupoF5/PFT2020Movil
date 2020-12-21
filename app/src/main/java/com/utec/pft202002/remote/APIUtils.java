package com.utec.pft202002.remote;

public class APIUtils {

    private APIUtils(){
    }

    //public static final String API_URL = "http://192.168.0.132:8080/PFT2020WEB/rest/";
    public static final String API_URL = "http://192.168.0.132:8100/PFT2020WEB/rest/";

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
