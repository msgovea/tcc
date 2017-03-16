package br.com.tcc.musicsocial.dao;

import br.com.tcc.musicsocial.entity.UsuarioDetalhe;

public interface UsuarioDAO extends BaseDAO<UsuarioDetalhe>{
	UsuarioDetalhe consultarPorEmail(String email);
}
