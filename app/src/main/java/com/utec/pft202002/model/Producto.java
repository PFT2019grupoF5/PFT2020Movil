package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.Segmentacion;

//import java.sql.Date;
// import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
//import java.util.Locale;
//import java.util.TimeZone;


public class Producto {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("lote")
    @Expose
    private String lote;

    @SerializedName("precio")
    @Expose
    private double precio;

    @SerializedName("felab")
    @Expose
//    @JsonFormat(pattern = "MMM dd, yyyy HH:mm:ss", timezone = "GMT-03:00")
//     @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-03:00")
//    private Date felab;
    private String felab;

    @SerializedName("fven")
    @Expose
//    @JsonFormat(pattern = "MMM dd, yyyy HH:mm:ss", timezone = "GMT-03:00")
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT-03:00")
//    private Date fven;
    private String fven;

    @SerializedName("peso")
    @Expose
    private double peso;

    @SerializedName("volumen")
    @Expose
    private double volumen;

    @SerializedName("estiba")
    @Expose
    private int estiba;

    @SerializedName("stkMin")
    @Expose
    private double stkMin;

    @SerializedName("stkTotal")
    @Expose
    private double stkTotal;

    @SerializedName("segmentac")
    @Expose
    private Segmentacion segmentac;

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;

    @SerializedName("familia")
    @Expose
    private Familia familia;


    public Producto() {
    }

//    public Producto(Long id, String nombre, String lote, double precio, Date felab, Date fven, double peso, double volumen, int estiba, double stkMin, double stkTotal, Segmentacion segmentac, Usuario usuario, Familia familia) {
    public Producto(Long id, String nombre, String lote, double precio, String felab, String fven, double peso, double volumen, int estiba, double stkMin, double stkTotal, Segmentacion segmentac, Usuario usuario, Familia familia) {
        this.id = id;
        this.nombre = nombre;
        this.lote = lote;
        this.precio = precio;
        this.felab = felab;
        this.fven = fven;
        this.peso = peso;
        this.volumen = volumen;
        this.estiba = estiba;
        this.stkMin = stkMin;
        this.stkTotal = stkTotal;
        this.segmentac = segmentac;
        this.usuario = usuario;
        this.familia = familia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

//    public Date getFelab() {
      public String getFelab() {
        return felab;
    }

//    public void setFelab(Date felab) {
    public void setFelab(String felab) {
        this.felab = felab;
    }

//    public Date getFven() {
    public String getFven() {
        return fven;
    }

//    public void setFven(Date fven) {
    public void setFven(String fven) {
        this.fven = fven;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public int getEstiba() {
        return estiba;
    }

    public void setEstiba(int estiba) {
        this.estiba = estiba;
    }

    public double getStkMin() {
        return stkMin;
    }

    public void setStkMin(double stkMin) {
        this.stkMin = stkMin;
    }

    public double getStkTotal() {
        return stkTotal;
    }

    public void setStkTotal(double stkTotal) {
        this.stkTotal = stkTotal;
    }

    public Segmentacion getSegmentac() {
        return segmentac;
    }

    public void setSegmentac(Segmentacion segmentac) {
        this.segmentac = segmentac;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }
}