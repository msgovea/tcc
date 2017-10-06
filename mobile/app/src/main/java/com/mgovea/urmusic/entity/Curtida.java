package com.mgovea.urmusic.entity;

import java.io.Serializable;

/**
 * Created by mgovea on 28/08/2017.
 */

public class Curtida implements Serializable {

    private static final long serialVersionUID = 9187329209753189865L;

    private Usuario usuario;

    private Long codigoPublicacao;

    public Curtida(Usuario usuario, Long codigoPublicacao) {
        this.usuario = usuario;
        this.codigoPublicacao = codigoPublicacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getCodigoPublicacao() {
        return codigoPublicacao;
    }

    public void setCodigoPublicacao(Long codigoPublicacao) {
        this.codigoPublicacao = codigoPublicacao;
    }

}