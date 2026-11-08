package com.ipn.mx.usuario.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Rol")

public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(name = "nombreRol", length = 50, nullable = false)
    private String nombreRol;

    @Column(name = "descripcionRol", length = 150, nullable = false)
    private String descripcionRol;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDate fecha;




}
