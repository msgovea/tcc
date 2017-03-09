package br.com.tcc.musicsocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.tcc.musicsocial.dto.LoginRequest;
import br.com.tcc.musicsocial.dto.Response;
import br.com.tcc.musicsocial.entity.NivelUsuario;
import br.com.tcc.musicsocial.entity.SituacaoConta;
import br.com.tcc.musicsocial.entity.TipoConexao;
import br.com.tcc.musicsocial.entity.Usuario;
import br.com.tcc.musicsocial.service.UsuarioService;

@RestController
public class UserController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(
				value = "/usuario/cadastro",
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE,
				method = RequestMethod.POST
			)
	public @ResponseBody Response<?> cadastrarUsuario(@RequestBody Usuario usuario) {
		try {
			return new Response<Usuario>("Sucesso!", usuarioService.cadastrarUsuario(usuario));
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>("Falha!", e);
		}
	}
	
	@RequestMapping(
			value = "/usuario/login",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE,
			method = RequestMethod.POST
		)
	public @ResponseBody Response<?> efetuarLogin(@RequestBody LoginRequest loginRequest) {
		try {
			Usuario usuario = usuarioService.efetuarLogin(loginRequest.getEmail(), loginRequest.getSenha());
			String mensagem;
			if (usuario == null) {
				mensagem = "Invalido!";
			} else {
				mensagem = "Sucesso!";
			}
			return new Response<Usuario>(mensagem, usuario);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Exception>("Falha!", e);
		}
	}
	
	@RequestMapping("/usuario/teste")
	public @ResponseBody Usuario teste() {
		Usuario u = new Usuario();
		u.setTipoConexao(new TipoConexao());
		u.setNivelUsuario(new NivelUsuario());
		u.setSituacaoConta(new SituacaoConta());
		return u;
	}
}
