package br.com.tcc.musicsocial.dto;

import java.util.List;

public class CadastrarGostosMusicaisRequest {
	private Integer codigoUsuario;
	private List<Integer> codigosGostosMusicais;
	private Integer favorito;

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public List<Integer> getCodigosGostosMusicais() {
		return codigosGostosMusicais;
	}

	public void setCodigosGostosMusicais(List<Integer> codigosGostosMusicais) {
		this.codigosGostosMusicais = codigosGostosMusicais;
	}

	public Integer getFavorito() {
		return favorito;
	}

	public void setFavorito(Integer favorito) {
		this.favorito = favorito;
	}
	
}
