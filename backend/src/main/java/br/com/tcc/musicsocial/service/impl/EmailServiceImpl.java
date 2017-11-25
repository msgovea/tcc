package br.com.tcc.musicsocial.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
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
	
	private static final String REMETENTE = "urmusic.me@gmail.com";
	
	private static final String templateContato = "<tr> <table width=\"100%%\" cellpadding=\"20\"> <tbody> <tr> <td dir=\"ltr\" style=\"font-family:'Helvetica Neue',helvetica,sans-serif;font-size:15px;color:#333333;line-height:21px\"> <strong style=\"font-size:17px\">Olá %s ,</strong> <br>Você recebeu uma nova mensagem <br><br><table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"> <tbody> <tr> <td dir=\"ltr\" width=\"40\" valign=\"top\" class=\"m_-5298157907432643778hidePhone\" style=\"width:0;max-height:0;overflow:hidden;float:left\"> <td dir=\"ltr\" valign=\"top\" style=\"background:#f9f9f9\"> <table width=\"100%%\" cellpadding=\"10\" cellspacing=\"0\" border=\"0\"> <tbody> <tr> <td dir=\"ltr\"> <table width=\"100%%\" cellpadding=\"5\" cellspacing=\"0\" border=\"0\"> <tbody> <tr> <td dir=\"ltr\" style=\"font-family:'Helvetica Neue',helvetica,sans-serif;line-height:1.5;font-size:14px;color:#8d8d8d\"> <img src=\"http://urmusic.me:82/perfil/%d.jpg\" width=\"40\" height=\"40\" style=\"vertical-align:middle;border-radius: 100px;\">   %s </td></tr><tr> <td dir=\"ltr\" style=\"font-family:'Helvetica Neue',helvetica,sans-serif;line-height:1.5;font-size:14px\"> <p> %s </p></td></tr><tr> <td dir=\"ltr\"> <a href=\"https://r789f.app.goo.gl/?link=http://www.urmusic.me/idLista/%d&apn=com.mgovea.urmusic\" style=\"color:#ffffff;font-family:'Helvetica Neue',helvetica,sans-serif;text-decoration:none;font-size:14px;background:#3f6d98;line-height:32px;padding:0 10px;display:inline-block;border-radius:3px\" target=\"_blank\"> Reponder mensagem</a> </td></tr></tbody> </table> </td></tr></tbody> </table> </td></tr></tbody> </table> <br><br><em style=\"color:#8c8c8c\">— <span class=\"lG\">urMusic</span></em> </td></tr></tbody> </table></tr>";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	public JavaMailSender sender;
	
	public void enviarEmail(String assunto, String destinatario, String texto) throws MessagingException {
		validarEmail(assunto, destinatario, texto);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom(new InternetAddress(REMETENTE, "urMusic"));
		} catch (UnsupportedEncodingException e) {
			helper.setFrom(REMETENTE);
			e.printStackTrace();
		}
		helper.setTo(destinatario);
		helper.setSubject(assunto);
		helper.setText(texto, true);
		
		sender.send(message); 
	}
	
	@Override
	public void enviarEmailContato(Integer codRemetente, String destinatario, String texto) throws MessagingException {
		UsuarioDetalhe usuarioDestinatario = usuarioService.buscarPorEmail(destinatario);
		UsuarioDetalhe usuarioRemetente = usuarioService.buscarPorId(codRemetente);
		if (usuarioRemetente != null) {
			this.enviarEmail("urMusic - Nova mensagem de: " + usuarioRemetente.getNome(), destinatario, String.format(templateContato, usuarioDestinatario.getNome(), codRemetente, usuarioRemetente.getNome(), texto, codRemetente));
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
