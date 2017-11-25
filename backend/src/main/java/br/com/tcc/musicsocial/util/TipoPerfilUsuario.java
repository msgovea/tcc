package br.com.tcc.musicsocial.util;

public enum TipoPerfilUsuario {
	MUSICO(1),
	AMADOR(2),
	COMUM(3),
	CASA_DE_EVENTO(4);
	
	private int value;

	private TipoPerfilUsuario(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
