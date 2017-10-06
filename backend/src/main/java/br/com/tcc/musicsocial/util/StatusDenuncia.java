package br.com.tcc.musicsocial.util;

public enum StatusDenuncia {
	INICIADA("I"),
	APROVADA("A"),
	REPROVADA("R");
	
	private String value;
	
	private StatusDenuncia(String value) {
		this.value = value;	
	}

	public String getValue() {
		return value;
	}
}
