package com.mgovea.urmusic.entity;

public class SituacaoConta {

	private Integer codigoSituacaoConta;
	
	private String tipo;
	
	public SituacaoConta(Integer codigo, String tipo) {
		this.codigoSituacaoConta = codigo;
		this.tipo = tipo;
	}

	public SituacaoConta() {
		//empty
	}

	public Integer getCodigoSituacaoConta() {
		return codigoSituacaoConta;
	}

	public void setCodigoSituacaoConta(Integer codigoSituacaoConta) {
		this.codigoSituacaoConta = codigoSituacaoConta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
