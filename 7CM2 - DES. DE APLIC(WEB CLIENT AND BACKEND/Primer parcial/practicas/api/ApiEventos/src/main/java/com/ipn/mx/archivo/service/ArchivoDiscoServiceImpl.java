package com.ipn.mx.archivo.service; //14oct

import com.ipn.mx.archivo.domain.ArchivoDisco; //14oct
import com.ipn.mx.archivo.repository.ArchivoDiscoRepositorio; //14oct
import java.io.FileNotFoundException; //14oct
import java.io.IOException; //14oct
import java.nio.file.Files; //14oct
import java.nio.file.Path; //14oct
import java.nio.file.StandardCopyOption; //14oct
import java.util.Optional; //14oct
import java.util.UUID; //14oct
import org.springframework.beans.factory.annotation.Autowired; //14oct
import org.springframework.beans.factory.annotation.Value; //14oct
import org.springframework.stereotype.Service; //14oct
import org.springframework.util.StringUtils; //14oct
import org.springframework.web.multipart.MultipartFile; //14oct

@Service //14oct
public class ArchivoDiscoServiceImpl implements ArchivoDiscoService { //14oct
    @Autowired //14oct
    private ArchivoDiscoRepositorio repository; //14oct

    // Carpeta donde se guardan los archivos. Sale de app.archivos.carpeta en application.properties
    // (o de la variable de entorno ARCHIVOS_DIR). Si la ruta es relativa, cuenta desde donde se arranca la API.
    @Value("${app.archivos.carpeta}") //14oct
    private String carpeta; //14oct

    @Override //14oct
    public ArchivoDisco guardarArchivo(MultipartFile archivo) throws IOException { //14oct
        // Se limpia el nombre que manda el cliente y se queda solo con el nombre, sin ninguna ruta.
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename()); //14oct
        nombreArchivo = Path.of(nombreArchivo).getFileName().toString(); //14oct

        // Se crea la carpeta si todavía no existe.
        Path directorio = Path.of(carpeta).toAbsolutePath().normalize(); //14oct
        Files.createDirectories(directorio); //14oct

        // Se le antepone un identificador único al nombre para que dos archivos con el mismo nombre
        // no se sobrescriban en el disco. El nombre original se conserva en la base de datos.
        Path destino = directorio.resolve(UUID.randomUUID() + "_" + nombreArchivo).normalize(); //14oct
        if (!destino.startsWith(directorio)) { //14oct
            throw new IOException("Nombre de archivo no válido"); //14oct
        } //14oct

        // Aquí se escribe el archivo en el disco.
        Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING); //14oct

        // En la base de datos solo queda la referencia: nombre, tipo y ruta.
        ArchivoDisco archivoDisco = ArchivoDisco.builder() //14oct
                .nombreArchivo(nombreArchivo) //14oct
                .tipoArchivo(archivo.getContentType()) //14oct
                .rutaArchivo(destino.toString()) //14oct
                .build(); //14oct
        return repository.save(archivoDisco); //14oct
    } //14oct

    @Override //14oct
    public Optional<ArchivoDisco> descargarArchivo(Long id) throws FileNotFoundException { //14oct
        Optional<ArchivoDisco> archivo = repository.findById(id); //14oct
        if (archivo.isPresent()) { //14oct
            return archivo; //14oct
        } //14oct
        throw new FileNotFoundException("No existe el elemento buscado"); //14oct
    } //14oct

    @Override //14oct
    public byte[] leerBytes(ArchivoDisco archivo) throws IOException { //14oct
        Path ruta = Path.of(archivo.getRutaArchivo()); //14oct
        if (!Files.exists(ruta)) { //14oct
            throw new FileNotFoundException("El archivo ya no está en el disco"); //14oct
        } //14oct
        return Files.readAllBytes(ruta); //14oct
    } //14oct
} //14oct
