package com.mgovea.urmusic.entity;

/**
 * Created by Mateus on 28/10/2017.
 */

public class Pais {
    String codigoArea;
    String pais;
    String Sigla;

    public Pais() {
    }

    public Pais(String codigoArea, String pais, String sigla) {
        this.codigoArea = codigoArea;
        this.pais = pais;
        Sigla = sigla;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSigla() {
        return Sigla;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }
}
