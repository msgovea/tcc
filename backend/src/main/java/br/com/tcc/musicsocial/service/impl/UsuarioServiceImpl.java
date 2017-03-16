package br.com.tcc.musicsocial.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.SituacaoConta;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	@Transactional
	public UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario) {
		usuario.setSituacaoConta(SituacaoConta.ATIVA.getEntity());
		usuarioDAO.save(usuario);
		return usuario;
	}

	@Override
	public UsuarioDetalhe efetuarLogin(String email, String senha) {
		UsuarioDetalhe usuario = usuarioDAO.consultarPorEmail(email);
		if (usuario != null && usuario.getSenha().equals(senha)) {
			return usuario;
		}
		return null;
	}

}
