package com.example.practica4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(b: Bundle?) {
        super.onCreate(b)
        setContentView(R.layout.activity_main)

        val xnatural = findViewById<EditText>(R.id.xnatural)
        val xboton = findViewById<Button>(R.id.xboton)
        val resultText = findViewById<TextView>(R.id.xcalcular)

        xboton.setOnClickListener {

            val numero = xnatural.text.toString().trim().toIntOrNull() ?: -1

            if (numero < 1) {
                resultText.text = "Ingresa un número natural válido (entero mayor o igual a 1)."
                return@setOnClickListener
            }

            resultText.text = buildString {
                appendLine("Número analizado: $numero")
                appendLine("-".repeat(28))

                appendLine(marca(esPrimo(numero)) + " Primo")
                appendLine(marca(esFibonacci(numero)) + " Fibonacci")

                // Ejercicio 1: número maravilloso
                val collatz = secuenciaMaravilloso(numero)
                appendLine(marca(collatz.last() == 1L) + " Maravilloso (Collatz)")
                appendLine("   ${collatz.joinToString(" -> ")}")
                appendLine("   pasos: ${collatz.size - 1}")

                // Ejercicio 2: constante de Kaprekar (solo 3 o 4 dígitos)
                appendLine("-".repeat(28))
                val kaprekar = pasosKaprekar(numero)
                when {
                    kaprekar == null ->
                        appendLine("Kaprekar: no aplica (requiere 3 o 4 dígitos)")
                    kaprekar.last() == 495 || kaprekar.last() == 6174 -> {
                        appendLine("Kaprekar: SÍ llega a la constante ${kaprekar.last()}")
                        appendLine("   ${kaprekar.joinToString(" -> ")}")
                        appendLine("   pasos: ${kaprekar.size - 1}")
                    }
                    else ->
                        appendLine("Kaprekar: no (dígitos iguales, la rutina llega a 0)")
                }
            }
        }
    }

    private fun marca(condicion: Boolean): String = if (condicion) "[SÍ]" else "[NO]"

    /** Primo: sin divisores entre 2 y la raíz cuadrada. */
    private fun esPrimo(num: Int): Boolean {
        if (num <= 1) return false
        for (i in 2..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
    }

    /** Fibonacci: genera 0,1,1,2,3,5... hasta alcanzar o pasar num. */
    private fun esFibonacci(num: Int): Boolean {
        var a = 0
        var b = 1
        while (a < num) {
            val siguiente = a + b
            a = b
            b = siguiente
        }
        return a == num
    }

    /**
     * Número maravilloso (conjetura de Collatz): par -> n/2, impar -> 3n+1.
     * Devuelve la secuencia completa; es maravilloso si termina en 1.
     * Se usa Long porque 3n+1 puede exceder el rango de Int.
     */
    private fun secuenciaMaravilloso(num: Int): List<Long> {
        val secuencia = mutableListOf(num.toLong())
        var n = num.toLong()
        while (n != 1L && secuencia.size < 1000) {
            n = if (n % 2 == 0L) n / 2 else 3 * n + 1
            secuencia.add(n)
        }
        return secuencia
    }

    /**
     * Rutina de Kaprekar para números de 3 o 4 dígitos.
     * Cada paso: (dígitos en orden descendente) - (dígitos en orden ascendente).
     * Converge a 495 (3 dígitos) o 6174 (4 dígitos), salvo dígitos repetidos.
     * Devuelve null si el número no tiene 3 ni 4 dígitos.
     */
    private fun pasosKaprekar(num: Int): List<Int>? {
        val digitos = num.toString().length
        if (digitos !in 3..4) return null
        val objetivo = if (digitos == 3) 495 else 6174

        val pasos = mutableListOf(num)
        var n = num
        while (n != objetivo && n != 0 && pasos.size < 20) {
            val ascendente = n.toString().padStart(digitos, '0').toList().sorted().joinToString("")
            val descendente = ascendente.reversed()
            n = descendente.toInt() - ascendente.toInt()
            pasos.add(n)
        }
        return pasos
    }
}
