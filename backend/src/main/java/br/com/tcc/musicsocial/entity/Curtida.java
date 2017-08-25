package br.com.tcc.musicsocial.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PBL_LIKES")
public class Curtida implements Serializable{
	
	private static final long serialVersionUID = 9187329209753189865L;

	@Id
	@ManyToOne
	@JoinColumn(name = "PBL_USR_CODIGO")
	private UsuarioDetalhe usuario;
	
	@Id
	@Column(name = "PBL_PBC_CODIGO")
	private Long codigoPublicacao;

	public UsuarioDetalhe getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDetalhe usuario) {
		this.usuario = usuario;
	}

	public Long getCodigoPublicacao() {
		return codigoPublicacao;
	}

	public void setCodigoPublicacao(Long codigoPublicacao) {
		this.codigoPublicacao = codigoPublicacao;
	}
	
}
