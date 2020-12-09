package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.tipoPerfil;

public class Usuario {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("apellido")
    @Expose
    private String apellido;

    @SerializedName("nomAcceso")
    @Expose
    private String nomAcceso;

    @SerializedName("contrasena")
    @Expose
    private String contrasena;

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("tipoPerfil")
    @Expose
    private tipoPerfil tipoPerfil;


    public Usuario() {
    }

    public Usuario(Long id, String nombre, String apellido, String nomAcceso, String contrasena, String correo, tipoPerfil tipoPerfil) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nomAcceso = nomAcceso;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tipoPerfil = tipoPerfil;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNomAcceso() {
        return nomAcceso;
    }

    public void setNomAcceso(String nomAcceso) {
        this.nomAcceso = nomAcceso;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public tipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(tipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }
}
