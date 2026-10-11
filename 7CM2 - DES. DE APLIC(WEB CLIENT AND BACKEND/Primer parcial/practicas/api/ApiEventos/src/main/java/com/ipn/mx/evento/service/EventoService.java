package com.ipn.mx.evento.service;

import com.ipn.mx.evento.domain.Evento;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface EventoService {

	public List<Evento> findAll();

	// zip: devuelve null cuando el id no existe; el controlador lo traduce a 404
	public Evento findById(Long id);

	public Evento save(Evento evento);

	public void delete(Long id);

	// zip: está declarado en el servicio (en el zip su cuerpo es un pendiente; aquí está el de la clase del PDF)
	public ByteArrayInputStream generarReporte(List<Evento> listaDeEventos);

}
