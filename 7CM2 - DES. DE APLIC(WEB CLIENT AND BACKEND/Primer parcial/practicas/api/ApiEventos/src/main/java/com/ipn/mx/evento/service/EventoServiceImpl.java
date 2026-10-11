package com.ipn.mx.evento.service;

import com.ipn.mx.evento.domain.Evento;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// zip: la interfaz y su implementación viven en el mismo paquete (el zip llama a la clase EventoSerrviceImpl, con typo)
@Service
public class EventoServiceImpl implements EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Evento findById(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Evento save(Evento evento) {
		return eventoRepository.save(evento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		eventoRepository.deleteById(id);
	}

	@Override
	public ByteArrayInputStream generarReporte(List<Evento> listaDeEventos) {
		Document documento = new Document();
		ByteArrayOutputStream salida = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(documento, salida);
			documento.open();

			Font letraTexto = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);
			Font letraEncabezadoTabla = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);

			Paragraph parrafo = new Paragraph("Lista de Eventos", letraTexto);
			parrafo.setAlignment(Element.ALIGN_CENTER);
			documento.add(parrafo);
			documento.add(new Chunk(Chunk.NEWLINE));

			PdfPTable tabla = new PdfPTable(4);
			tabla.setWidthPercentage(100);

			String[] encabezados = {"ID Evento", "Nombre del Evento", "Descripción del Evento", "Fecha de Creación"};
			for (String encabezado : encabezados) {
				PdfPCell celda = new PdfPCell(new Phrase(encabezado, letraEncabezadoTabla));
				celda.setHorizontalAlignment(Element.ALIGN_CENTER);
				celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
				celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
				celda.setBorderWidth(1);
				tabla.addCell(celda);
			}

			for (Evento e : listaDeEventos) {
				String[] datos = {
					String.valueOf(e.getIdEvento()),
					e.getNombreEvento(),
					e.getDescripcionEvento(),
					String.valueOf(e.getFechaCreacion())
				};
				for (String dato : datos) {
					PdfPCell celda = new PdfPCell(new Phrase(dato, letraTexto));
					celda.setHorizontalAlignment(Element.ALIGN_CENTER);
					tabla.addCell(celda);
				}
			}

			documento.add(tabla);
			documento.close();
		} catch (DocumentException e) {
			throw new IllegalStateException("No se pudo generar el PDF de eventos", e);
		}

		return new ByteArrayInputStream(salida.toByteArray());
	}

}
