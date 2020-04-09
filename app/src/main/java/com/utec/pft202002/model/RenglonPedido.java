package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.Segmentacion;

import java.util.Date;

public class RenglonPedido {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("rennro")
    @Expose
    private int rennro;

    @SerializedName("rencant")
    @Expose
    private int rencant;

    @SerializedName("producto")
    @Expose
    private Producto producto;

    @SerializedName("pedido")
    @Expose
    private Pedido pedido;


    public RenglonPedido() {
    }

    public RenglonPedido(Long id, int rennro, int rencant, Producto producto, Pedido pedido) {
        this.id = id;
        this.rennro = rennro;
        this.rencant = rencant;
        this.producto = producto;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRennro() {
        return rennro;
    }

    public void setRennro(int rennro) {
        this.rennro = rennro;
    }

    public int getRencant() {
        return rencant;
    }

    public void setRencant(int rencant) {
        this.rencant = rencant;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}