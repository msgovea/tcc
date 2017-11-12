package com.mgovea.urmusic.util;

/**
 * Created by Mateus on 12/11/2017.
 */

public enum TipoPerfilUsuario {
    MUSICO(1),
    AMADOR(2),
    COMUM(3);

    private int value;

    private TipoPerfilUsuario(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}