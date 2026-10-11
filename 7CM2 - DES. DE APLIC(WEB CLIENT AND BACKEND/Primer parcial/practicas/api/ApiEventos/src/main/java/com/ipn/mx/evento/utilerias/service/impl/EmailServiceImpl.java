package com.ipn.mx.evento.utilerias.service.impl;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.evento.utilerias.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

// clase del correo (8 oct): implementación del envío de correo. @Service le dice a Spring que la cree y la
// ofrezca al controlador. Usa Gmail por SMTP, con los datos de application.properties (spring.mail.*).
// Para que Gmail lo acepte, la cuenta debe permitir aplicaciones de terceros: se usa una contraseña de
// aplicación, no la contraseña normal. Sin eso Gmail rechaza el envío con una AuthenticationException.
@Service
public class EmailServiceImpl implements EmailService {

	// Escribe mensajes en la consola del servidor (INFO, WARN, ERROR). Se usa para avisar cuando no se manda
	// el correo sin tumbar la petición.
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	// clase del correo (8 oct): JavaMailSender es el objeto de Spring que sabe hablar con el servidor SMTP.
	// Spring lo crea solo con los datos spring.mail.* de application.properties, y @Autowired lo inyecta aquí.
	@Autowired
	private JavaMailSender mailSender;

	// tarea de clase (8 oct): el profesor escribió el correo de un compañero directamente en el código para
	// la demostración, y luego dijo que usuario, contraseña y demás datos no deben ir escritos, sino como
	// variables de entorno. Por eso el destinatario se lee de la propiedad app.correo.destinatario, que a su
	// vez sale de la variable MAIL_TO. El ":" del @Value es el valor por defecto: vacío si no existe.
	@Value("${app.correo.destinatario:}")
	private String destinatario;

	@Override
	public void enviarCorreo(Evento evento) {
		// Sin destinatario no hay a quién escribirle: se avisa en el log y se sale. El evento ya quedó guardado.
		if (destinatario == null || destinatario.isBlank()) {
			log.warn("MAIL_TO está vacío: no se envía correo del evento '{}'", evento.getNombreEvento());
			return;
		}

		// clase del correo (8 oct): SimpleMailMessage es un correo de texto plano, sin formato ni adjuntos.
		// Se le dan destinatario, asunto y texto, y se envía con mailSender.send. El asunto en clase fue
		// "notificación de... evento" (improvisado); aquí queda como "Notificación de Evento".
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(destinatario);
		email.setSubject("Notificación de Evento");
		// El texto se arma uniendo piezas: nombre y fecha del evento, y una duración que el profesor improvisó
		// ("no sé, lo que se les ocurra... dos días"). Quedaría, por ejemplo: "El evento Huawei Day se llevará a
		// cabo el 2026-10-27 y tendrá una duración de 2 días."
		email.setText("El evento " + evento.getNombreEvento()
				+ " se llevará a cabo el " + evento.getFechaCreacion() + " y tendrá una duración de 2 días.");

		// Diferencia con la clase: allá, una contraseña incorrecta lanzaba una AuthenticationException y el
		// POST respondía con error aunque el evento ya se hubiera guardado. Aquí se atrapa MailException, se
		// deja el error en el log y la petición sigue normal: crear el evento no depende de que el correo salga.
		try {
			mailSender.send(email);
		} catch (MailException e) {
			log.error("No se pudo enviar el correo del evento '{}': {}", evento.getNombreEvento(), e.getMessage());
		}
	}

}
