package br.com.tcc.musicsocial.util;

public enum TipoPerfilUsuario {
	MUSICO(1),
	AMADOR(2),
	COMUM(3);
	
	private int value;

	private TipoPerfilUsuario(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
