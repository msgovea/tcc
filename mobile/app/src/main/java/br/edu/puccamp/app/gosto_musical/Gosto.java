package br.edu.puccamp.app.gosto_musical;

import java.io.Serializable;

public class Gosto {
    private Integer codigo;
    private String descricao;
    private Boolean selecionado;
    private Boolean favorito;

    public Gosto(String descricao, Boolean selecionado, Boolean favorito) {
        this.descricao = descricao;
        this.favorito = favorito;
        this.selecionado = false;
    }

    public Gosto(Integer codigo, String descricao, Boolean selecionado, Boolean favorito) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.favorito = favorito;
        this.selecionado = false;
    }

    public Gosto(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.favorito = false;
        this.selecionado = false;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getSelecionado() {
        return selecionado == null ? false: selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    public Boolean getFavorito() {
        return favorito == null ? false: favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gosto gosto1 = (Gosto) o;

        if (descricao != null ? !descricao.equals(gosto1.descricao) : gosto1.descricao != null) return false;
        if (selecionado != null ? !selecionado.equals(gosto1.selecionado) : gosto1.selecionado != null)
            return false;
        return favorito != null ? favorito.equals(gosto1.favorito) : gosto1.favorito == null;

    }

    @Override
    public int hashCode() {
        int result = descricao != null ? descricao.hashCode() : 0;
        result = 31 * result + (selecionado != null ? selecionado.hashCode() : 0);
        result = 31 * result + (favorito != null ? favorito.hashCode() : 0);
        return result;
    }
}
