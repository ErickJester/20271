package com.ipn.mx.dto;

public record ConversionResponse(
    Double valorOriginal,
    String unidadOriginal,
    Double valorRespuesta,
    String unidadRespuesta
) {}
