package br.com.tcc.musicsocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.musicsocial.dto.Response;
import br.com.tcc.musicsocial.entity.Denuncia;
import br.com.tcc.musicsocial.service.DenunciaService;
import br.com.tcc.musicsocial.util.MessagesEnum;

@RestController
@RequestMapping("/denuncia")
public class DenunciaController {
	
	@Autowired
	private DenunciaService denunciaService;
	
	@RequestMapping("/cadastrar")
	public Response<?> cadastrarDenuncia(@RequestBody Denuncia denuncia) {
		try {
			if (denunciaService.denunciarPublicacao(denuncia) != null) {
				return new Response<Object>(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response<Object>(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
	
	@RequestMapping("/get")
	public Response<?> getDenuncias() {
		try {
			return new Response<Object>(MessagesEnum.SUCESSO.getDescricao(), denunciaService.getDenuncias());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
	
	@RequestMapping("/aprovar/{codigoDenuncia}/{tipoAprovacao}")
	public Response<?> aprovarDenuncia(@PathVariable Long codigoDenuncia, @PathVariable Integer tipoAprovacao) {
		try {
			if(denunciaService.aprovarDenuncia(codigoDenuncia, tipoAprovacao)){
				return new Response<Object>(MessagesEnum.SUCESSO.getDescricao());
			}
			return new Response<Object>(MessagesEnum.INVALIDO.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
}
