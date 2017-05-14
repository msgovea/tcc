package br.edu.puccamp.app.gosto_musical;

public class Gosto {
    private String gosto;
    private Boolean selecionado;
    private Boolean favorito;

    public Gosto(String gosto, Boolean selecionado, Boolean favorito) {
        this.gosto = gosto;
        this.selecionado = selecionado;
        this.favorito = favorito;
    }

    public String getGosto() {
        return gosto;
    }

    public void setGosto(String gosto) {
        this.gosto = gosto;
    }

    public Boolean getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Boolean selecionado) {
        this.selecionado = selecionado;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gosto gosto1 = (Gosto) o;

        if (gosto != null ? !gosto.equals(gosto1.gosto) : gosto1.gosto != null) return false;
        if (selecionado != null ? !selecionado.equals(gosto1.selecionado) : gosto1.selecionado != null)
            return false;
        return favorito != null ? favorito.equals(gosto1.favorito) : gosto1.favorito == null;

    }

    @Override
    public int hashCode() {
        int result = gosto != null ? gosto.hashCode() : 0;
        result = 31 * result + (selecionado != null ? selecionado.hashCode() : 0);
        result = 31 * result + (favorito != null ? favorito.hashCode() : 0);
        return result;
    }
}
