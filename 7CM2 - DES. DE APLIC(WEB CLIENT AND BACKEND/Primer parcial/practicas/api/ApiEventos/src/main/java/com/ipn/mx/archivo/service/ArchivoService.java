package com.ipn.mx.archivo.service;

import com.ipn.mx.archivo.domain.Archivo;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public interface ArchivoService {
    Archivo guardarArchivo(MultipartFile archivo) throws IOException; 
    Optional <Archivo> descargarArchivo(Long id) throws FileNotFoundException;  
}
