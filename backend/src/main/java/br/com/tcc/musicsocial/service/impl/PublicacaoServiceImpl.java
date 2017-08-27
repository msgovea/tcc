package br.com.tcc.musicsocial.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import br.com.tcc.musicsocial.dao.ComentarioDAO;
import br.com.tcc.musicsocial.dao.CurtidasDAO;
import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Comentario;
import br.com.tcc.musicsocial.entity.Curtida;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.service.PublicacaoService;
import br.com.tcc.musicsocial.service.UsuarioService;

@Service
public class PublicacaoServiceImpl implements PublicacaoService {

	@Autowired
	private PublicacaoDAO publicacaoDAO;

	@Autowired
	private ComentarioDAO comentarioDAO;

	@Autowired
	private CurtidasDAO curtidasDAO;
	
	@Autowired
	private UsuarioService usuarioService;

	@Override
	public List<Publicacao> getPublicacoes(String idUsuario) {
		Integer id = Integer.parseInt(new String(Base64Utils.decodeFromString(idUsuario)));
		return publicacaoDAO.getPublicacoes(id);
	}

	@Override
	@Transactional
	public Publicacao cadastrarPublicacao(Publicacao publicacao) {
		if (!isPublicacaoValida(publicacao)) {
			return null;
		}
		publicacao.setDataPublicacao(new Date(Calendar.getInstance().getTimeInMillis()));
		publicacao.setAtiva(true);
		publicacaoDAO.save(publicacao);
		return publicacao;
	}

	private boolean isPublicacaoValida(Publicacao publicacao) {
		if (publicacao.getUsuario() == null || publicacao.getUsuario().getCodigoUsuario() == null) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public Boolean removerPublicacao(Long codigo) {
		Publicacao publicacao = publicacaoDAO.find(codigo);
		if (publicacao != null) {
			publicacao.setAtiva(false);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Boolean comentarPublicacao(Comentario comentario) {
		if (isComentarioValido(comentario)) {
			comentarioDAO.save(comentario);
			return true;
		}
		return false;
	}

	private boolean isComentarioValido(Comentario comentario) {
		return comentario != null && comentario.getCodigoPublicacao() != null && comentario.getUsuario() != null
				&& comentario.getUsuario().getCodigoUsuario() != null
				&& !StringUtils.isEmpty(comentario.getComentario());
	}

	@Override
	public List<Comentario> listarComentarios(Long codigoPublicacao) {
		return comentarioDAO.listarComentarios(codigoPublicacao);
	}

	@Override
	@Transactional
	public Integer curtir(Curtida curtida) {
		if (curtida.getUsuario() == null || curtida.getUsuario().getCodigoUsuario() == null || curtida.getCodigoPublicacao() == null || publicacaoDAO.find(curtida.getCodigoPublicacao()) == null || usuarioService.buscarPorId(curtida.getUsuario().getCodigoUsuario()) == null) {
			return 0;
		}
		if (curtidasDAO.findByPk(curtida.getUsuario().getCodigoUsuario(), curtida.getCodigoPublicacao()) == null) {
			curtidasDAO.save(curtida);
			return 1;
		} else {
			curtidasDAO.remove(curtida);
			return 2;
		}
	}
}
