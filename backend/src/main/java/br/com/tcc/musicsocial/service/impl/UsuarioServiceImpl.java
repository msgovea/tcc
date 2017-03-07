package br.com.tcc.musicsocial.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.Usuario;
import br.com.tcc.musicsocial.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Override
	@Transactional
	public Usuario cadastrarUsuario(Usuario usuario) {
		usuarioDAO.save(usuario);
		return usuario;
	}

}
