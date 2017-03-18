package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NVU_NIVEL_USUARIO")
public class NivelUsuario {
	
	@Id
	@Column(name = "NVU_CODIGO")
	private Integer codigoNivel;
	
	@Column(name = "NVU_TIPO")
	private String tipo;
	
	public NivelUsuario() {
		//empty
	}

	public NivelUsuario(Integer codigoNivel, String tipo) {
		this.codigoNivel = codigoNivel;
		this.tipo = tipo;
	}

	public Integer getCodigoNivel() {
		return codigoNivel;
	}

	public void setCodigoNivel(Integer codigoNivel) {
		this.codigoNivel = codigoNivel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
