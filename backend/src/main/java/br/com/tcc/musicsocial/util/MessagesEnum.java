package br.com.tcc.musicsocial.util;

public enum MessagesEnum {
	SUCESSO("Sucesso!"),
	INVALIDO("Invalido!"),
	FALHA("Falha!"),
	EMAIL_JA_CADASTRADO("Email ja cadastrado");
	
	private String descricao;

	private MessagesEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
