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
import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Comentario;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.service.PublicacaoService;

@Service
public class PublicacaoServiceImpl implements PublicacaoService {

	@Autowired
	private PublicacaoDAO publicacaoDAO;

	@Autowired
	private ComentarioDAO comentarioDAO;

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
}
