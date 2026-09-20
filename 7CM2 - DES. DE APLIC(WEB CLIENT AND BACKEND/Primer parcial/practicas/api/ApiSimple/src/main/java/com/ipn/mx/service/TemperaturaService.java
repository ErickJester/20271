package com.ipn.mx.service;

import com.ipn.mx.exception.ValorFueraDeRangoException;
import org.springframework.stereotype.Service;

@Service
public class TemperaturaService {

    public static final double CERO_ABSOLUTO_CELSIUS = -273.15;
    public static final double CERO_ABSOLUTO_FAHRENHEIT = -459.67;
    public static final double CERO_ABSOLUTO_KELVIN = 0.0;

    public double celsiusAFahrenheit(double celsius) {
        validar(celsius, CERO_ABSOLUTO_CELSIUS, "°C");
        return redondear(celsius * 9 / 5 + 32);
    }

    public double celsiusAKelvin(double celsius) {
        validar(celsius, CERO_ABSOLUTO_CELSIUS, "°C");
        return redondear(celsius + 273.15);
    }

    public double fahrenheitACelsius(double fahrenheit) {
        validar(fahrenheit, CERO_ABSOLUTO_FAHRENHEIT, "°F");
        return redondear((fahrenheit - 32) * 5 / 9);
    }

    public double fahrenheitAKelvin(double fahrenheit) {
        validar(fahrenheit, CERO_ABSOLUTO_FAHRENHEIT, "°F");
        return redondear((fahrenheit - 32) * 5 / 9 + 273.15);
    }

    public double kelvinACelsius(double kelvin) {
        validar(kelvin, CERO_ABSOLUTO_KELVIN, "K");
        return redondear(kelvin - 273.15);
    }

    public double kelvinAFahrenheit(double kelvin) {
        validar(kelvin, CERO_ABSOLUTO_KELVIN, "K");
        return redondear((kelvin - 273.15) * 9 / 5 + 32);
    }

    private void validar(double valor, double minimo, String unidad) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            throw new ValorFueraDeRangoException("El valor debe ser un número finito.");
        }
        if (valor < minimo) {
            throw new ValorFueraDeRangoException(
                "%s %s está por debajo del cero absoluto (mínimo permitido: %s %s)."
                    .formatted(valor, unidad, minimo, unidad));
        }
    }

    // Dos decimales; el "+ 0.0" convierte un posible -0.0 en 0.0.
    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0 + 0.0;
    }
}
