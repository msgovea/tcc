package br.com.tcc.musicsocial.dao;

import java.util.List;

import br.com.tcc.musicsocial.entity.Usuario;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;

public interface UsuarioDAO extends BaseDAO<UsuarioDetalhe>{
	UsuarioDetalhe consultarPorEmail(String email);
	List<UsuarioDetalhe> consultarPorNome(String nome);
	Integer consultarQtdSeguidores(Usuario usuario);
}
