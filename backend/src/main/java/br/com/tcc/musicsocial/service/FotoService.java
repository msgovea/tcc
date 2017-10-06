package br.com.tcc.musicsocial.service;

public interface FotoService {

	boolean gravarImagemPerfilUsuario(byte[] foto, Long idUsuario);

	boolean gravarImagemPublicacao(byte[] foto, Long idPublicacao);
	
}
