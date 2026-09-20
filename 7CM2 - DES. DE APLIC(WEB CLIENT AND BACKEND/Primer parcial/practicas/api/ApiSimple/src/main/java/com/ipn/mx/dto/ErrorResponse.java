package com.ipn.mx.dto;

public record ErrorResponse(
    int estado,
    String error,
    String mensaje,
    String ruta
) {}
