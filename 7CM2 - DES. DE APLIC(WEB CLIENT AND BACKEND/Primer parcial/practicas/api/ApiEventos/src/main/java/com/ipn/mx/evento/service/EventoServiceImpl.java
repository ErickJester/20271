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

// Implementación del servicio: aquí vive la lógica entre el controlador y la base de datos. Casi todos los
// métodos solo delegan en el repositorio; el único con trabajo propio es generarReporte, que arma el PDF.
// zip: la interfaz y su implementación viven en el mismo paquete (el zip llama a la clase EventoSerrviceImpl, con typo).
// @Service le dice a Spring que cree un objeto de esta clase y lo ofrezca a quien lo pida con @Autowired.
@Service
public class EventoServiceImpl implements EventoService {

	// El repositorio es quien ejecuta los SELECT, INSERT, UPDATE y DELETE contra MySQL. Spring lo inyecta.
	@Autowired
	private EventoRepository eventoRepository;

	// 29sept: trae todos los eventos (SELECT * FROM Evento). readOnly = true abre una transacción solo de
	// lectura, que le avisa a la base de datos que aquí no se va a modificar nada.
	@Override
	@Transactional(readOnly = true)
	public List<Evento> findAll() {
		return eventoRepository.findAll();
	}

	// 29sept: busca un evento por id. El repositorio devuelve un Optional (una caja que puede estar vacía);
	// orElse(null) entrega el Evento si existe y null si no. zip: el controlador revisa ese null y responde 404.
	@Override
	@Transactional(readOnly = true)
	public Evento findById(Long id) {
		return eventoRepository.findById(id).orElse(null);
	}

	// clase del CRUD (7 oct): sirve para crear y para actualizar. Si el evento no tiene id, JPA hace un INSERT;
	// si ya trae id (como en el PUT, que lo conserva), hace un UPDATE. Devuelve el evento ya guardado, con su id.
	// @Transactional (sin readOnly) agrupa la operación: si algo falla, se deshace todo.
	@Override
	@Transactional
	public Evento save(Evento evento) {
		return eventoRepository.save(evento);
	}

	// clase del CRUD (7 oct): borra el evento con ese id. No devuelve nada (void), por eso el controlador no
	// tiene un objeto que regresar en el DELETE. Con Spring Data actual, un id que no existe no lanza error.
	@Override
	@Transactional
	public void delete(Long id) {
		eventoRepository.deleteById(id);
	}

	// clase del PDF (8 oct): construye el reporte con iText 5 y lo devuelve como bytes, sin escribir ningún
	// archivo en disco. El método ya estaba declarado en la interfaz y el profesor dijo "algún día lo
	// escribiremos": en esa clase lo termina. Sigue una idea parecida al HTML: se define cada pieza (título,
	// tabla, encabezados, celdas) y luego se va agregando al documento.
	// Los pasos son: 1) crear el documento y un destino en memoria, 2) conectarlos con PdfWriter y abrir,
	// 3) escribir el título, 4) crear la tabla y sus encabezados, 5) agregar una fila por cada evento,
	// 6) agregar la tabla, cerrar y entregar los bytes.
	@Override
	public ByteArrayInputStream generarReporte(List<Evento> listaDeEventos) {
		// Document es el PDF vacío. salida es un arreglo de bytes en memoria donde se va escribiendo.
		Document documento = new Document();
		ByteArrayOutputStream salida = new ByteArrayOutputStream();

		try {
			// PdfWriter conecta el documento con la salida: lo que se agregue al documento se escribe en salida.
			PdfWriter.getInstance(documento, salida);
			documento.open();

			// clase del PDF (8 oct): hay tres estilos de letra. Título: Courier 14 azul. Texto de la tabla:
			// Helvetica 10 azul. Encabezados: Helvetica 9 negrita roja sobre fondo cian (más abajo).
			Font tipoLetra = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLUE);

			// Un Paragraph es como la etiqueta <p> de HTML: se crea con su texto y su fuente, se alinea
			// (al centro) y se agrega al documento. Chunk.NEWLINE agrega un salto de línea debajo del título.
			Paragraph parrafo = new Paragraph("Lista de eventos", tipoLetra);
			parrafo.setAlignment(Element.ALIGN_CENTER);
			documento.add(parrafo);
			documento.add(Chunk.NEWLINE);

			// Esta es la fuente de las celdas del cuerpo de la tabla (Helvetica 10 azul, no la del título).
			Font letraTexto = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLUE);

			// La tabla equivale a <table>. El 4 es el número de columnas (id, nombre, descripción y fecha)
			// y ocupa el 100 % del ancho de la página.
			PdfPTable tabla = new PdfPTable(4);
			tabla.setWidthPercentage(100);

			// Encabezados: es el <thead> con sus <th>. Stream.of recorre los cuatro títulos y, por cada uno,
			// forEach crea una celda (PdfPCell), le da formato y la agrega a la tabla. Los títulos son
			// aproximados: el profesor dijo "o como sea que se llame". Formato de cada celda: letra roja en
			// negrita, centrada, fondo cian y borde de grosor 1. La alineación vertical usa ALIGN_CENTER
			// como en clase (lo técnicamente correcto sería ALIGN_MIDDLE, pero con una sola línea no se nota).
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

			// Cuerpo de la tabla (el <tbody>): una vuelta por evento. En cada vuelta se crean cuatro celdas, una
			// por columna, y se agregan en ese orden. La tabla de iText va llenando las filas de cuatro en
			// cuatro celdas. String.valueOf convierte a texto el id y la fecha, que no son String; en el
			// profesor el ciclo va después de los encabezados y antes de agregar la tabla al documento.
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

			// Se agrega la tabla ya llena y se cierra el documento. Cerrar es obligatorio: hasta ese momento el
			// PDF no queda completo en la salida.
			documento.add(tabla);
			documento.close();
		} catch (DocumentException e) {
			// DocumentException es el error que lanza iText si no puede armar el documento. En clase el
			// profesor primero puso un catch vacío "para quitar el error" y luego lo deja con e.printStackTrace(),
			// que solo imprime el error en consola. Si eso pasara, el método igual devuelve lo que alcanzó a escribir.
			e.printStackTrace();
		}

		// toByteArray() saca los bytes del PDF terminado y ByteArrayInputStream los envuelve para que el
		// controlador los pueda enviar como respuesta.
		return new ByteArrayInputStream(salida.toByteArray());
	}

}
