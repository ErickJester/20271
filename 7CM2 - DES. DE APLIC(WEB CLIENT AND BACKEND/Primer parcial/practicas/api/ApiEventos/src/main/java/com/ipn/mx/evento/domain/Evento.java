package com.ipn.mx.evento.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// La entidad Evento representa una fila de la tabla Evento en MySQL: cada atributo de la clase es una columna.
// En la clase del 28 oct se separan las validaciones hacia CreateOrUpdateEventoDTO,
// quedando esta entidad únicamente con el mapeo a la base de datos.
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEvento")
	private Long idEvento;

	@Column(name = "nombreEvento", length = 200, nullable = false, unique = true)
	private String nombreEvento;

	@Column(name = "descripcionEvento", length = 500, nullable = false)
	private String descripcionEvento;

	@Column(name = "fechaCreacion", nullable = false)
	private LocalDate fechaCreacion;

}
