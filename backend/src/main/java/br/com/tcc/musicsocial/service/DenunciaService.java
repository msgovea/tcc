package br.com.tcc.musicsocial.service;

import java.util.List;

import br.com.tcc.musicsocial.entity.Denuncia;

public interface DenunciaService {

	Denuncia denunciarPublicacao(Denuncia denuncia);

	List<Denuncia> getDenuncias();

	boolean aprovarDenuncia(Long codigoDenuncia, Integer tipoAprovacao);

}
