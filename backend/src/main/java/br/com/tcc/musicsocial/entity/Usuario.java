package br.com.tcc.musicsocial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USR_USUARIO")
@SequenceGenerator(name = "GERADOR_COD_USR", sequenceName = "SEQ_COD_USUARIO", allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@Column(name = "USR_CODIGO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GERADOR_COD_USR")
	private Integer codigoUsuario;
	
	@Column(name = "USR_EMAIL")
	private String email;
	
	@Column(name = "USR_SENHA")
	private String senha;
	
	@Column(name = "USR_CAD_CONFIRM")
	private Boolean cadastroConfimado;
	
	@ManyToOne
	@JoinColumn(name = "USR_NVU_NIVEL_USUARIO")
	private NivelUsuario nivelUsuario;
	
	@ManyToOne
	@JoinColumn(name = "USR_STC_SITUACAO_CONTA")
	private SituacaoConta situacaoConta;
	
	@ManyToOne
	@JoinColumn(name = "USR_TPC_TIPO_CONEXAO")
	private TipoConexao tipoConexao;

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
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

	public Boolean getCadastroConfimado() {
		return cadastroConfimado;
	}

	public void setCadastroConfimado(Boolean cadastroConfimado) {
		this.cadastroConfimado = cadastroConfimado;
	}

}
