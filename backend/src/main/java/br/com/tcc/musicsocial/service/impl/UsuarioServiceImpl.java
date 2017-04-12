package br.com.tcc.musicsocial.service.impl;

import java.math.BigInteger;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.service.EmailService;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.GeradorHash;
import br.com.tcc.musicsocial.util.SituacaoConta;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final String ASSUNTO = "Confirmação de Email";

	private static final String HOST = "listbuy.me";
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	@Transactional
	public UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario) {
		UsuarioDetalhe usuarioBanco = usuarioDAO.consultarPorEmail(usuario.getEmail());
		if (usuarioBanco != null) {
			throw new RuntimeException("Usuario já cadastrado!");
		}
		
		usuario.setSituacaoConta(SituacaoConta.ATIVA.getEntity());
		usuarioDAO.save(usuario);
		enviarEmailConfirmacao(usuario);
		return usuario;
	}

	private void enviarEmailConfirmacao(UsuarioDetalhe u) {
		String id = Base64Utils.encodeToString(u.getCodigoUsuario().toString().getBytes());
		String emailHash = GeradorHash.gerarHash(u.getEmail());
		String textoEmail = String.format(montarEmailConfirmacao(), u.getNome(), HOST, id, emailHash);
		try {
			emailService.enviarEmail(ASSUNTO, u.getEmail(), textoEmail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	private String montarEmailConfirmacao() {
		StringBuilder email = new StringBuilder();
		email.append("Olá %s, <br>");
		email.append("Seu cadastro foi realizado com sucesso! ");
		email.append("Precisamos apenas que nos confirme seu email clicando no link abaixo. <br>");
		email.append("<a href=\"http://%s/#/app/confirmacao/%s/%s\">Confirmar Cadastro</a> <br>");
		return email.toString();
	}
	
	@Override
	public UsuarioDetalhe efetuarLogin(String email, String senha) {
		UsuarioDetalhe usuario = usuarioDAO.consultarPorEmail(email);
		if (usuario != null && usuario.getSenha().equals(senha)) {
			return usuario;
		}
		return null;
	}

	@Override
	@Transactional
	public Boolean confirmarEmail(String idEncoded, String emailEncoded) {
		byte[] array = Base64Utils.decodeFromString(idEncoded);
		Integer id = Integer.parseInt(new String(array));
		UsuarioDetalhe usuario;
		usuario = usuarioDAO.find(id);
		
		if(usuario != null && emailEncoded.equals(GeradorHash.gerarHash(usuario.getEmail()))) {
			usuario.setCadastroConfimado(true);
			return true;
		}

		return false;
	}

}
