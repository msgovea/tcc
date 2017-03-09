package br.com.tcc.musicsocial.service;

import br.com.tcc.musicsocial.entity.Usuario;

public interface UsuarioService {
	Usuario cadastrarUsuario(Usuario usuario);
	Usuario efetuarLogin(String email, String senha);
}
