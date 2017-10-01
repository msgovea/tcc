package br.com.tcc.musicsocial.service;

public interface FotoService {

	boolean gravarImagemPerfilUsuario(byte[] foto, Long idUsuario);
	
}
