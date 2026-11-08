package com.ipn.mx.evento.dto;

import java.time.LocalDate;

// DTO que representa la salida completa de un evento
public record EventoDTO(
        Long idEvento,
        String nombreEvento,
        String descripcionEvento,
        LocalDate fechaCreacion
) {
}
