package com.ipn.mx.archivo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ipn.mx.archivo.domain.Archivo;
public interface ArchivoRepositorio extends JpaRepository<Archivo, Long> {
    
}
