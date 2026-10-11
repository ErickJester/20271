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
import java.util.stream.Stream;
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

			// clase del PDF (8 oct): título en Courier 14 azul, texto en Helvetica 10 azul,
			// encabezados en Helvetica 9 negrita rojo sobre fondo cian
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

			for (Evento e : listaDeEventos) {
				PdfPCell celdaIdEvento = new PdfPCell(new Phrase(String.valueOf(e.getIdEvento()), letraTexto));
				PdfPCell celdaNombreEvento = new PdfPCell(new Phrase(String.valueOf(e.getNombreEvento()), letraTexto));
				PdfPCell celdaDescripcionEvento = new PdfPCell(new Phrase(String.valueOf(e.getDescripcionEvento()), letraTexto));
				PdfPCell celdaFechaEvento = new PdfPCell(new Phrase(String.valueOf(e.getFechaCreacion()), letraTexto));

				tabla.addCell(celdaIdEvento);
				tabla.addCell(celdaNombreEvento);
				tabla.addCell(celdaDescripcionEvento);
				tabla.addCell(celdaFechaEvento);
			}

			documento.add(tabla);
			documento.close();
		} catch (DocumentException e) {
			throw new IllegalStateException("No se pudo generar el PDF de eventos", e);
		}

		return new ByteArrayInputStream(salida.toByteArray());
	}

}
