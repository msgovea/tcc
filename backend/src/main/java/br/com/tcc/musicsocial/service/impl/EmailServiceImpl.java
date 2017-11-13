package br.com.tcc.musicsocial.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.tcc.musicsocial.entity.Email;
import br.com.tcc.musicsocial.entity.UsuarioDetalhe;
import br.com.tcc.musicsocial.service.EmailService;
import br.com.tcc.musicsocial.service.UsuarioService;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final String REMETENTE = "wagner.j.luz@gmail.com";
	
	private static final String templateContato = "<div style=\"border:1px; border-style: solid; border-color: black; border-radius: 5px; padding: 10px; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif\">    <div style=\"margin: 0; padding: 5px; height: 50px;\">        <div style=\"height: 50px; float:left;\"><img src=\"http://urmusic.me:82/perfil/%d.jpg\" style=\"width: 50px; height: 50px; border-radius: 100px;\"></div>        <div style=\"float:left; vertical-align: middle; padding: 6px 0px 0px 15px;\"><span style=\"font-size: 2em; display:block;\">%s</span></div>    </div>    <div style=\"border:1px; border-style: solid; border-color: black; border-radius: 5px; background: lightgrey; margin: 15px 65px 0px 65px; padding: 3px;\">        %s    </div>    <div style=\"height: 30px;padding: 20px 20px 0px 0px;\">        Contato via aplicativo urMusic.         <a href=\"http://urmusic.me\" target=\"blank\"><img src=\"http://urmusic.me:82/logo_completo.png\" style=\"width: 100px; float: right;\"></a>    </div></div>";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	public JavaMailSender sender;
	
	public void enviarEmail(String assunto, String destinatario, String texto) throws MessagingException {
		validarEmail(assunto, destinatario, texto);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(REMETENTE);
		helper.setTo(destinatario);
		helper.setSubject(assunto);
		helper.setText(texto, true);
		
		sender.send(message);
	}
	
	@Override
	public void enviarEmailContato(Integer codRemetente, String destinatario, String texto) throws MessagingException {
		UsuarioDetalhe usuarioRemetente = usuarioService.buscarPorId(codRemetente);
		if (usuarioRemetente != null) {
			this.enviarEmail("Contato de " + usuarioRemetente.getNome(), destinatario, String.format(templateContato,codRemetente, usuarioRemetente.getNome(), texto));
		}
	}
	
	private void validarEmail(String assunto, String destinatario, String texto) {
		if (assunto == null || StringUtils.isEmpty(destinatario) || texto == null) {
			throw new RuntimeException("E-mail inválido!");
		}
	}
	
	public void enviarEmail(Email email) throws MessagingException {
		enviarEmail(email.getAssunto(), email.getDestinatario(), email.getMensagem());
	}
}
