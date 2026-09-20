package com.ipn.mx.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ipn.mx.exception.ValorFueraDeRangoException;
import org.junit.jupiter.api.Test;

class TemperaturaServiceTest {

    private final TemperaturaService servicio = new TemperaturaService();

    @Test
    void convierteLasSeisEscalas() {
        assertEquals(50.0, servicio.celsiusAFahrenheit(10));
        assertEquals(293.15, servicio.celsiusAKelvin(20));
        assertEquals(0.0, servicio.fahrenheitACelsius(32));
        assertEquals(273.15, servicio.fahrenheitAKelvin(32));
        assertEquals(0.0, servicio.kelvinACelsius(273.15));
        assertEquals(32.0, servicio.kelvinAFahrenheit(273.15));
    }

    @Test
    void aceptaElCeroAbsolutoExacto() {
        assertEquals(-459.67, servicio.celsiusAFahrenheit(-273.15));
        assertEquals(0.0, servicio.celsiusAKelvin(-273.15));
        assertEquals(-273.15, servicio.fahrenheitACelsius(-459.67));
        assertEquals(0.0, servicio.fahrenheitAKelvin(-459.67));
        assertEquals(-273.15, servicio.kelvinACelsius(0));
        assertEquals(-459.67, servicio.kelvinAFahrenheit(0));
    }

    @Test
    void rechazaValoresPorDebajoDelCeroAbsoluto() {
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.celsiusAFahrenheit(-273.16));
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.celsiusAKelvin(-500));
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.fahrenheitACelsius(-459.68));
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.fahrenheitAKelvin(-1000));
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.kelvinACelsius(-0.01));
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.kelvinAFahrenheit(-5));
    }

    @Test
    void rechazaNaNEInfinito() {
        assertThrows(ValorFueraDeRangoException.class, () -> servicio.celsiusAKelvin(Double.NaN));
        assertThrows(ValorFueraDeRangoException.class,
            () -> servicio.kelvinACelsius(Double.POSITIVE_INFINITY));
    }
}
