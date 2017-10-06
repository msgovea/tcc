package br.com.tcc.musicsocial.service;

import java.util.List;

import br.com.tcc.musicsocial.entity.Amigo;
import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.util.ReturnType;

public interface UsuarioService {
	UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario);
	UsuarioDetalhe efetuarLogin(String email, String senha);
	Boolean confirmarEmail(String idEncoded, String emailEncoded);
	Boolean recuperarSenha(String email);
	Boolean redefinirSenha(String idBase, String emailHash, String senhaHash);
	
	Boolean cadastrarGostoMusical(Integer codUsuario, List<Integer> codGostosMusicais, Integer favorito);
	List<GostoMusical> getGostos();
	UsuarioDetalhe atualizarUsuario(UsuarioDetalhe usuario);

	UsuarioDetalhe buscarPorId(Integer id);
	List<UsuarioDetalhe> buscarPorNome(String nome);
	ReturnType seguir(Amigo amigo);
	List<UsuarioDetalhe> amigosSugeridos(Long idUsuario);
}
