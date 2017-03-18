package br.com.tcc.musicsocial.service;

import br.com.tcc.musicsocial.entity.UsuarioDetalhe;

public interface UsuarioService {
	UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario);
	UsuarioDetalhe efetuarLogin(String email, String senha);
}
