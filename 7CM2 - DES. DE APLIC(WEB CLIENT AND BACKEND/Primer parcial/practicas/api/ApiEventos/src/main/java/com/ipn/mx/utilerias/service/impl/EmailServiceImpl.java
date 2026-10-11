package com.ipn.mx.utilerias.service.impl;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.utilerias.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${app.correo.destinatario:}")
	private String destinatario;

	@Override
	public void enviarCorreo(Evento evento) {
		if (destinatario == null || destinatario.isBlank()) {
			log.warn("MAIL_TO está vacío: no se envía correo del evento '{}'", evento.getNombreEvento());
			return;
		}

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destinatario);
		email.setSubject("Notificación de Evento");
		email.setText("El evento " + evento.getNombreEvento()
				+ " se llevará a cabo el " + evento.getFechaCreacion() + ".");

		try {
			mailSender.send(email);
		} catch (MailException e) {
			log.error("No se pudo enviar el correo del evento '{}': {}", evento.getNombreEvento(), e.getMessage());
		}
	}

}
