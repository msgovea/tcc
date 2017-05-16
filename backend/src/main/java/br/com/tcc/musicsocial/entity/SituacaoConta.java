package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STC_SITUACAO_CONTA")
public class SituacaoConta {
	
	@Id
	@Column(name = "STC_CODIGO")
	private Integer codigoSituacaoConta;
	
	@Column(name = "STC_TIPO")
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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SituacaoConta other = (SituacaoConta) obj;
		if (codigoSituacaoConta == null) {
			if (other.codigoSituacaoConta != null)
				return false;
		} else if (!codigoSituacaoConta.equals(other.codigoSituacaoConta))
			return false;
		return true;
	}
	
}
