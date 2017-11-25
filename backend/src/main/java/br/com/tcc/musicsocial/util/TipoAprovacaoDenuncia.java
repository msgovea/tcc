package br.com.tcc.musicsocial.util;

public enum TipoAprovacaoDenuncia {
	BAN_USUARIO(1),
	BAN_PUBLICACAO(2),
	REPROVAR(3);
	
	private Integer value;
	
	private TipoAprovacaoDenuncia(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	
}
