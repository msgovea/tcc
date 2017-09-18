package br.com.tcc.musicsocial.entity;

import java.util.Date;

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
@Table(name = "DEN_DENUNCIA")
@SequenceGenerator(name = "denunciaSeq", sequenceName = "SEQ_COD_DENUNCIA")
public class Denuncia {
	
	@Id
	@Column(name = "DEN_CODIGO")
	@GeneratedValue(generator = "denunciaSeq", strategy = GenerationType.SEQUENCE)
	private Long codigo;
	
	@JoinColumn(name = "DEN_PBC_CODIGO_PUBLICACAO")
	@ManyToOne
	private Publicacao publicacao;
	
	@JoinColumn(name = "DEN_USR_DENUNCIADO")
	@ManyToOne
	private UsuarioDetalhe denunciado;
	
	@JoinColumn(name = "DEN_USR_DENUNCIANTE")
	@ManyToOne
	private UsuarioDetalhe denunciante;
	
	@Column(name = "DEN_MOTIVO")
	private String motivo;
	
	@Column(name = "DEN_STATUS")
	private String status;
	
	@Column(name = "DEN_DATA")
	private Date data;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Publicacao getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(Publicacao publicacao) {
		this.publicacao = publicacao;
	}

	public UsuarioDetalhe getDenunciado() {
		return denunciado;
	}

	public void setDenunciado(UsuarioDetalhe denunciado) {
		this.denunciado = denunciado;
	}

	public UsuarioDetalhe getDenunciante() {
		return denunciante;
	}

	public void setDenunciante(UsuarioDetalhe denunciante) {
		this.denunciante = denunciante;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
