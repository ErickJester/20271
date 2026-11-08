package com.ipn.mx.evento.utilerias.service;

import com.ipn.mx.evento.dto.EventoDTO;

// clase del correo: contrato del servicio que manda correos.
// En la clase del 28 oct se actualiza para recibir EventoDTO en lugar de la entidad Evento.
public interface EmailService {

	public void enviarCorreo(EventoDTO evento);

}
