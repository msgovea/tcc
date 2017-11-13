package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Publicacao;

public interface PublicacaoDAO extends BaseDAO<Publicacao> {
	List<Publicacao>  getPublicacoes(Integer idUsuario);

	List<Publicacao> getPublicacoesDeAmigos(Integer idUsuario);

	Integer consultarQtdComentarios(Publicacao publicacao);

	Publicacao getPublicacao(Long idPublicacao);

	List<Publicacao> getPublicacoesEmAlta(List<Integer> gostos);

}
