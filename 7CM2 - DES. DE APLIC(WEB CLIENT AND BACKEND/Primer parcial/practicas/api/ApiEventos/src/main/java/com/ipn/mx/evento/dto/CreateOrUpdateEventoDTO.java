package com.ipn.mx.evento.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateOrUpdateEventoDTO(
        @NotBlank(message = "El nombre del evento no debe estar vacío")
        @Size(min = 4, max = 200, message = "El nombre del evento debe tener entre 4 y 200")
        String nombreEvento,

        @NotBlank(message = "La descripción del evento no debe estar vacía")
        @Size(min = 4, max = 500, message = "La descripción del evento debe tener entre 4 y 500")
        String descripcionEvento,

        @NotNull(message = "La fecha es obligatoria")
        @FutureOrPresent(message = "La fecha del evento debe ser hoy o en el futuro")
        LocalDate fechaCreacion
) {
}
