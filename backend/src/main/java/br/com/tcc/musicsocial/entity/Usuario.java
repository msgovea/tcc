package br.com.tcc.musicsocial.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@Column(name = "USR_DT_INSRT")
	private Date dataInsrt;
	
	@ManyToOne
	@JoinColumn(name = "USR_NVU_NIVEL_USUARIO")
	private NivelUsuario nivelUsuario;
	
	@ManyToOne
	@JoinColumn(name = "USR_STC_SITUACAO_CONTA")
	private SituacaoConta situacaoConta;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.usuario")
	private List<UsuarioGostoMusical> gostosMusicais;
	
	@Column(name = "USR_TIPO_PERFIL")
	private Integer tipoPerfil;

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

	public Date getDataInsrt() {
		return dataInsrt;
	}

	public void setDataInsrt(Date dataInsrt) {
		this.dataInsrt = dataInsrt;
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
	
	public List<UsuarioGostoMusical> getGostosMusicais() {
		return gostosMusicais;
	}

	public void setGostosMusicais(List<UsuarioGostoMusical> gostosMusicais) {
		this.gostosMusicais = gostosMusicais;
	}
	
	public Integer getTipoPerfil() {
		return tipoPerfil;
	}

	public void setTipoPerfil(Integer tipoPerfil) {
		this.tipoPerfil = tipoPerfil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoUsuario == null) ? 0 : codigoUsuario.hashCode());
		result = prime * result + ((dataInsrt == null) ? 0 : dataInsrt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gostosMusicais == null) ? 0 : gostosMusicais.hashCode());
		result = prime * result + ((nivelUsuario == null) ? 0 : nivelUsuario.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((situacaoConta == null) ? 0 : situacaoConta.hashCode());
		result = prime * result + ((tipoPerfil == null) ? 0 : tipoPerfil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigoUsuario == null) {
			if (other.codigoUsuario != null)
				return false;
		} else if (!codigoUsuario.equals(other.codigoUsuario))
			return false;
		if (dataInsrt == null) {
			if (other.dataInsrt != null)
				return false;
		} else if (!dataInsrt.equals(other.dataInsrt))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gostosMusicais == null) {
			if (other.gostosMusicais != null)
				return false;
		} else if (!gostosMusicais.equals(other.gostosMusicais))
			return false;
		if (nivelUsuario == null) {
			if (other.nivelUsuario != null)
				return false;
		} else if (!nivelUsuario.equals(other.nivelUsuario))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (situacaoConta == null) {
			if (other.situacaoConta != null)
				return false;
		} else if (!situacaoConta.equals(other.situacaoConta))
			return false;
		if (tipoPerfil == null) {
			if (other.tipoPerfil != null)
				return false;
		} else if (!tipoPerfil.equals(other.tipoPerfil))
			return false;
		return true;
	}


}
