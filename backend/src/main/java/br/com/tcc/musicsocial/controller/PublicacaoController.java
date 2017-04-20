package br.com.tcc.musicsocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.musicsocial.dto.Response;
import br.com.tcc.musicsocial.entity.Publicacao;
import br.com.tcc.musicsocial.service.PublicacaoService;
import br.com.tcc.musicsocial.util.MessagesEnum;

@RestController
@RequestMapping("/publicacoes")
public class PublicacaoController {
	
	@Autowired
	private PublicacaoService publicacaoService;
	
	@RequestMapping(value = "/get/{idUsuario}")
	public Response<?> getPublicacoes(@PathVariable("idUsuario") String idUsuario) {
		try {
			return new Response<List<Publicacao>>(MessagesEnum.SUCESSO.getDescricao(),publicacaoService.getPublicacoes(idUsuario));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
}
