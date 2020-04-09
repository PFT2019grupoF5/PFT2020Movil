package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.tipoLoc;

public class EntidadLoc {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("codigo")
    @Expose
    private int codigo;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("direccion")
    @Expose
    private String direccion;

    @SerializedName("tipoloc")
    @Expose
    private tipoLoc tipoLoc;

    @SerializedName("ciudad")
    @Expose
    private Ciudad ciudad;


    public EntidadLoc() {
    }

    public EntidadLoc(Long id, int codigo, String nombre, String direccion, tipoLoc tipoloc, Ciudad ciudad) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipoLoc = tipoloc;
        this.ciudad = ciudad;
    }

    public Long getId() { return id; }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public tipoLoc getTipoLoc() {
        return tipoLoc;
    }

    public void setTipoLoc(tipoLoc tipoLoc) {
        this.tipoLoc = tipoLoc;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

}
