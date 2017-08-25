package br.com.tcc.musicsocial.dao;

import br.com.tcc.musicsocial.entity.Curtida;

public interface CurtidasDAO extends BaseDAO<Curtida> {

	Curtida findByPk(Integer idUsuario, Long idPublicacao);

}
