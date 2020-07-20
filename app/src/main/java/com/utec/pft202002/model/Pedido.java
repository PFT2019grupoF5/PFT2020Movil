package com.utec.pft202002.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.utec.pft202002.Enum.estadoPedido;

public class Pedido {
    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("pedfecestim")
    @Expose
    private String pedfecestim;

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("pedreccodigo")
    @Expose
    private int pedreccodigo;

    @SerializedName("pedrecfecha")
    @Expose
    private String pedrecfecha;

    @SerializedName("pedreccomentario")
    @Expose
    private String pedreccomentario;

    @SerializedName("pedestado")
    @Expose
    private estadoPedido pedestado;

    @SerializedName("usuario")
    @Expose
    private Usuario usuario;


    public Pedido() {
    }

public Pedido(Long id, String pedfecestim, String fecha, int pedreccodigo, String pedrecfecha, String pedreccomentario, estadoPedido pedestado, Usuario usuario) {
        this.id = id;
        this.pedfecestim = pedfecestim;
        this.fecha = fecha;
        this.pedreccodigo = pedreccodigo;
        this.pedrecfecha = pedrecfecha;
        this.pedreccomentario = pedreccomentario;
        this.pedestado = pedestado;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPedfecestim() {
        return pedfecestim;
    }

    public void setPedfecestim(String pedfecestim) {
        this.pedfecestim = pedfecestim;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPedreccodigo() {
        return pedreccodigo;
    }

    public void setPedreccodigo(int pedreccodigo) {
        this.pedreccodigo = pedreccodigo;
    }

    public String getPedrecfecha() {
        return pedrecfecha;
    }

    public void setPedrecfecha(String pedrecfecha) {
        this.pedrecfecha = pedrecfecha;
    }

    public String getPedreccomentario() {
        return pedreccomentario;
    }

    public void setPedreccomentario(String pedreccomentario) { this.pedreccomentario = pedreccomentario; }

    public estadoPedido getPedestado() {
        return pedestado;
    }

    public void setPedestado(estadoPedido pedestado) {
        this.pedestado = pedestado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}