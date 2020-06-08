package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.tipoMovimiento;

public class Movimiento {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("cantidad")
    @Expose
    private int cantidad;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("costo")
    @Expose
    private double costo;

    @SerializedName("tipoMov")
    @Expose
    private tipoMovimiento tipoMov;

    @SerializedName("producto")
    @Expose
    private Producto producto;

    @SerializedName("almacenamiento")
    @Expose
    private Almacenamiento almacenamiento;


    public Movimiento() {
    }

    public Movimiento(Long id, String fecha, int cantidad, String descripcion, double costo, tipoMovimiento tipoMov, Producto producto, Almacenamiento almacenamiento) {
        this.id = id;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.costo = costo;
        this.producto = producto;
        this.almacenamiento = almacenamiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public tipoMovimiento getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(tipoMovimiento tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Almacenamiento getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(Almacenamiento almacenamiento) {
        this.almacenamiento = almacenamiento;
    }
}