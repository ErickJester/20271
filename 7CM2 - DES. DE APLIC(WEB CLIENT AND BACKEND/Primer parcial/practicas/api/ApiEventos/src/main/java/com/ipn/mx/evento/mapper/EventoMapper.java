package com.ipn.mx.evento.mapper;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.evento.dto.CreateOrUpdateEventoDTO;
import com.ipn.mx.evento.dto.EventoDTO;

import java.util.List;

public class EventoMapper {

    // Entity Class ==> Clase DTO
    public static EventoDTO convertirADTO(Evento evento) {
        if (evento == null) {
            return null;
        }
        return new EventoDTO(
                evento.getIdEvento(),
                evento.getNombreEvento(),
                evento.getDescripcionEvento(),
                evento.getFechaCreacion()
        );
    }

    // DTO ==> Entity
    public static Evento convertirEntity(CreateOrUpdateEventoDTO dto) {
        if (dto == null) {
            return null;
        }
        Evento evento = new Evento();
        evento.setNombreEvento(dto.nombreEvento());
        evento.setDescripcionEvento(dto.descripcionEvento());
        evento.setFechaCreacion(dto.fechaCreacion());
        return evento;
    }

    // DTO ==> Entity para un update
    public static void actualizarAPartirDeDTO(CreateOrUpdateEventoDTO dto, Evento evento) {
        if (dto == null || evento == null) {
            return;
        }
        evento.setNombreEvento(dto.nombreEvento());
        evento.setDescripcionEvento(dto.descripcionEvento());
        evento.setFechaCreacion(dto.fechaCreacion());
    }

    public static List<EventoDTO> convertirAListaDTO(List<Evento> eventos) {
        if (eventos == null) {
            return List.of();
        }
        return eventos.stream()
                .map(EventoMapper::convertirADTO)
                .toList();
    }
}
