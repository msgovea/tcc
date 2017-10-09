package br.com.tcc.musicsocial.service;

import java.util.List;

import br.com.tcc.musicsocial.entity.Comentario;
import br.com.tcc.musicsocial.entity.Curtida;
import br.com.tcc.musicsocial.entity.Publicacao;

public interface PublicacaoService {

	Publicacao cadastrarPublicacao(Publicacao publicacao);
	
	Boolean removerPublicacao(Long codigo);

	Boolean comentarPublicacao(Comentario comentario);

	List<Comentario> listarComentarios(Long codigoPublicacao);

	Integer curtir(Curtida curtida);

	List<Publicacao> getPublicacoesDeAmigos(Integer idUsuario);

	List<Publicacao> getPublicacoesEmAlta();

	Boolean removerComentario(Long codigoComentario);

	Boolean impulsionarPublicacao(Long codigoPublicacao);

	List<Publicacao> getPublicacoes(String idUsuario);
}
