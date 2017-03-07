package br.com.tcc.musicsocial.entity;

import java.sql.Date;

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
@Table(name = "USR_USUARIO")
@SequenceGenerator(name = "GERADOR_COD_USR", sequenceName = "SEQ_COD_USUARIO", allocationSize = 1)
public class Usuario {
	
	@Id
	@Column(name = "USR_COD")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_COD_USR")
	private Integer codigoUsuario;
	
	@Column(name = "USR_NOME")
	private String nome;
	
	@Column(name = "USR_SOBRENOME")
	private String sobrenome;
	
	@Column(name = "USR_EMAIL")
	private String email;
	
	@Column(name = "USR_SENHA")
	private String senha;
	
	@Column(name = "USR_DATA_NASC")
	private Date dataNascimento;
	
	@Column(name = "USR_CIDADE")
	private String cidade;
	
	@Column(name = "USR_ESTADO")
	private String estado;
	
	@Column(name = "USR_PAIS")
	private String pais;
	
	@ManyToOne
	@JoinColumn(name = "USR_NIVEL_USER")
	private NivelUsuario nivelUsuario;
	
	@ManyToOne
	@JoinColumn(name = "USR_SITUACAO_CONTA")
	private SituacaoConta situacaoConta;
	
	@ManyToOne
	@JoinColumn(name = "USR_TIPO_CONEXAO")
	private TipoConexao tipoConexao;

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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

	public NivelUsuario getNivelUsuario() {
		return nivelUsuario;
	}

	public void setNivelUsuario(NivelUsuario nivelUsuario) {
		this.nivelUsuario = nivelUsuario;
	}

	public SituacaoConta getSituacaoConta() {
		return situacaoConta;
	}

	public void setSituacaoConta(SituacaoConta situacaoConta) {
		this.situacaoConta = situacaoConta;
	}

	public TipoConexao getTipoConexao() {
		return tipoConexao;
	}

	public void setTipoConexao(TipoConexao tipoConexao) {
		this.tipoConexao = tipoConexao;
	}
}
