package br.com.tcc.musicsocial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.musicsocial.dto.CadastrarGostosMusicaisRequest;
import br.com.tcc.musicsocial.dto.LoginRequest;
import br.com.tcc.musicsocial.dto.Response;
import br.com.tcc.musicsocial.entity.GostoMusical;
import br.com.tcc.musicsocial.entity.Usuario;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.service.UsuarioService;
import br.com.tcc.musicsocial.util.MessagesEnum;

@RestController
public class UserController {

	@Autowired
	private UsuarioService usuarioService;

	@CrossOrigin
	@RequestMapping(value = "/usuario/cadastro", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody Response<?> cadastrarUsuario(@RequestBody UsuarioDetalhe usuario) {
		try {
			return new Response<UsuarioDetalhe>(MessagesEnum.SUCESSO.getDescricao(),
					usuarioService.cadastrarUsuario(usuario));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/usuario/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public @ResponseBody Response<?> efetuarLogin(@RequestBody LoginRequest loginRequest) {
		try {
			Usuario usuario = usuarioService.efetuarLogin(loginRequest.getEmail(), loginRequest.getSenha());
			String mensagem;
			if (usuario == null) {
				mensagem = MessagesEnum.INVALIDO.getDescricao();
			} else {
				mensagem = MessagesEnum.SUCESSO.getDescricao();
			}
			return new Response<Usuario>(mensagem, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@CrossOrigin
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/usuario/confirmar/{id}/{email}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public Response<?> confirmarEmail(@PathVariable("id") String id, @PathVariable("email") String email) {
		try {
			if (usuarioService.confirmarEmail(id, email)) {
				return new Response(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@CrossOrigin
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usuario/recuperar/{email}")
	public Response<?> recuperarSenha(@PathVariable("email") String email) {
		try {
			if (usuarioService.recuperarSenha(email)) {
				return new Response(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@CrossOrigin
	@SuppressWarnings("rawtypes")
	@RequestMapping("/usuario/redefinir/{idBase}/{emailHash}/{senhaHash}")
	public Response<?> redefinirSenha(@PathVariable("idBase") String idBase,
			@PathVariable("emailHash") String emailHash, @PathVariable("senhaHash") String senhaHash) {
		try {
			if (usuarioService.redefinirSenha(idBase, emailHash, senhaHash)) {
				return new Response(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/usuario/gostosmusicais", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@CrossOrigin
	public Response<?> cadastrarGostoMusical(@RequestBody CadastrarGostosMusicaisRequest request) {
		try { 
			if (usuarioService.cadastrarGostoMusical(request.getCodigoUsuario(),
					request.getCodigosGostosMusicais(), request.getFavorito())) {
				return new Response(MessagesEnum.SUCESSO.getDescricao());
			} else {
				return new Response(MessagesEnum.INVALIDO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@CrossOrigin
	@RequestMapping("/usuario/getGostosMusicais")
	public Response<?> getGostosMusicais() {
		try { 
			List<GostoMusical> gostoMusical = usuarioService.getGostos();
			return new Response(MessagesEnum.SUCESSO.getDescricao(), gostoMusical);			
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>(MessagesEnum.FALHA.getDescricao(), e);
		}
	}
}
