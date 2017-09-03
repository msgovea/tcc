package br.com.tcc.musicsocial.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "PBC_PUBLICACOES")
@SequenceGenerator(name = "GERADOR_COD_PBC", sequenceName = "SEQ_COD_PUBLICACAO", allocationSize = 1)
public class Publicacao {

	@Id
	@Column(name = "PBC_CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_COD_PBC")
	private Long codigo;

	@Column(name = "PBC_CONTEUDO")
	private String conteudo;

	@Column(name = "PBC_IMAGEM")
	@Lob
	private byte[] imagem;

	@Column(name = "PBC_DATA_PUBLICACAO")
	private Date dataPublicacao;

	@Column(name = "PBC_ATIVA")
	private Boolean ativa;

	@ManyToOne(targetEntity = UsuarioDetalhe.class)
	@JoinColumn(name = "PBC_USR_CODIGO", referencedColumnName = "USR_CODIGO")
	private UsuarioDetalhe usuario;

	@ManyToMany
	@JoinTable(name = "PBL_LIKES", joinColumns = {
		@JoinColumn(name = "PBL_PBC_CODIGO")
	}, inverseJoinColumns = {
		@JoinColumn(name = "PBL_USR_CODIGO")
	})
	private List<Usuario> likes;
	
	@Transient
	private Integer qtdComentarios;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
	}

	public UsuarioDetalhe getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDetalhe usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getLikes() {
		return likes;
	}

	public void setLikes(List<Usuario> likes) {
		this.likes = likes;
	}

	public Integer getQtdComentarios() {
		return qtdComentarios;
	}

	public void setQtdComentarios(Integer qtdComentarios) {
		this.qtdComentarios = qtdComentarios;
	}

}
