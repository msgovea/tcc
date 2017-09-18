package br.com.tcc.musicsocial.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tcc.musicsocial.dao.DenunciaDAO;
import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Denuncia;
import br.com.tcc.musicsocial.service.DenunciaService;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.StatusDenuncia;

@Service
public class DenunciaServiceImpl implements DenunciaService {

	@Autowired
	private DenunciaDAO denunciaDAO;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PublicacaoDAO PublicacaoDAO;
	
	@Transactional
	@Override
	public Denuncia denunciarPublicacao(Denuncia denuncia) {
		if(isDenunciaValida(denuncia)) {
			denuncia.setStatus(StatusDenuncia.INICIADA.getValue());
			denuncia.setData(new Date());
			denunciaDAO.save(denuncia);
			return denuncia;
		}
		return null;
	}
	
	private boolean isDenunciaValida(Denuncia denuncia) {
		return denuncia != null && denuncia.getDenunciado() != null && denuncia.getDenunciado().getCodigoUsuario() != null 
				&& denuncia.getDenunciante() != null && denuncia.getDenunciante().getCodigoUsuario() != null
				&& denuncia.getPublicacao() != null && denuncia.getPublicacao().getCodigo() != null
				&& usuarioService.buscarPorId(denuncia.getDenunciante().getCodigoUsuario()) != null 
				&& usuarioService.buscarPorId(denuncia.getDenunciado().getCodigoUsuario()) != null 
				&& PublicacaoDAO.find(denuncia.getPublicacao().getCodigo()) != null;
	}
	
	@Override
	public List<Denuncia> getDenuncias() {
		return denunciaDAO.getDenuncias();
	}
}
