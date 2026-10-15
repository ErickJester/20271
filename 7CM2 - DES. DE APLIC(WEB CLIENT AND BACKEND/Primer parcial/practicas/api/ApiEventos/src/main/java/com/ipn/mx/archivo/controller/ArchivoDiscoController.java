package com.ipn.mx.archivo.controller; //14oct

import com.ipn.mx.archivo.domain.ArchivoDisco; //14oct
import com.ipn.mx.archivo.dto.RespuestaDTO; //14oct
import com.ipn.mx.archivo.service.ArchivoDiscoService; //14oct
import java.io.IOException; //14oct
import org.springframework.beans.factory.annotation.Autowired; //14oct
import org.springframework.http.HttpHeaders; //14oct
import org.springframework.http.HttpStatus; //14oct
import org.springframework.http.MediaType; //14oct
import org.springframework.http.ResponseEntity; //14oct
import org.springframework.web.bind.annotation.GetMapping; //14oct
import org.springframework.web.bind.annotation.PathVariable; //14oct
import org.springframework.web.bind.annotation.PostMapping; //14oct
import org.springframework.web.bind.annotation.RequestMapping; //14oct
import org.springframework.web.bind.annotation.RequestParam; //14oct
import org.springframework.web.bind.annotation.RestController; //14oct
import org.springframework.web.multipart.MultipartFile; //14oct

// Versión de la tarea: los archivos se guardan en el disco y la base de datos solo guarda la ruta.
// Funciona igual que ArchivoController (el que guarda en la base de datos), con otra ruta base.
@RestController //14oct
@RequestMapping("/api/v1/apiArchivosDisco") //14oct
public class ArchivoDiscoController { //14oct
    @Autowired //14oct
    private ArchivoDiscoService service; //14oct

    @PostMapping("/subirArchivo") //14oct
    public ResponseEntity<RespuestaDTO> subirArchivo(@RequestParam MultipartFile archivo) throws IOException { //14oct
        service.guardarArchivo(archivo); //14oct
        return ResponseEntity.status(HttpStatus.CREATED).body( //14oct
                new RespuestaDTO("Archivo guardado en disco satisfactoriamente") //14oct
        ); //14oct
    } //14oct

    @GetMapping("/descargarArchivo/{id}") //14oct
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable Long id) throws IOException { //14oct
        ArchivoDisco a = service.descargarArchivo(id).get(); //14oct

        // Si el cliente no mandó el tipo del archivo, se usa el genérico para binarios.
        String tipo = a.getTipoArchivo() != null ? a.getTipoArchivo() : "application/octet-stream"; //14oct
        HttpHeaders header = new HttpHeaders(); //14oct
        header.setContentType(MediaType.valueOf(tipo)); //14oct
        header.add("Content-Disposition", "attachment; filename=\"" + a.getNombreArchivo() + "\""); //14oct

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(service.leerBytes(a)); //14oct
    } //14oct
} //14oct
