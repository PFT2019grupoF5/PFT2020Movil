package com.utec.pft202002.model;

import com.utec.pft202002.Enum.estadoPedido;

public class RenglonReporte {

    private int pedreccodigo;
    private String pedfecestim;
    private String fecha;
    private estadoPedido pedestado;
    private Producto producto;
    private int rencant;

    public RenglonReporte() {
    }

    public RenglonReporte(int pedreccodigo, String fecha, String pedfecestim, estadoPedido pedestado, Producto producto, int rencant) {
        this.pedreccodigo = pedreccodigo;
        this.pedfecestim = pedfecestim;
        this.fecha = fecha;
        this.pedestado = pedestado;
        this.producto = producto;
        this.rencant = rencant;
    }

    public int getPedreccodigo() {
        return pedreccodigo;
    }

    public void setPedreccodigo(int pedreccodigo) {
        this.pedreccodigo = pedreccodigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPedfecestim() {
        return pedfecestim;
    }

    public void setPedfecestim(String pedfecestim) {
        this.pedfecestim = pedfecestim;
    }

    public estadoPedido getPedestado() {
        return pedestado;
    }

    public void setPedestado(estadoPedido pedestado) {
        this.pedestado = pedestado;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getRencant() {
        return rencant;
    }

    public void setRencant(int rencant) {
        this.rencant = rencant;
    }
}
