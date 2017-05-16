package br.com.tcc.musicsocial.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class UsuarioGostoMusicalPk implements Serializable {
	
	private static final long serialVersionUID = 3832695674117057109L;

	@ManyToOne
	private GostoMusical gostoMusical;
	
	@ManyToOne
	private Usuario usuario;
	
	

	public UsuarioGostoMusicalPk() {
		//empty
	}

	public UsuarioGostoMusicalPk(GostoMusical gostoMusical, Usuario usuario) {
		super();
		this.gostoMusical = gostoMusical;
		this.usuario = usuario;
	}

	public GostoMusical getGostoMusical() {
		return gostoMusical;
	}

	public void setGostoMusical(GostoMusical gostoMusical) {
		this.gostoMusical = gostoMusical;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGostoMusicalPk other = (UsuarioGostoMusicalPk) obj;
		if (gostoMusical == null) {
			if (other.gostoMusical != null)
				return false;
		} else if (!gostoMusical.equals(other.gostoMusical))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
}
