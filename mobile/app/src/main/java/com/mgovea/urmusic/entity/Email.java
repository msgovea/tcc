package com.mgovea.urmusic.entity;

/**
 * Created by Mateus on 27/10/2017.
 */

public class Email {

    private Long codRemetente;
    private String texto;
    private String destinatario;

    public Email(Long codRemetente, String texto, String destinatario) {
        this.codRemetente = codRemetente;
        this.texto = texto;
        this.destinatario = destinatario;
    }

    public Email() {
    }

    public Long getCodRemetente() {
        return codRemetente;
    }

    public void setCodRemetente(Long codRemetente) {
        this.codRemetente = codRemetente;
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