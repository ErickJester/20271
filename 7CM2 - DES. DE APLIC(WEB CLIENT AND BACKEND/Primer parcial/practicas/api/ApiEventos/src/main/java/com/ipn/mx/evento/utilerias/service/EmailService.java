package com.ipn.mx.evento.utilerias.service;

import com.ipn.mx.evento.domain.Evento;

// clase del correo (8 oct): contrato del servicio que manda correos. El profesor crea un paquete nuevo
// "utilerias" (código de apoyo que no es parte del CRUD) dentro de evento: com.ipn.mx.evento.utilerias.service.
// Igual que con EventoService, el controlador solo conoce esta interfaz; la implementación está en el
// subpaquete impl. Solo tiene un método, y recibe el evento para poder armar el mensaje con sus datos.
// El profesor dijo del argumento: "nada más por ponerle algo, ya ustedes sabrán qué hacen con él".
public interface EmailService {

	public void enviarCorreo(Evento evento);

}
