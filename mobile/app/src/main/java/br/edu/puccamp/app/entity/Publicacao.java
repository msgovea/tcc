package br.edu.puccamp.app.entity;

import java.util.ArrayList;

public class Publicacao {

    public Publicacao(Usuario usuario, String conteudo) {
        this.usuario = usuario;
        this.conteudo = conteudo;
    }

    public Publicacao() {
    }

    private Long codigo;

    private String conteudo;

    private byte[] imagem;

    private String dataPublicacao;

    private Boolean ativa;

    private Usuario usuario;

    /* TODO MGOVEA - REMOVER QND WAGNER IMPLEMENTAR */

    private Boolean curtido = false;

    private Long curtidas = Long.valueOf(0);

    private ArrayList<Usuario> likes;

    private Long qtdComentarios = Long.valueOf(0);

    public Boolean getCurtido() {
        return curtido;
    }

    public void setCurtido(Boolean curtido) {
        this.curtido = curtido;
    }

    public Long getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(Long curtidas) {
        this.curtidas = curtidas;
    }

    public Long getQtdComentarios() {
        return qtdComentarios;
    }

    public void setQtdComentarios(Long qtdComentarios) {
        this.qtdComentarios = qtdComentarios;
    }

    // END TODO MGOVEA - REMOVER QND WAGNER IMPLEMENTAR

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}