package br.com.tcc.musicsocial.service;

import java.util.List;

import br.com.tcc.musicsocial.entity.Publicacao;

public interface PublicacaoService {

	List<Publicacao> getPublicacoes(String idUsuario);
	
	Publicacao cadastrarPublicacao(Publicacao publicacao);
}
