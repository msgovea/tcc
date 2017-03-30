package br.com.tcc.musicsocial.util;

public enum SituacaoConta {
	ATIVA(1),
	INATIVA(2);
	
	private Integer value;
	
	private SituacaoConta(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public br.com.tcc.musicsocial.entity.SituacaoConta getEntity() {
		if (this.value.equals(1)) {
			return new br.com.tcc.musicsocial.entity.SituacaoConta(1, SituacaoConta.ATIVA.toString());
		} else { 
			return new br.com.tcc.musicsocial.entity.SituacaoConta(2, SituacaoConta.INATIVA.toString());
		}
	}
}
