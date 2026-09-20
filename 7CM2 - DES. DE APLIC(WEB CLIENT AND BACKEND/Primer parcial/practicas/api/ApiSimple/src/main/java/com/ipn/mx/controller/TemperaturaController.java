package com.ipn.mx.controller;

import com.ipn.mx.dto.ConversionResponse;
import com.ipn.mx.service.TemperaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Conversor de temperaturas entre Celsius, Fahrenheit y Kelvin.
 * Uso: http://localhost:8082/api/temperatura/convertir-celsius-a-fahrenheit?valor=10
 */
@RestController
@RequestMapping("/api/temperatura")
public class TemperaturaController {

    private static final String CELSIUS = "°C";
    private static final String FAHRENHEIT = "°F";
    private static final String KELVIN = "K";

    private final TemperaturaService servicio;

    public TemperaturaController(TemperaturaService servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/convertir-celsius-a-fahrenheit")
    public ResponseEntity<ConversionResponse> celsiusAFahrenheit(
        @RequestParam(name = "valor") Double celsius
    ) {
        Double fahrenheit = servicio.celsiusAFahrenheit(celsius);
        return ResponseEntity.ok(new ConversionResponse(celsius, CELSIUS, fahrenheit, FAHRENHEIT));
    }

    @GetMapping("/convertir-celsius-a-kelvin")
    public ResponseEntity<ConversionResponse> celsiusAKelvin(
        @RequestParam(name = "valor") Double celsius
    ) {
        Double kelvin = servicio.celsiusAKelvin(celsius);
        return ResponseEntity.ok(new ConversionResponse(celsius, CELSIUS, kelvin, KELVIN));
    }

    @GetMapping("/convertir-fahrenheit-a-celsius")
    public ResponseEntity<ConversionResponse> fahrenheitACelsius(
        @RequestParam(name = "valor") Double fahrenheit
    ) {
        Double celsius = servicio.fahrenheitACelsius(fahrenheit);
        return ResponseEntity.ok(new ConversionResponse(fahrenheit, FAHRENHEIT, celsius, CELSIUS));
    }

    @GetMapping("/convertir-fahrenheit-a-kelvin")
    public ResponseEntity<ConversionResponse> fahrenheitAKelvin(
        @RequestParam(name = "valor") Double fahrenheit
    ) {
        Double kelvin = servicio.fahrenheitAKelvin(fahrenheit);
        return ResponseEntity.ok(new ConversionResponse(fahrenheit, FAHRENHEIT, kelvin, KELVIN));
    }

    @GetMapping("/convertir-kelvin-a-celsius")
    public ResponseEntity<ConversionResponse> kelvinACelsius(
        @RequestParam(name = "valor") Double kelvin
    ) {
        Double celsius = servicio.kelvinACelsius(kelvin);
        return ResponseEntity.ok(new ConversionResponse(kelvin, KELVIN, celsius, CELSIUS));
    }

    @GetMapping("/convertir-kelvin-a-fahrenheit")
    public ResponseEntity<ConversionResponse> kelvinAFahrenheit(
        @RequestParam(name = "valor") Double kelvin
    ) {
        Double fahrenheit = servicio.kelvinAFahrenheit(kelvin);
        return ResponseEntity.ok(new ConversionResponse(kelvin, KELVIN, fahrenheit, FAHRENHEIT));
    }
}
