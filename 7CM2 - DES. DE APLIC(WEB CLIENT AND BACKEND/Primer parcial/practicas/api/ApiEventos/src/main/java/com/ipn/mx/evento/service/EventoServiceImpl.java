package com.ipn.mx.evento.service;

import com.ipn.mx.evento.domain.Evento;
import com.ipn.mx.evento.dto.CreateOrUpdateEventoDTO;
import com.ipn.mx.evento.dto.EventoDTO;
import com.ipn.mx.evento.mapper.EventoMapper;
import com.ipn.mx.evento.repository.EventoRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventoDTO> findAll() {
        return EventoMapper.convertirAListaDTO(eventoRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public EventoDTO findById(Long id) {
        return eventoRepository.findById(id)
                .map(EventoMapper::convertirADTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "El evento con ID: " + id + " no existe en la base de datos"));
    }

    @Override
    @Transactional
    public EventoDTO save(CreateOrUpdateEventoDTO dto) {
        Evento evento = EventoMapper.convertirEntity(dto);
        return EventoMapper.convertirADTO(eventoRepository.save(evento));
    }

    @Override
    @Transactional
    public EventoDTO update(Long id, CreateOrUpdateEventoDTO dto) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Error al actualizar: el ID " + id + " no se encuentra en la base de datos"));
        EventoMapper.actualizarAPartirDeDTO(dto, evento);
        return EventoMapper.convertirADTO(eventoRepository.save(evento));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "El evento con ID: " + id + " no fue encontrado"));
        eventoRepository.delete(evento);
    }

    @Override
    public ByteArrayInputStream generarReporte(List<EventoDTO> listaDeEventos) {
        Document documento = new Document();
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(documento, salida);
            documento.open();

            Font tipoLetra = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
            Paragraph parrafo = new Paragraph("Lista de eventos", tipoLetra);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            documento.add(parrafo);
            documento.add(Chunk.NEWLINE);

            Font letraTexto = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLUE);
            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);

            Stream.of("Clave del evento", "Nombre del evento", "Descripción del evento", "Fecha del evento")
                    .forEach(headerTitle -> {
                        Font letraEncabezadoTabla = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, BaseColor.RED);
                        PdfPCell encabezados = new PdfPCell(new Phrase(headerTitle, letraEncabezadoTabla));
                        encabezados.setVerticalAlignment(Element.ALIGN_CENTER);
                        encabezados.setHorizontalAlignment(Element.ALIGN_CENTER);
                        encabezados.setBackgroundColor(BaseColor.CYAN);
                        encabezados.setBorderWidth(1);
                        tabla.addCell(encabezados);
                    });

            for (EventoDTO e : listaDeEventos) {
                PdfPCell celdaIdEvento = new PdfPCell(new Phrase(String.valueOf(e.idEvento()), letraTexto));
                PdfPCell celdaNombreEvento = new PdfPCell(new Phrase(String.valueOf(e.nombreEvento()), letraTexto));
                PdfPCell celdaDescripcionEvento = new PdfPCell(new Phrase(String.valueOf(e.descripcionEvento()), letraTexto));
                PdfPCell celdaFechaEvento = new PdfPCell(new Phrase(String.valueOf(e.fechaCreacion()), letraTexto));

                tabla.addCell(celdaIdEvento);
                tabla.addCell(celdaNombreEvento);
                tabla.addCell(celdaDescripcionEvento);
                tabla.addCell(celdaFechaEvento);
            }

            documento.add(tabla);
            documento.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(salida.toByteArray());
    }

}
