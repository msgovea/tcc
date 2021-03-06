package br.com.tcc.musicsocial.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import br.com.tcc.musicsocial.dao.ComentarioDAO;
import br.com.tcc.musicsocial.dao.CurtidasDAO;
import br.com.tcc.musicsocial.dao.GostoMusicalDAO;
import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Comentario;
import br.com.tcc.musicsocial.entity.Curtida;
import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.entity.UsuarioGostoMusical;
import br.com.tcc.musicsocial.service.FotoService;
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
	
	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private GostoMusicalDAO gostoMusicalDAO;

	@Override
	public List<Publicacao> getPublicacoes(String idUsuario) {
		Integer id = Integer.parseInt(new String(Base64Utils.decodeFromString(idUsuario)));
		return populaQtdComentarios(publicacaoDAO.getPublicacoes(id));
	}

	@Override
	public List<Publicacao> getPublicacoesDeAmigos(Integer idUsuario) {
		return populaQtdComentarios(publicacaoDAO.getPublicacoesDeAmigos(idUsuario));
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Publicacao cadastrarPublicacao(Publicacao publicacao) {
		if (!isPublicacaoValida(publicacao)) {
			return null;
		}
		publicacao.setDataPublicacao(new Date(Calendar.getInstance().getTimeInMillis()));
		publicacao.setAtiva(true);
		publicacao.setImpulsionada(false);
		publicacao.setTemImagem(false);
		publicacaoDAO.save(publicacao);
		if(publicacao.getImagem() != null) {
			publicacao.setTemImagem(true);
			fotoService.gravarImagemPublicacao(publicacao.getImagem(), publicacao.getCodigo());
		}
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
		if (curtida.getUsuario() == null || curtida.getUsuario().getCodigoUsuario() == null
				|| curtida.getCodigoPublicacao() == null || publicacaoDAO.find(curtida.getCodigoPublicacao()) == null
				|| usuarioService.buscarPorId(curtida.getUsuario().getCodigoUsuario()) == null) {
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

	@Override
	public Publicacao populaQtdComentarios(Publicacao publicacao) {
		if (publicacao != null) {
			publicacao.setQtdComentarios(publicacaoDAO.consultarQtdComentarios(publicacao));
		}
		return publicacao;
	}

	@Override
	public List<Publicacao> populaQtdComentarios(List<Publicacao> publicacoes) {
		for (Publicacao publicacao : publicacoes) {
			populaQtdComentarios(publicacao);
		}
		return publicacoes;
	}

	@Override
	public List<Publicacao> getPublicacoesEmAlta(Integer codUsuario) {
		UsuarioDetalhe usuario = usuarioService.buscarPorId(codUsuario);
		List<Integer> gostos = new ArrayList<>();
		gostos.add(0);
		for(UsuarioGostoMusical usuarioGosto : usuario.getGostosMusicais()) {
			gostos.add(usuarioGosto.getPk().getGostoMusical().getCodigo());
		}
		return populaQtdComentarios(publicacaoDAO.getPublicacoesEmAlta(gostos));
	}

	@Override
	@Transactional
	public Boolean removerComentario(Long codigoComentario) {
		Comentario comentario = comentarioDAO.find(codigoComentario);
		if (comentario != null) {
			comentarioDAO.remove(comentario);
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public Boolean impulsionarPublicacao(Long codigoPublicacao, Integer gostoMusical) {
		Publicacao publicacao = publicacaoDAO.find(codigoPublicacao);
		if(publicacao != null) {
			if(gostoMusical != null) {
				GostoMusical gosto = gostoMusicalDAO.findGostoById(gostoMusical);
				publicacao.setGosto(gosto);
			}
			publicacao.setImpulsionada(true);
			return true;
		}
		return false;
	}
	
	@Override
	public Publicacao getPublicacao(Long idPublicacao) {
		return publicacaoDAO.getPublicacao(idPublicacao);
	}
}
