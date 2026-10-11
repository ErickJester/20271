package com.ipn.mx.evento.repository;

import com.ipn.mx.evento.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
