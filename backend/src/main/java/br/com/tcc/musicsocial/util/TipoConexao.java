package br.com.tcc.musicsocial.util;

public enum TipoConexao {
	SISTEMA(1),
	FACEBOOK(2),
	GOOGLE(3);
	
	private Integer value;

	private TipoConexao(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public br.com.tcc.musicsocial.entity.TipoConexao getEntity() {
		switch (this.value) {
		case 1:
			return new br.com.tcc.musicsocial.entity.TipoConexao(1, TipoConexao.SISTEMA.toString());
		case 2:
			return new br.com.tcc.musicsocial.entity.TipoConexao(2, TipoConexao.FACEBOOK.toString());
		default:
			return new br.com.tcc.musicsocial.entity.TipoConexao(3, TipoConexao.GOOGLE.toString());
		}
	}
}
