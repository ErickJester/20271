package com.ipn.mx.archivo.service; //14oct

import com.ipn.mx.archivo.domain.ArchivoDisco; //14oct
import java.io.FileNotFoundException; //14oct
import java.io.IOException; //14oct
import java.util.Optional; //14oct
import org.springframework.web.multipart.MultipartFile; //14oct

public interface ArchivoDiscoService { //14oct
    // Copia el archivo a la carpeta del disco y guarda en la base de datos su nombre, tipo y ruta.
    ArchivoDisco guardarArchivo(MultipartFile archivo) throws IOException; //14oct

    // Busca en la base de datos el registro (con la ruta); lanza error si el id no existe.
    Optional<ArchivoDisco> descargarArchivo(Long id) throws FileNotFoundException; //14oct

    // Lee del disco los bytes del archivo al que apunta la ruta del registro.
    byte[] leerBytes(ArchivoDisco archivo) throws IOException; //14oct
} //14oct
