package com.ipn.mx.archivo.domain; //14oct

import jakarta.persistence.Column; //14oct
import jakarta.persistence.Entity; //14oct
import jakarta.persistence.GeneratedValue; //14oct
import jakarta.persistence.GenerationType; //14oct
import jakarta.persistence.Id; //14oct
import jakarta.persistence.Table; //14oct
import lombok.AllArgsConstructor; //14oct
import lombok.Builder; //14oct
import lombok.Data; //14oct
import lombok.NoArgsConstructor; //14oct

// Tarea de la clase de archivos: guardar el archivo en el disco y no en la base de datos.
// Esta entidad es la contraparte de Archivo. En lugar del arreglo de bytes (datosArchivo, un mediumblob)
// solo guarda la ruta donde quedó el archivo en el disco. Así la tabla pesa unos cuantos bytes por fila.
@Data //14oct
@Entity //14oct
@Builder //14oct
@NoArgsConstructor //14oct
@AllArgsConstructor //14oct
@Table(name = "ArchivoDisco") //14oct
public class ArchivoDisco { //14oct
    @Id //14oct
    @GeneratedValue(strategy = GenerationType.IDENTITY) //14oct
    private Long idArchivoDisco; //14oct
    private String nombreArchivo; //14oct
    private String tipoArchivo; //14oct

    // Ruta completa del archivo en el disco, por ejemplo C:\...\archivos-subidos\3f2a..._foto.jpg
    @Column(length = 500) //14oct
    private String rutaArchivo; //14oct
} //14oct
