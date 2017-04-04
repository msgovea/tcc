package br.com.tcc.musicsocial.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GeradorHash {

	private static byte[] gerarHash(String frase, String algoritmo) {
		try {
			MessageDigest md = MessageDigest.getInstance(algoritmo);
			md.update(frase.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
	
	public static String gerarMd5(String texto) {
		return new String(gerarHash(texto, "MD5"));
	}
}
