package com.ipn.mx.evento.controller;

import com.ipn.mx.evento.dto.CreateOrUpdateEventoDTO;
import com.ipn.mx.evento.dto.EventoDTO;
import com.ipn.mx.evento.service.EventoService;
import com.ipn.mx.evento.utilerias.service.EmailService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/eventos")
@Slf4j
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EmailService emailService;

    // Lista de eventos: 200 OK con lista o 204 No Content si está vacía
    @GetMapping
    public ResponseEntity<List<EventoDTO>> readAll() {
        List<EventoDTO> eventos = eventoService.findAll();
        if (eventos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(eventos);
    }

    // Reporte PDF adaptado a List<EventoDTO>
    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> generarPdf() {
        List<EventoDTO> listaDeEventos = eventoService.findAll();
        ByteArrayInputStream salidaByteArray = eventoService.generarReporte(listaDeEventos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reporteEventos.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(salidaByteArray));
    }

    // Consulta por ID: 200 OK si existe, 404 Not Found si no, 500 si falla la BD
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            EventoDTO evento = eventoService.findById(id);
            return ResponseEntity.ok(evento);
        } catch (EntityNotFoundException e) {
            respuesta.put("mensaje", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al realizar la consulta");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Creación: valida el DTO de entrada (400), guarda (201), envía correo sin tumbar la petición
    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody CreateOrUpdateEventoDTO dto,
            BindingResult result) {

        Map<String, Object> respuesta = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            respuesta.put("errores", errores);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        EventoDTO eventoNuevo;
        try {
            eventoNuevo = eventoService.save(dto);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al realizar el insert en la base de datos");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            emailService.enviarCorreo(eventoNuevo);
        } catch (MailException e) {
            log.warn("No se pudo enviar el correo del evento {}", eventoNuevo.idEvento(), e);
        }

        respuesta.put("mensaje", "El evento se ha creado con éxito");
        respuesta.put("evento", eventoNuevo);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // Actualización: valida DTO (400), actualiza por ID (200), o lanza 404 / 500
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody CreateOrUpdateEventoDTO dto,
            BindingResult result,
            @PathVariable Long id) {

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
            EventoDTO eventoActualizado = eventoService.update(id, dto);
            respuesta.put("mensaje", "El evento se ha actualizado con éxito");
            respuesta.put("evento", eventoActualizado);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            respuesta.put("mensaje", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al actualizar el evento en la base de datos");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminación (Tarea 1): busca antes de borrar, responde 404 si no existe, 200 con mensaje o 500
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();

        try {
            eventoService.delete(id);
            respuesta.put("mensaje", "El evento se eliminó con éxito");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            respuesta.put("mensaje", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
        } catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al eliminar el evento de la base de datos");
            respuesta.put("error", e.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
