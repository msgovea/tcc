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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoNivel == null) ? 0 : codigoNivel.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NivelUsuario other = (NivelUsuario) obj;
		if (codigoNivel == null) {
			if (other.codigoNivel != null)
				return false;
		} else if (!codigoNivel.equals(other.codigoNivel))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

}
