package com.mgovea.urmusic.entity;

import java.util.ArrayList;

public class Publicacao {

    public Publicacao(Usuario usuario, String conteudo) {
        this.usuario = usuario;
        this.conteudo = conteudo;
    }

    public Publicacao(Usuario usuario, String conteudo, byte[] imagem) {
        this.usuario = usuario;
        this.conteudo = conteudo;
        this.imagem = imagem;
    }

    public Publicacao(Long codigo) {
        this.codigo = codigo;
    }

    public Publicacao() {
    }

    private Long codigo;

    private String conteudo;

    private byte[] imagem;

    private boolean temImagem;

    private String dataPublicacao;

    private String video;

    private Boolean ativa;

    private Usuario usuario;

    private ArrayList<Usuario> likes;

    private Long qtdComentarios = Long.valueOf(0);

    public boolean isTemImagem() {
        return temImagem;
    }

    public void setTemImagem(boolean temImagem) {
        this.temImagem = temImagem;
    }

    public ArrayList<Usuario> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Usuario> likes) {
        this.likes = likes;
    }

    public void addLike(Usuario usuario) {
        this.likes.add(usuario);
    }

    public void removeLike(Usuario usuario) {
        for (Usuario u :
                this.likes) {
            if (u.getCodigoUsuario() == usuario.getCodigoUsuario()) {
                this.likes.remove(u);
            }
        }
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Long getQtdComentarios() {
        return qtdComentarios;
    }

    public void setQtdComentarios(Long qtdComentarios) {
        this.qtdComentarios = qtdComentarios;
    }

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