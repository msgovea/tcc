package com.mgovea.urmusic.entity;

/**
 * Created by Mateus on 30/09/2017.
 */

public class ImagemUsuario {
    Long idUsuario;
    byte[] imagem;

    public ImagemUsuario(Long idUsuario, byte[] imagem) {
        this.idUsuario = idUsuario;
        this.imagem = imagem;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
