package br.com.tcc.musicsocial.service;

import br.com.tcc.musicsocial.entity.UsuarioDetalhe;

public interface UsuarioService {
	UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario);
	UsuarioDetalhe efetuarLogin(String email, String senha);
	Boolean confirmarEmail(String idEncoded, String emailEncoded);
	Boolean recuperarSenha(String email);
	Boolean redefinirSenha(String idBase, String emailHash, String senhaHash);
}
