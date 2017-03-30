package br.com.tcc.musicsocial.util;

public enum NivelUsuario {
	COMUM(1),
	CLIENTE(2),
	ADMIN(3);
	
	private Integer value;
	
	private NivelUsuario(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public br.com.tcc.musicsocial.entity.NivelUsuario getEntity() {
		switch (this.value) {
		case 1:
			return new br.com.tcc.musicsocial.entity.NivelUsuario(1, NivelUsuario.COMUM.toString());
		case 2:
			return new br.com.tcc.musicsocial.entity.NivelUsuario(2, NivelUsuario.CLIENTE.toString());
		default:
			return new br.com.tcc.musicsocial.entity.NivelUsuario(3, NivelUsuario.ADMIN.toString());
		}
	}
}
