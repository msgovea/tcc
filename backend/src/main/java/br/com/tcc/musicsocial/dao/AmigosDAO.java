package br.com.tcc.musicsocial.dao;

import br.com.tcc.musicsocial.entity.Amigo;

public interface AmigosDAO extends BaseDAO<Amigo> {

	Amigo findByPk(Amigo amigo);

}
