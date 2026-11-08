package com.ipn.mx.archivo.controller;


import com.ipn.mx.archivo.domain.Archivo;
import com.ipn.mx.archivo.dto.RespuestaDTO;
import com.ipn.mx.archivo.service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/apiArchivos")

public class ArchivoController {
    @Autowired
    private ArchivoService service;

    @PostMapping("/subirArchivo")
    public ResponseEntity<RespuestaDTO> subirArchivo(
            @RequestParam MultipartFile archivo
            ) throws IOException {
        service.guardarArchivo(archivo);
        return ResponseEntity.status(HttpStatus.OK).body(
                new RespuestaDTO("Archivo subido satisfactoriamente")
                );
    }
    @GetMapping ("/descargarArchivo/{id}")
    public ResponseEntity <byte[]> descargarArchivo(@PathVariable Long id)
            throws FileNotFoundException {
        Archivo a = service.descargarArchivo(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE,
                        a.getTipoArchivo())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                + a.getNombreArchivo() + "\""
                ).body(a.getDatosArchivo());

    }
}
