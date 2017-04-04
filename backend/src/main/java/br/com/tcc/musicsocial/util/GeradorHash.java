package br.com.tcc.musicsocial.util;

import org.springframework.util.DigestUtils;

public class GeradorHash {

	public static String gerarHash(String frase) {
		return DigestUtils.md5DigestAsHex(frase.getBytes());
	}
}
