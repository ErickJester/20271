package com.ipn.mx.evento.utilerias.service;

import com.ipn.mx.evento.domain.Evento;

// clase del correo: el profesor crea el paquete utilerias dentro de evento (com.ipn.mx.evento.utilerias.service)
public interface EmailService {

	public void enviarCorreo(Evento evento);

}
