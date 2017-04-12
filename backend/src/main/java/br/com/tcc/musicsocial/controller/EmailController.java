package br.com.tcc.musicsocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.musicsocial.dto.Response;
import br.com.tcc.musicsocial.entity.Email;
import br.com.tcc.musicsocial.service.EmailService;
import br.com.tcc.musicsocial.util.MessagesEnum;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;

	@RequestMapping(
			value = "/enviar",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST
			)
	@CrossOrigin
	public @ResponseBody Response<?> enviarEmail(@RequestBody Email email) {
		try {
			emailService.enviarEmail(email);
			return new Response<Object>(MessagesEnum.SUCESSO.getDescricao());
		} catch (Exception e) {
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
	
}
