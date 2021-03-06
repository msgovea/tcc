package br.com.tcc.musicsocial.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import br.com.tcc.musicsocial.dao.AmigosDAO;
import br.com.tcc.musicsocial.dao.GostoMusicalDAO;
import br.com.tcc.musicsocial.dao.UsuarioDAO;
import br.com.tcc.musicsocial.entity.Amigo;
import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.Usuario;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.entity.UsuarioGostoMusical;
import br.com.tcc.musicsocial.entity.UsuarioGostoMusicalPk;
import br.com.tcc.musicsocial.service.EmailService;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.GeradorHash;
import br.com.tcc.musicsocial.util.ReturnType;
import br.com.tcc.musicsocial.util.SituacaoConta;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final String ASSUNTO = "Confirmação de Email";

	private static final String HOST = "urmusic.me";

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	private EmailService emailService;

	@Autowired
	private GostoMusicalDAO gostoMusicalDAO;
	
	@Autowired
	private AmigosDAO amigosDAO;

	@Override
	@Transactional
	public UsuarioDetalhe cadastrarUsuario(UsuarioDetalhe usuario) {
		UsuarioDetalhe usuarioBanco = usuarioDAO.consultarPorEmail(usuario.getEmail());
		if (usuarioBanco != null) {
			return null;
		}

		usuario.setSituacaoConta(SituacaoConta.AGUARDANDO_CONFIRMACAO.getEntity());
		usuario.setDataInsrt(new Date(Calendar.getInstance().getTimeInMillis()));
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
		email.append("<a href=\"http://%s/#/access/confirmarCadastro/%s/%s\">Confirmar Cadastro</a> <br>");
		return email.toString();
	}

	private String montarEmailRecuperacao() {
		StringBuilder email = new StringBuilder();
		email.append("Olá %s, <br>");
		email.append("Sua solicitação de recuperação de senha foi realizada com sucesso! ");
		email.append("Altere sua senha clicando no link abaixo. <br>");
		email.append("<a href=\"http://%s/#/access/redefinirSenha/%s/%s\">Redefinir senha</a> <br>");
		return email.toString();
	}

	@Override
	public UsuarioDetalhe efetuarLogin(String email, String senha) {
		UsuarioDetalhe usuario = usuarioDAO.consultarPorEmail(email);
		if (usuario != null && usuario.getSenha().equals(senha)) {
			return popularQtdSeguidos(usuario);
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

		if (usuario != null && emailEncoded.equals(GeradorHash.gerarHash(usuario.getEmail()))) {
			usuario.setSituacaoConta(SituacaoConta.ATIVA.getEntity());
			return true;
		}

		return false;
	}

	@Override
	public Boolean recuperarSenha(String emailBase) {
		String email = new String(Base64Utils.decodeFromString(emailBase));
		UsuarioDetalhe usuario = usuarioDAO.consultarPorEmail(email);
		if (usuario != null) {
			String id = Base64Utils.encodeToString(usuario.getCodigoUsuario().toString().getBytes());
			String emailHash = GeradorHash.gerarHash(email);
			String textoEmail = String.format(montarEmailRecuperacao(), usuario.getNome(), HOST, id, emailHash);
			try {
				emailService.enviarEmail("Recuperação de Senha", usuario.getEmail(), textoEmail);
				return true;
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	@Transactional
	public Boolean redefinirSenha(String idBase, String emailHash, String senhaHash) {
		try {
			Integer idUsuario = Integer.parseInt(new String(Base64Utils.decodeFromString(idBase)));

			UsuarioDetalhe user = usuarioDAO.find(idUsuario);
			if (user != null && emailHash.equals(GeradorHash.gerarHash(user.getEmail()))) {
				user.setSenha(senhaHash);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean cadastrarGostoMusical(Integer codUsuario, List<Integer> codGostosMusicais, Integer favorito) {
		if (codUsuario == null || codGostosMusicais == null || codGostosMusicais.size() < 1) {
			return false;
		}

		Usuario user = new Usuario();
		user.setCodigoUsuario(codUsuario);
		for (Integer codGostoMusical : codGostosMusicais) {
			UsuarioGostoMusical ugm = new UsuarioGostoMusical();
			GostoMusical gosto = new GostoMusical();
			gosto.setCodigo(codGostoMusical);
			ugm.setPk(new UsuarioGostoMusicalPk(gosto, user));
			ugm.setFavorito(false);
			if (codGostoMusical.equals(favorito)) {
				ugm.setFavorito(true);
			}
			gostoMusicalDAO.save(ugm);
		}

		return true;
	}

	public List<GostoMusical> getGostos() {
		return gostoMusicalDAO.findAllGostos();
	}

	@Override
	@Transactional
	public UsuarioDetalhe atualizarUsuario(UsuarioDetalhe usuario) {
		if (usuario.getGostosMusicais() != null && !usuario.getGostosMusicais().isEmpty()) {
			usuario.setDataNascimento(Date.valueOf(usuario.getDataNascimento().toLocalDate().plusDays(1)));
			for (UsuarioGostoMusical usuarioGosto : usuario.getGostosMusicais()) {
				usuarioGosto.getPk().setUsuario(usuario);
			}
		}
		usuario.setSeguidores(usuarioDAO.find(usuario.getCodigoUsuario()).getSeguidores());
		return popularQtdSeguidos(usuarioDAO.update(usuario));
	}

	@Override
	public UsuarioDetalhe buscarPorId(Integer id) {
		return popularQtdSeguidos(usuarioDAO.find(id));
	}
	
	@Override
	public UsuarioDetalhe buscarPorEmail(String email) {
		return popularQtdSeguidos(usuarioDAO.consultarPorEmail(email));
	}

	@Override
	public List<UsuarioDetalhe> buscarPorNome(String nome) {
		String nomeDecodificado = new String(Base64Utils.decodeFromString(nome));
		return populaQtdSeguidos(usuarioDAO.consultarPorNome(nomeDecodificado));
	}
	
	@Override
	@Transactional
	public ReturnType seguir(Amigo amigo) {
		if(isAmigoValid(amigo)) {
			if(amigosDAO.findByPk(amigo) == null) {
				amigosDAO.save(amigo);
				return ReturnType.INSERIDO;
			} else {
				amigosDAO.remove(amigo);
				return ReturnType.REMOVIDO;
			}
		}
		return ReturnType.INVALIDO;
	}

	private boolean isAmigoValid(Amigo amigo) {
		return amigo != null && amigo.getSegue() != null && amigo.getSegue().getCodigoUsuario() != null
				&& amigo.getSeguido() != null && amigo.getSeguido().getCodigoUsuario() != null
				&& usuarioDAO.find(amigo.getSegue().getCodigoUsuario()) != null
				&& usuarioDAO.find(amigo.getSeguido().getCodigoUsuario()) != null;
	}
	
	private UsuarioDetalhe popularQtdSeguidos(UsuarioDetalhe usuario) {
		if (usuario != null) {
			usuario.setQtdSeguidos(usuarioDAO.consultarQtdSeguidores(usuario));
		}
		usuario.setCodigoSeguidores();
		return usuario;
	}
	
	private List<UsuarioDetalhe> populaQtdSeguidos(List<UsuarioDetalhe> usuarios) {
		for (UsuarioDetalhe usuario : usuarios) {
			popularQtdSeguidos(usuario);
		}
		return usuarios;
	}
	
	@Override
	public List<UsuarioDetalhe> amigosSugeridos(Long idUsuario) {
		return usuarioDAO.amigosSugeridos(idUsuario);
	}
}
