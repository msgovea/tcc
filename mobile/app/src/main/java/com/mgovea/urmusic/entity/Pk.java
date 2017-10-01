package com.mgovea.urmusic.entity;

/**
 * Created by mgovea on 8/9/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Pk {

    @SerializedName("gostoMusical")
    @Expose
    private GostoMusical gostoMusical;

    public GostoMusical getGostoMusical() {
        return gostoMusical;
    }

    public void setGostoMusical(GostoMusical gostoMusical) {
        this.gostoMusical = gostoMusical;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
