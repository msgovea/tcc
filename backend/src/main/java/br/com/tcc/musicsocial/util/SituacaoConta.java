package br.com.tcc.musicsocial.util;

public enum SituacaoConta {
	AGUARDANDO_CONFIRMACAO(0), ATIVA(1), INATIVA(2), BANIDA(3);

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
		switch (this.value) {
		case 0:
			return new br.com.tcc.musicsocial.entity.SituacaoConta(0, SituacaoConta.AGUARDANDO_CONFIRMACAO.toString());
		case 1:
			return new br.com.tcc.musicsocial.entity.SituacaoConta(1, SituacaoConta.ATIVA.toString());
		case 2:
			return new br.com.tcc.musicsocial.entity.SituacaoConta(2, SituacaoConta.INATIVA.toString());
		case 3:
			return new br.com.tcc.musicsocial.entity.SituacaoConta(3, SituacaoConta.BANIDA.toString());
		default:
			return null;
		}
	}
}
