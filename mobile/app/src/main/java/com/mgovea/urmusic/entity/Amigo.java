package com.mgovea.urmusic.entity;

import java.io.Serializable;

/**
 * Created by mgovea on 28/08/2017.
 */

public class Amigo implements Serializable {

    private static final long serialVersionUID = 5002382079162001593L;

    private Usuario segue;
    private Usuario seguido;

    public Usuario getSegue() {
        return segue;
    }

    public void setSegue(Usuario segue) {
        this.segue = segue;
    }

    public Usuario getSeguido() {
        return seguido;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
    }

}