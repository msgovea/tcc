package br.com.tcc.musicsocial.dto;

public class ComentarRequest {
	
	private Integer codigoUsuario;
	private Long codigoPublicacao;
	private String comentario;

	public Integer getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(Integer codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public Long getCodigoPublicacao() {
		return codigoPublicacao;
	}

	public void setCodigoPublicacao(Long codigoPublicacao) {
		this.codigoPublicacao = codigoPublicacao;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
