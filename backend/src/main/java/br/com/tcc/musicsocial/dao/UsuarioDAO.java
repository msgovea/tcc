package br.com.tcc.musicsocial.dao;

import br.com.tcc.musicsocial.entity.Usuario;

public interface UsuarioDAO extends BaseDAO<Usuario>{
	Usuario consultarPorEmail(String email);
}
