package com.ipn.mx.evento.repository;

import com.ipn.mx.evento.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

// 29sept: el repositorio es la capa que habla con la base de datos. Es una interfaz vacía a propósito: al
// extender JpaRepository<Evento, Long> (la entidad y el tipo de su id), Spring genera la implementación y
// regala los métodos del CRUD: findAll, findById, save, deleteById, count y más. No hay que escribir SQL.
// Si hiciera falta una consulta especial, aquí se declararía un método con un nombre como findByNombreEvento.
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
