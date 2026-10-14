package com.ipn.mx.asistente.domain;

import java.time.LocalDate;

public class Asistente {
    private Long idAsistente;
    private String nombre;
    private String paterno;
    private String materno;
    private String email;
    private LocalDate fechaNacimiento;
    private String calle;
    private String colonia;
    private int numero;
    private boolean activo;
    private byte [] foto;
}
