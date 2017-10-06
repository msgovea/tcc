package br.com.tcc.musicsocial.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AMG_AMIGOS")
public class Amigo implements Serializable {
	
	private static final long serialVersionUID = 5002382079162001593L;

	@Id
	@ManyToOne
	@JoinColumn(name = "AMG_SEGUIDOR", nullable = false, updatable = false)
	private UsuarioDetalhe segue;

	@Id
	@ManyToOne
	@JoinColumn(name = "AMG_SEGUIDO", nullable = false, updatable = false)
	private UsuarioDetalhe seguido;

	public UsuarioDetalhe getSegue() {
		return segue;
	}

	public void setSegue(UsuarioDetalhe segue) {
		this.segue = segue;
	}

	public UsuarioDetalhe getSeguido() {
		return seguido;
	}

	public void setSeguido(UsuarioDetalhe seguido) {
		this.seguido = seguido;
	}

}
