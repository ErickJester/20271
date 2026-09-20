package com.ipn.mx.exception;

import com.ipn.mx.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ?valor=abc, ?valor=20,5 ...
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> valorNoNumerico(
        MethodArgumentTypeMismatchException ex, HttpServletRequest request
    ) {
        String mensaje = "El parámetro '%s' debe ser un número (con punto decimal); se recibió '%s'."
            .formatted(ex.getName(), ex.getValue());
        return responder(HttpStatus.BAD_REQUEST, mensaje, request);
    }

    // No se mandó ?valor= o viene vacío
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> parametroFaltante(
        MissingServletRequestParameterException ex, HttpServletRequest request
    ) {
        String mensaje = "Falta el parámetro obligatorio '%s'.".formatted(ex.getParameterName());
        return responder(HttpStatus.BAD_REQUEST, mensaje, request);
    }

    // Por debajo del cero absoluto, NaN o infinito
    @ExceptionHandler(ValorFueraDeRangoException.class)
    public ResponseEntity<ErrorResponse> fueraDeRango(
        ValorFueraDeRangoException ex, HttpServletRequest request
    ) {
        return responder(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> responder(
        HttpStatus estado, String mensaje, HttpServletRequest request
    ) {
        ErrorResponse cuerpo = new ErrorResponse(
            estado.value(), estado.getReasonPhrase(), mensaje, request.getRequestURI());
        return ResponseEntity.status(estado).body(cuerpo);
    }
}
