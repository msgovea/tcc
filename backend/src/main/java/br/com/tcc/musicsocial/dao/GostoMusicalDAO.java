package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.UsuarioGostoMusical;

public interface GostoMusicalDAO extends BaseDAO<UsuarioGostoMusical> {

	List<GostoMusical> findAllGostos();
}
