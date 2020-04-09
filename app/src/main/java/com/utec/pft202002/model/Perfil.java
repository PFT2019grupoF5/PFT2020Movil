package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.tipoPerfil;

public class Perfil {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("tipoPerfil")
    @Expose
    private tipoPerfil tipoPerfil;

    public Perfil() {
    }

    public Perfil(Long id, tipoPerfil tipoPerfil) {
        this.id = id;
        this.tipoPerfil = tipoPerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public tipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(tipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }
}