package com.ipn.mx.evento.utilerias.service.impl;

import com.ipn.mx.evento.dto.EventoDTO;
import com.ipn.mx.evento.utilerias.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// clase del correo: implementación del envío de correo.
// En la clase del 28 oct se actualiza para recibir EventoDTO y usar sus métodos de record (sin get).
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Value("${app.correo.destinatario:}")
	private String destinatario;

	@Override
	public void enviarCorreo(EventoDTO evento) {
		if (destinatario == null || destinatario.isBlank()) {
			log.warn("MAIL_TO está vacío: no se envía correo del evento '{}'", evento.nombreEvento());
			return;
		}

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destinatario);
		email.setSubject("Notificación de Evento");
		email.setText("El evento " + evento.nombreEvento()
				+ " se llevará a cabo el " + evento.fechaCreacion() + " y tendrá una duración de 2 días.");

		try {
			mailSender.send(email);
		} catch (MailException e) {
			log.error("No se pudo enviar el correo del evento '{}': {}", evento.nombreEvento(), e.getMessage());
		}
	}

}
