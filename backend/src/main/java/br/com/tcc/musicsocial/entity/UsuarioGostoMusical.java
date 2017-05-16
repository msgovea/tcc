package br.com.tcc.musicsocial.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "GUS_GOSTO_USUARIO")
@AssociationOverrides({
	@AssociationOverride(name = "pk.usuario", joinColumns = @JoinColumn(name = "GUS_USR_CODIGO")),
	@AssociationOverride(name = "pk.gostoMusical", joinColumns = @JoinColumn(name = "GUS_GMU_CODIGO"))
})
public class UsuarioGostoMusical {
	
	@EmbeddedId
	private UsuarioGostoMusicalPk pk;
	
	@Column(name = "GUS_PREFERIDO")
	private Boolean favorito;

	public UsuarioGostoMusicalPk getPk() {
		return pk;
	}

	public void setPk(UsuarioGostoMusicalPk pk) {
		this.pk = pk;
	}

	public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}
	
}
