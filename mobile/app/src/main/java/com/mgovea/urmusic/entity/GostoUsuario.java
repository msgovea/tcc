package com.mgovea.urmusic.entity;

import java.util.ArrayList;

/**
 * Created by mgovea on 06/06/2017.
 */

public class GostoUsuario {
    Long codigoUsuario;
    ArrayList<Integer> codigosGostosMusicais;
    Integer favorito;

    public GostoUsuario() {
    }

    public GostoUsuario(Long codigoUsuario, ArrayList<Integer> codigosGostosMusicais, Integer favorito) {

        this.codigoUsuario = codigoUsuario;
        this.codigosGostosMusicais = codigosGostosMusicais;
        this.favorito = favorito;
    }

    public Long getCodigoUsuario() {

        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public ArrayList<Integer> getCodigosGostosMusicais() {
        return codigosGostosMusicais;
    }

    public void setCodigosGostosMusicais(ArrayList<Integer> codigosGostosMusicais) {
        this.codigosGostosMusicais = codigosGostosMusicais;
    }

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
    }
}
