package com.mgovea.urmusic.entity;

/**
 * Created by mgovea on 8/9/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class GostosMusicai {

    @SerializedName("pk")
    @Expose
    private Pk pk;
    @SerializedName("favorito")
    @Expose
    private Boolean favorito;

    public Pk getPk() {
        return pk;
    }

    public void setPk(Pk pk) {
        this.pk = pk;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}