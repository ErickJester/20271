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

// La entidad Evento representa una fila de la tabla Evento en MySQL: cada atributo de la clase es una columna.
// Hibernate (JPA) crea la tabla sola a partir de esta clase, porque en application.properties está
// spring.jpa.hibernate.ddl-auto=update. Esta misma clase es también el JSON que se envía y se recibe en la API.

// 29sept: Lombok como en la clase del primer día. Estas tres anotaciones escriben el código repetitivo al
// compilar, para no teclearlo:
//  - @Data genera los getters y setters (getNombreEvento, setNombreEvento...), equals, hashCode y toString.
//  - @NoArgsConstructor genera el constructor vacío, que JPA y Jackson necesitan para crear objetos.
//  - @AllArgsConstructor genera un constructor con todos los campos.
@AllArgsConstructor
@NoArgsConstructor
@Data
// @Entity marca la clase como tabla. zip: la tabla se llama Evento (con mayúscula, por el name) y las columnas
// llevan el nombre camelCase de cada @Column. Eso depende de las propiedades de naming strategy en
// application.properties; sin ellas, Spring Boot convertiría todo a snake_case (id_evento, nombre_evento).
@Entity(name = "Evento")
public class Evento implements Serializable {

	private static final long serialVersionUID = 1L;

	// Llave primaria. IDENTITY significa que MySQL asigna el número con AUTO_INCREMENT: al crear un evento no
	// se manda el id, la base lo genera y lo devuelve. Por eso el tipo es Long (admite null antes de guardarse).
	// zip: el id es de tipo Long, y aquí la columna se llama idEvento (el zip la llama "Evento", parece descuido).
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idEvento")
	private Long idEvento;

	// 29sept: el nombre tiene de 4 a 200 caracteres y es único. Las anotaciones de validación (@NotBlank, @Size,
	// @NotNull) se revisan cuando el controlador recibe el JSON con @Valid; si fallan, responde 400 con el
	// message de la regla. @NotBlank rechaza vacío o solo espacios. @Size limita la longitud. En @Column,
	// length = 200 es el tamaño del VARCHAR en MySQL, nullable = false hace la columna NOT NULL y unique = true
	// impide dos eventos con el mismo nombre (repetirlo da el error 500 que el profesor probó con "Expo ESCOM").
	// Los mensajes son los del zip.
	@NotBlank(message = "El nombre del evento no debe estar vacío")
	@Size(min = 4, max = 200, message = "El nombre del evento debe tener entre 4-200 caracteres")
	@Column(name = "nombreEvento", length = 200, nullable = false, unique = true)
	private String nombreEvento;

    // 29sept: la descripción tiene de 4 a 500 caracteres. zip: allí el mensaje de @Size dice "El nombre del
	// evento" (un error de copiar y pegar); aquí está corregido para que diga "La descripción".
	@NotBlank(message = "La descripción del evento no debe estar vacía")
	@Size(min = 4, max = 500, message = "La descripción del evento debe tener entre 4-500 caracteres")
	@Column(name = "descripcionEvento", length = 500, nullable = false)
	private String descripcionEvento;

	// La fecha es un LocalDate (solo día, mes y año, sin hora). En JSON se escribe como "2026-10-27".
	// @NotNull obliga a mandarla. En la clase del correo el profesor comenta que debió llamarse "fecha del
	// evento", porque en realidad es la fecha en que el evento se llevará a cabo; el nombre se quedó como fechaCreacion.
	@NotNull(message = "La fecha es obligatoria")
	@Column(name = "fechaCreacion", nullable = false)
	private LocalDate fechaCreacion;

}
