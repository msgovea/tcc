package com.mgovea.urmusic.entity;

/**
 * Created by Mateus on 27/10/2017.
 */

public class Email {

    private Long codigoRemetente;
    private String texto;
    private String destinatario;

    public Email(Long codigoRemetente, String texto, String destinatario) {
        this.codigoRemetente = codigoRemetente;
        this.texto = texto;
        this.destinatario = destinatario;
    }

    public Email() {
    }

    public Long getCodigoRemetente() {
        return codigoRemetente;
    }

    public void setCodigoRemetente(Long codigoRemetente) {
        this.codigoRemetente = codigoRemetente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}