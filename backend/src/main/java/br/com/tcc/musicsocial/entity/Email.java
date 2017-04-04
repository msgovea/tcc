package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "EML_EMAIL")
@SequenceGenerator(name = "GERADOR_COD_EMAIL", sequenceName = "SEQ_COD_EMAIL", allocationSize = 1)
public class Email {
	
	@Id
	@Column(name = "EML_CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_COD_EMAIL")
	private Integer codigoEmail;
	
	@Column(name = "EML_REMETENTE")
	private String remetente;
	
	@Column(name = "EML_ASSUNTO")
	private String assunto;
	
	@Column(name = "EML_MENSAGEM")
	private String mensagem;

	@Transient
	private String destinatario;
	
	public Integer getCodigoEmail() {
		return codigoEmail;
	}

	public void setCodigoEmail(Integer codigoEmail) {
		this.codigoEmail = codigoEmail;
	}

	public String getRemetente() {
		return remetente;
	}

	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
}
