package br.com.tcc.musicsocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@CrossOrigin
	@RequestMapping(value = "/get/{idUsuario}")
	public Response<?> getPublicacoes(@PathVariable("idUsuario") String idUsuario) {
		try {
			return new Response<List<Publicacao>>(MessagesEnum.SUCESSO.getDescricao(),
					publicacaoService.getPublicacoes(idUsuario));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@RequestMapping(value = "/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@CrossOrigin
	public Response<?> cadastrarPublicacao(@RequestBody Publicacao request) {
		try {
			if (publicacaoService.cadastrarPublicacao(request) != null) {
				return new Response<Object>(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response<Object>(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
	
	@RequestMapping("/remover")
	public Response<?> removerPublicacao(@RequestParam Long codigo) {
		try {
			if (publicacaoService.removerPublicacao(codigo)) {
				return new Response<Object>(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response<Object>(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
}
