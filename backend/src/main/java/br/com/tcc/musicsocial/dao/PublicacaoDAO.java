package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Publicacao;

public interface PublicacaoDAO extends BaseDAO<Publicacao> {
	List<Publicacao>  getPublicacoes(Integer idUsuario);

	List<Publicacao> getPublicacoesDeAmigos(Integer idUsuario);
}
