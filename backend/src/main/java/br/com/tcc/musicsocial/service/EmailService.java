package br.com.tcc.musicsocial.service;

import javax.mail.MessagingException;

import br.com.tcc.musicsocial.entity.Email;

public interface EmailService {
	void enviarEmail(String assunto, String destinatario, String texto) throws MessagingException;
	
	void enviarEmail(Email email) throws MessagingException;
}
