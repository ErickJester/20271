package com.ipn.mx.archivo.service;

import com.ipn.mx.archivo.domain.Archivo;
import com.ipn.mx.archivo.repository.ArchivoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class ArchivoServiceImpl implements ArchivoService{
    @Autowired
    private ArchivoRepositorio repository;

    @Override
    public  Archivo guardarArchivo (MultipartFile archivo) throws IOException {
        // cleanPath limpia el nombre que manda el cliente (quita rutas como ../ para que no se escape de la carpeta)
        String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());
        // El archivo se guarda dentro de la base de datos: nombre, tipo (por ejemplo image/png) y los bytes
        Archivo a = Archivo.builder()
                .nombreArchivo(nombreArchivo)
                .tipoArchivo(archivo.getContentType())
                .datosArchivo(archivo.getBytes())
                .build();
        return repository.save(a);
    }

    @Override
    public Optional<Archivo> descargarArchivo (Long id) throws FileNotFoundException {
        Optional<Archivo> archivo = repository.findById(id);
        if (archivo.isPresent()){
            return archivo;
        }
        throw new FileNotFoundException("No se encontro el archivo");
    }

}
