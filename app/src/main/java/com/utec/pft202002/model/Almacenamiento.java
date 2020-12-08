package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Almacenamiento {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("costoop")
    @Expose
    private double costoop;

    @SerializedName("capestiba")
    @Expose
    private double capestiba;

    @SerializedName("cappeso")
    @Expose
    private double cappeso;

    @SerializedName("volumen")
    @Expose
    private int volumen;

    @SerializedName("entidadLoc")
    @Expose
    private EntidadLoc entidadLoc;


    public Almacenamiento() {
    }

    public Almacenamiento(Long id, int volumen, String nombre, double costoop, double capestiba, double cappeso, EntidadLoc entidadLoc) {
        this.id = id;
        this.volumen = volumen;
        this.nombre = nombre;
        this.costoop = costoop;
        this.capestiba = capestiba;
        this.cappeso = cappeso;
        this.entidadLoc = entidadLoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoop() {
        return costoop;
    }

    public void setCostoop(double costoop) {
        this.costoop = costoop;
    }

    public double getCapestiba() {
        return capestiba;
    }

    public void setCapestiba(double capestiba) {
        this.capestiba = capestiba;
    }

    public double getCappeso() {
        return cappeso;
    }

    public void setCappeso(double cappeso) {
        this.cappeso = cappeso;
    }

    public EntidadLoc getEntidadLoc() {
        return entidadLoc;
    }

    public void setEntidadLoc(EntidadLoc entidadLoc) {
        this.entidadLoc = entidadLoc;
    }
}