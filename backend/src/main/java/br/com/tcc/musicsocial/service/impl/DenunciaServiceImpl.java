package br.com.tcc.musicsocial.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.dao.DenunciaDAO;
import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Denuncia;
import br.com.tcc.musicsocial.service.DenunciaService;
import br.com.tcc.musicsocial.service.PublicacaoService;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.SituacaoConta;
import br.com.tcc.musicsocial.util.StatusDenuncia;
import br.com.tcc.musicsocial.util.TipoAprovacaoDenuncia;

@Service
public class DenunciaServiceImpl implements DenunciaService {

	@Autowired
	private DenunciaDAO denunciaDAO;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PublicacaoDAO publicacaoDAO;
	
	@Autowired
	private PublicacaoService publicacaoService;

	@Transactional
	@Override
	public Denuncia denunciarPublicacao(Denuncia denuncia) {
		if (isDenunciaValida(denuncia)) {
			denuncia.setStatus(StatusDenuncia.INICIADA.getValue());
			denuncia.setData(new Date(Calendar.getInstance().getTimeInMillis()));
			denunciaDAO.save(denuncia);
			return denuncia;
		}
		return null;
	}

	private boolean isDenunciaValida(Denuncia denuncia) {
		return denuncia != null && denuncia.getDenunciado() != null
				&& denuncia.getDenunciado().getCodigoUsuario() != null && denuncia.getDenunciante() != null
				&& denuncia.getDenunciante().getCodigoUsuario() != null && denuncia.getPublicacao() != null
				&& denuncia.getPublicacao().getCodigo() != null
				&& usuarioService.buscarPorId(denuncia.getDenunciante().getCodigoUsuario()) != null
				&& usuarioService.buscarPorId(denuncia.getDenunciado().getCodigoUsuario()) != null
				&& publicacaoDAO.find(denuncia.getPublicacao().getCodigo()) != null;
	}

	@Override
	public List<Denuncia> getDenuncias() {
		return populaPublicacoes(denunciaDAO.getDenuncias());
	}
	
	private List<Denuncia> populaPublicacoes(List<Denuncia> denuncias) {
		for (Denuncia denuncia : denuncias) {
			publicacaoService.populaQtdComentarios(denuncia.getPublicacao());
		}
		return denuncias;
	}

	@Transactional
	@Override
	public boolean aprovarDenuncia(Long codigoDenuncia, Integer tipoAprovacao) {
		Denuncia denuncia = denunciaDAO.find(codigoDenuncia);
		if (denuncia != null && denuncia.getStatus().equalsIgnoreCase(StatusDenuncia.INICIADA.getValue())) {
			if (tipoAprovacao.equals(TipoAprovacaoDenuncia.BAN_USUARIO.getValue())) {
				denuncia.setStatus(StatusDenuncia.APROVADA.getValue());
				denuncia.getDenunciado().setSituacaoConta(SituacaoConta.BANIDA.getEntity());
				usuarioService.atualizarUsuario(denuncia.getDenunciado());
				return true;
			} else if (tipoAprovacao.equals(TipoAprovacaoDenuncia.BAN_PUBLICACAO.getValue())) {
				denuncia.setStatus(StatusDenuncia.APROVADA.getValue());
				denuncia.getPublicacao().setAtiva(false);
				publicacaoDAO.update(denuncia.getPublicacao());
				return true;
			} else if (tipoAprovacao.equals(TipoAprovacaoDenuncia.REPROVAR.getValue())) {
				denuncia.setStatus(StatusDenuncia.REPROVADA.getValue());
			}
		}
		return false;
	}
}
