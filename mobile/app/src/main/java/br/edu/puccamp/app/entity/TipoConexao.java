package br.edu.puccamp.app.entity;



public class TipoConexao {
	

	private Integer codigoTipoConexao;
	
	private String tipo;
	
	public TipoConexao(Integer codigoTipoConexao, String tipo) {
		super();
		this.codigoTipoConexao = codigoTipoConexao;
		this.tipo = tipo;
	}

	public TipoConexao() {
		super();
	}

	public Integer getCodigoTipoConexao() {
		return codigoTipoConexao;
	}

	public void setCodigoTipoConexao(Integer codigoTipoConexao) {
		this.codigoTipoConexao = codigoTipoConexao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
