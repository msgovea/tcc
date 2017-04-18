package br.com.tcc.musicsocial.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import br.com.tcc.musicsocial.dao.PublicacaoDAO;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.service.PublicacaoService;

@Service
public class PublicacaoServiceImpl implements PublicacaoService {
	
	@Autowired
	private PublicacaoDAO publicacaoDAO;

	@Override
	public List<Publicacao> getPublicacoes(String idUsuario) {
		Integer id = Integer.parseInt(new String(Base64Utils.decodeFromString(idUsuario)));	
		return publicacaoDAO.getPublicacoes(id);
	}

}
