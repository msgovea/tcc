package com.mgovea.urmusic.entity;

/**
 * Created by Mateus on 27/10/2017.
 */

public class Email {

    private Integer codigoEmail;
    private String remetente;
    private String assunto;
    private String mensagem;
    private String destinatario;

    public Email(String assunto, String mensagem, String destinatario) {
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.destinatario = destinatario;
    }

    public Integer getCodigoEmail() {
        return codigoEmail;
    }

    public void setCodigoEmail(Integer codigoEmail) {
        this.codigoEmail = codigoEmail;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}