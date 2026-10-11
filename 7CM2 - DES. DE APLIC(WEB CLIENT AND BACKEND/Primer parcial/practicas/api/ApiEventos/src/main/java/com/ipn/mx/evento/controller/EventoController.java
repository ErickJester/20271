package com.ipn.mx.evento.controller;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.evento.service.EventoService;
import com.ipn.mx.evento.utilerias.service.EmailService;
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

// Este es el controlador REST. Recibe las peticiones HTTP (las que mandas desde Insomnia, Swagger o Angular),
// le pasa el trabajo a los servicios y devuelve la respuesta en JSON. No guarda datos ni habla con MySQL
// directamente: eso lo hace EventoService, que usa el repositorio.

// CORS: el navegador no deja que una página en localhost:4200 (Angular) llame a esta API en otro puerto,
// salvo que la API lo autorice. Esta anotación autoriza ese origen. Viene de un resumen de IA de la clase del
// CRUD y no se oye en el audio; se dejó porque más adelante el frontend es Angular.
@CrossOrigin(origins = {"http://localhost:4200"})
// @RestController hace que lo que devuelve cada método se convierta a JSON, en lugar de buscar una página HTML.
@RestController
// clase del CRUD (7 oct), parte 2: el profesor deja la ruta base en /api/v1/eventos. Todas las rutas de abajo
// cuelgan de ahí, por ejemplo GET /api/v1/eventos/3 o GET /api/v1/eventos/pdf. Es la misma ruta que usa
// springdoc.paths-to-match en application.properties para decidir qué sale en Swagger.
@RequestMapping("/api/v1/eventos")
public class EventoController {

	// Spring crea el servicio por su cuenta y lo deja aquí (inyección de dependencias). No hay "new".
	@Autowired
	private EventoService eventoService;

	// clase del correo (8 oct): igual que el servicio, Spring inyecta aquí el servicio que manda correos.
	// El profesor lo agrega "hasta arriba" del controlador, junto al otro @Autowired.
	@Autowired
	private EmailService emailService;

	// Devuelve todos los eventos como una lista JSON. Es el "leer_todos" que el profesor tenía en Insomnia.
	// Nombre del método: zip, se llama readAll.
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Evento> readAll() {
		return eventoService.findAll();
	}

	// clase del PDF (8 oct): genera el reporte de eventos y lo devuelve como archivo PDF.
	// Pasos: 1) se piden todos los eventos, 2) el servicio arma el PDF en memoria y lo devuelve como un
	// ByteArrayInputStream (los bytes del archivo), 3) se agrega el encabezado Content-Disposition con valor
	// "inline", que le dice al navegador que lo muestre en pantalla en lugar de descargarlo, y con el nombre
	// reporteEventos.pdf, 4) se responde 200 con el tipo application/pdf y el contenido envuelto en un
	// InputStreamResource, que es la forma en que Spring sabe enviar un flujo de bytes.
	// En Insomnia el profesor duplica "leer_todos" y le agrega /pdf a la ruta para probarlo.
	@GetMapping("/pdf")
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

	// Devuelve un solo evento por su id (GET /api/v1/eventos/3). El {id} de la ruta llega al método gracias a
	// @PathVariable. Nombre del método: zip, se llama read.
	// El retorno es ResponseEntity<?> porque puede ser un Evento (200) o un mapa con un mensaje de error
	// (404 o 500), y el código HTTP se elige a mano.
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Evento evento = null;
		// Este mapa se convierte en un JSON {"mensaje": "...", "error": "..."} cuando hay que avisar de un problema.
		Map<String, Object> respuesta = new HashMap<>();

		try {
			evento = eventoService.findById(id);
		} catch (DataAccessException e) {
			// DataAccessException es el error que Spring lanza cuando falla algo con la base de datos
			// (conexión caída, tabla inexistente, etc.). Se responde 500 con el detalle técnico.
			respuesta.put("mensaje", "Error al realizar la consulta");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// zip: findById devuelve null cuando el id no existe, y aquí eso se traduce a 404 (no encontrado).
		if (evento == null) {
			respuesta.put("mensaje", "El evento ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(evento, HttpStatus.OK);
	}

	// clase del CRUD (7 oct), parte 1: crea un evento nuevo (POST /api/v1/eventos con el JSON en el cuerpo).
	// Lo que recibe el método:
	//  - @RequestBody: Spring convierte el JSON del cuerpo en un objeto Evento.
	//  - @Valid: antes de entrar al método, revisa las reglas de la entidad (@NotBlank, @Size, @NotNull).
	//  - BindingResult: guarda el resultado de esa revisión. Tiene que ir justo después del objeto validado;
	//    si no estuviera, un dato inválido haría fallar la petición antes de llegar aquí.
	// Los tres caminos posibles:
	//  - Datos inválidos: se arma una lista con un texto por cada campo incorrecto (con un stream sobre
	//    getFieldErrors, que da el nombre del campo y el mensaje de la regla) y se responde 400. Ejemplo del
	//    profesor: el nombre "ISC" tiene 3 letras y la regla pide de 4 a 200.
	//  - Falla la base de datos al guardar: se responde 500 con el mensaje. Ejemplo del profesor: repetir
	//    "Expo ESCOM", porque el nombre es único. getMostSpecificCause da el mensaje de MySQL, que es más claro
	//    que el genérico de Hibernate.
	//  - Todo bien: se responde 201 (creado) con el evento ya guardado, que incluye el id que le asignó MySQL.
	@PostMapping
	public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody Evento evento, BindingResult result) {
		// "evento" es el que llega en la petición; "eventoNuevo" es el que devuelve la base de datos ya guardado.
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
			respuesta.put("mensaje", "Error al insertar el evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// clase del correo (8 oct): una vez guardado el evento se manda el correo de aviso. Si el evento no se
		// pudo guardar, el método ya salió arriba con el 500 y nunca llega aquí.
		emailService.enviarCorreo(eventoNuevo);

		respuesta.put("mensaje", "El evento se creó satisfactoriamente");
		respuesta.put("evento", eventoNuevo);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	// clase del CRUD (7 oct), parte 1: actualiza un evento existente (PUT /api/v1/eventos/3 con el JSON nuevo).
	// El profesor lo arma copiando el POST y agregando el id como @PathVariable. Usa el mismo @Valid y
	// BindingResult, y añade un paso: buscar primero el evento que ya existe (eventoActual).
	// Orden de las comprobaciones: 1) validar el JSON (400), 2) ver si el evento existe (404 si findById da null),
	// 3) copiar los campos nuevos sobre el evento que ya está en la base y guardarlo (500 si falla).
	// Se copia campo por campo sobre eventoActual, y no se guarda el objeto que llegó, para conservar el mismo
	// id: así save actualiza el registro en lugar de crear otro.
	@PutMapping("/{id}")
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
			respuesta.put("mensaje", "Error al actualizar el evento ".concat(id.toString()).concat(" no existe en la base de datos"));
			return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
		}

		try {
			eventoActual.setNombreEvento(evento.getNombreEvento());
			eventoActual.setDescripcionEvento(evento.getDescripcionEvento());
			eventoActual.setFechaCreacion(evento.getFechaCreacion());

			eventoActualizado = eventoService.save(eventoActual);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al actualizar el evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		respuesta.put("mensaje", "El evento se actualizó satisfactoriamente");
		respuesta.put("evento", eventoActualizado);
		// clase del CRUD (7 oct): el profesor había puesto 201 (CREATED) al copiar el POST y lo cambia a 200 (OK),
		// porque actualizar no crea nada nuevo.
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

	// clase del CRUD (7 oct): elimina un evento (DELETE /api/v1/eventos/3). Es el más corto de los cuatro porque
	// no recibe un objeto: no lleva @Valid, ni BindingResult, ni el if de errores, solo el id de la ruta.
	// El profesor no comprueba si el evento existe. En clase, borrar un id que no existía dio 500, y dejó de
	// tarea que respondiera con un 404 y un mensaje claro, igual que el GET por id. Esa tarea no está aplicada.
	// Aquí, con Spring Data actual, borrar un id inexistente no falla y responde 204.
	// 204 significa "sin contenido": por estándar esa respuesta no lleva cuerpo, así que el mensaje
	// "se eliminó satisfactoriamente" nunca le llega al cliente. Se dejó como lo dictó el profesor.
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
		Map<String, Object> respuesta = new HashMap<>();

		try {
			eventoService.delete(id);
		} catch (DataAccessException e) {
			respuesta.put("mensaje", "Error al eliminar el evento");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		respuesta.put("mensaje", "El evento se eliminó satisfactoriamente");
		return new ResponseEntity<>(respuesta, HttpStatus.NO_CONTENT);
	}

}
