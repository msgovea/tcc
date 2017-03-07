package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TPC_TIPO_CONEXAO")
public class TipoConexao {
	
	@Id
	@Column(name = "TPC_COD")
	private Integer codigoTipoConexao;
	
	@Column(name = "TPC_TIPO")
	private String tipo;

	public Integer getCodigoTipoConexao() {
		return codigoTipoConexao;
	}

	public void setCodigoTipoConexao(Integer codigoTipoConexao) {
		this.codigoTipoConexao = codigoTipoConexao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
