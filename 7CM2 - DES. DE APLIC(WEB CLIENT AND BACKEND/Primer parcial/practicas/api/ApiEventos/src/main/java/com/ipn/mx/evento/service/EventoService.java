package com.ipn.mx.evento.service;

import com.ipn.mx.evento.domain.Evento;
import java.io.ByteArrayInputStream;
import java.util.List;

// El servicio es el contrato de lo que se puede hacer con los eventos. El controlador depende de esta interfaz y
// no de la clase concreta, así que EventoServiceImpl se puede cambiar sin tocar el controlador. Es la capa que
// queda entre el controlador (HTTP) y el repositorio (base de datos).
public interface EventoService {

	// 29sept: devuelve todos los eventos. Es lo que usa el GET que se escribió en la clase del primer día.
	public List<Evento> findAll();

	// zip: devuelve el evento, o null cuando el id no existe. El controlador revisa ese null y responde 404.
	public Evento findById(Long id);

	// clase del CRUD (7 oct): guarda un evento. Lo usan create (INSERT) y update (UPDATE).
	public Evento save(Evento evento);

	// clase del CRUD (7 oct): borra el evento con ese id. No devuelve nada.
	public void delete(Long id);

	// zip: ya estaba declarado en el servicio, pero su cuerpo era un pendiente; aquí está el de la clase del PDF
	// (8 oct). Recibe la lista de eventos y devuelve los bytes del PDF listo para enviarse como respuesta.
	public ByteArrayInputStream generarReporte(List<Evento> listaDeEventos);

}
