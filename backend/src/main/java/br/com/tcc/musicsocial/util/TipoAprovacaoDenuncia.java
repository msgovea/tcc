package br.com.tcc.musicsocial.util;

public enum TipoAprovacaoDenuncia {
	BAN_USUARIO(1),
	BAN_PUBLICACAO(2);
	
	private int value;
	
	private TipoAprovacaoDenuncia(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
