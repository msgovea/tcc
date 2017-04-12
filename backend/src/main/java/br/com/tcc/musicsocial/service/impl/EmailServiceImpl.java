package br.com.tcc.musicsocial.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.tcc.musicsocial.entity.Email;
import br.com.tcc.musicsocial.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	private static final String REMETENTE = "tcc.puccampinas@gmail.com";
	
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
	
	private void validarEmail(String assunto, String destinatario, String texto) {
		if (assunto == null || StringUtils.isEmpty(destinatario) || texto == null) {
			throw new RuntimeException("E-mail inválido!");
		}
	}
	
	public void enviarEmail(Email email) throws MessagingException {
		enviarEmail(email.getAssunto(), email.getDestinatario(), email.getMensagem());
	}
}
