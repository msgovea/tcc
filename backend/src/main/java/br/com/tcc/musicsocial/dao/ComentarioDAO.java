package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Comentario;

public interface ComentarioDAO extends BaseDAO<Comentario> {

	List<Comentario> listarComentarios(Long codigoPublicacao);

}
