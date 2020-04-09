package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Familia {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("descrip")
    @Expose
    private String descrip;

    @SerializedName("incompat")
    @Expose
    private String incompat;

    public Familia() {
    }

    public Familia(Long id, String nombre, String descrip, String incopat) {
        this.id = id;
        this.nombre = nombre;
        this.descrip = descrip;
        this.incompat = incompat;
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

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getIncompat() {
        return incompat;
    }

    public void setIncompat(String incompat) {
        this.incompat = incompat;
    }
}