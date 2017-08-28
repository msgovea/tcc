package br.edu.puccamp.app.entity;

/**
 * Created by Mateus on 20/08/2017.
 */


public class Comentario {

    private Long codigo;
    private Long codigoPublicacao;
    private Usuario usuario;
    private String comentario;

    public Comentario(Long codigo, Long codigoPublicacao, Usuario usuario, String comentario) {
        this.codigo = codigo;
        this.codigoPublicacao = codigoPublicacao;
        this.usuario = usuario;
        this.comentario = comentario;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigoPublicacao() {
        return codigoPublicacao;
    }

    public void setCodigoPublicacao(Long codigoPublicacao) {
        this.codigoPublicacao = codigoPublicacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

}
