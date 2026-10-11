package com.ipn.mx.evento.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 29sept: Lombok como en la clase del primer día (@Data reemplaza getters, setters, equals, hashCode y toString)
@AllArgsConstructor
@NoArgsConstructor
@Data
// zip: la tabla se llama Evento y las columnas llevan el nombre camelCase de @Column (ver naming en application.properties)
@Entity(name = "Evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEvento")
	private Long idEvento;

	// 29sept: nombre de 4 a 200 caracteres y único. Los mensajes son los del zip
	@NotBlank(message = "El nombre del evento no debe estar vacío")
	@Size(min = 4, max = 200, message = "El nombre del evento debe tener entre 4-200 caracteres")
	@Column(name = "nombreEvento", length = 200, nullable = false, unique = true)
	private String nombreEvento;

	// 29sept: descripción de 4 a 500 caracteres. zip: este mensaje dice "El nombre del evento" (error de copia), aquí corregido
	@NotBlank(message = "La descripción del evento no debe estar vacía")
	@Size(min = 4, max = 500, message = "La descripción del evento debe tener entre 4-500 caracteres")
	@Column(name = "descripcionEvento", length = 500, nullable = false)
	private String descripcionEvento;

	@NotNull(message = "La fecha es obligatoria")
	@Column(name = "fechaCreacion", nullable = false)
	private LocalDate fechaCreacion;

}
