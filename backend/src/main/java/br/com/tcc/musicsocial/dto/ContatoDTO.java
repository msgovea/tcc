package br.com.tcc.musicsocial.dto;

import lombok.Data;

@Data
public class ContatoDTO {
	
	private Integer codRemetente;
	private String texto;
	private String destinatario;
}
