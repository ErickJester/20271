package com.ipn.mx.evento.controller;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.evento.service.EventoService;
import com.ipn.mx.utilerias.service.EmailService;
import jakarta.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
// zip: la base es /api/v1/ApiEventos (con A mayúscula) y cada método agrega /eventos
@RequestMapping("/api/v1/ApiEventos")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private EmailService emailService;

	// zip: se llama readAll
	@GetMapping("/eventos")
	@ResponseStatus(HttpStatus.OK)
	public List<Evento> readAll() {
		return eventoService.findAll();
	}

	@GetMapping("/eventos/pdf")
	public ResponseEntity<InputStreamResource> generarPdf() {
		List<Evento> listaDeEventos = eventoService.findAll();
		ByteArrayInputStream salidaByteArray = eventoService.generarReporte(listaDeEventos);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=reporteEventos.pdf");

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(salidaByteArray));
	}

	// zip: se llama read
	@GetMapping("/eventos/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Evento evento = null;
		Map<String, Object> respuesta = new HashMap<>();

		try {
			evento = eventoService.findById(id);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al realizar la consulta");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (evento == null) {
			respuesta.put("mensaje", "El evento ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(evento, HttpStatus.OK);
	}

	@PostMapping("/eventos")
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody Evento evento, BindingResult result) {
		Evento eventoNuevo = null;
		Map<String, Object> respuesta = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			respuesta.put("errores", errores);
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
		}

		try {
			eventoNuevo = eventoService.save(evento);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al insertar el Evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		emailService.enviarCorreo(eventoNuevo);

		respuesta.put("mensaje", "El Evento se creó satisfactoriamente");
		respuesta.put("evento", eventoNuevo);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	@PutMapping("/eventos/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Evento evento, BindingResult result, @PathVariable Long id) {
		Evento eventoActual = eventoService.findById(id);
		Evento eventoActualizado = null;
		Map<String, Object> respuesta = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			respuesta.put("errores", errores);
			return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
		}

		if (eventoActual == null) {
			respuesta.put("mensaje", "Error: no se pudo editar, el Evento ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		try {
			eventoActual.setNombreEvento(evento.getNombreEvento());
			eventoActual.setDescripcionEvento(evento.getDescripcionEvento());
			eventoActual.setFechaCreacion(evento.getFechaCreacion());

			eventoActualizado = eventoService.save(eventoActual);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al actualizar el Evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		respuesta.put("mensaje", "El Evento se actualizó satisfactoriamente");
		respuesta.put("evento", eventoActualizado);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	@DeleteMapping("/eventos/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Map<String, Object> respuesta = new HashMap<>();
		Evento eventoActual = eventoService.findById(id);

		if (eventoActual == null) {
			respuesta.put("mensaje", "Error: no se pudo eliminar, el Evento ID: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		try {
			eventoService.delete(id);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al eliminar el Evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		respuesta.put("mensaje", "El Evento se eliminó satisfactoriamente");
		return new ResponseEntity<>(respuesta, HttpStatus.NO_CONTENT);
	}

}
