package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "COM_COMENTARIOS")
@SequenceGenerator(name = "GERADOR_COD_COM", sequenceName = "SEQ_COD_COMENT", allocationSize = 1)
public class Comentario {
	
	@Id
	@Column(name = "COM_CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_COD_COM")
	private Long codigo;
	
	@Column(name = "COM_PBC_CODIGO")
	private Long codigoPublicacao;
	
	@ManyToOne
	@JoinColumn(name = "COM_USR_CODIGO")
	private UsuarioDetalhe usuario;
	
	@Column(name = "COM_COMENTARIO")
	private String comentario;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigoPublicacao() {
		return codigoPublicacao;
	}

	public void setCodigoPublicacao(Long codigoPublicacao) {
		this.codigoPublicacao = codigoPublicacao;
	}

	public UsuarioDetalhe getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDetalhe usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
