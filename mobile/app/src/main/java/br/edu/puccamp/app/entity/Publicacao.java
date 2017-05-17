package br.edu.puccamp.app.entity;

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