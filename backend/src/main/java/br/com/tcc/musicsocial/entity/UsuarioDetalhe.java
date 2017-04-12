package br.com.tcc.musicsocial.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "USD_USUARIO_DETALHE")
@PrimaryKeyJoinColumn(name = "USR_CODIGO")
public class UsuarioDetalhe extends Usuario {

	@Column(name = "USD_NOME")
	private String nome;
	
	@Column(name = "USD_NICKNAME")
	private String apelido;

	@Column(name = "USD_DATA_NASC")
	private Date dataNascimento;
	
	@Column(name = "USD_CIDADE")
	private String cidade;
	
	@Column(name = "USD_ESTADO")
	private String estado;
	
	@Column(name = "USD_PAIS")
	private String pais;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
}
