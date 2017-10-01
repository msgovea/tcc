package br.edu.puccamp.app.entity;



public class NivelUsuario {

	private Integer codigoNivel;

	private String tipo;
	
	public NivelUsuario() {
		//empty
	}

	public NivelUsuario(Integer codigoNivel, String tipo) {
		this.codigoNivel = codigoNivel;
		this.tipo = tipo;
	}

	public Integer getCodigoNivel() {
		return codigoNivel;
	}

	public void setCodigoNivel(Integer codigoNivel) {
		this.codigoNivel = codigoNivel;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
