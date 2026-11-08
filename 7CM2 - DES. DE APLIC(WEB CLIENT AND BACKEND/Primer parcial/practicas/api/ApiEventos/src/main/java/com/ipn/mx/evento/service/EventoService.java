package com.ipn.mx.evento.service;

import com.ipn.mx.evento.dto.CreateOrUpdateEventoDTO;
import com.ipn.mx.evento.dto.EventoDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

// El servicio es el contrato de lo que se puede hacer con los eventos. El controlador depende de esta interfaz y
// no de la clase concreta. En la clase del 28 oct se actualiza para trabajar exclusivamente con DTOs.
public interface EventoService {

    public List<EventoDTO> findAll();

    public EventoDTO findById(Long id);

    public EventoDTO save(CreateOrUpdateEventoDTO dto);

    public EventoDTO update(Long id, CreateOrUpdateEventoDTO dto);

    public void delete(Long id);

    public ByteArrayInputStream generarReporte(List<EventoDTO> listaDeEventos);

}
