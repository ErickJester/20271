package com.example.intentoskotlin

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import kotlin.math.abs
import kotlin.math.sqrt

class ResultActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val coefA = intent.getDoubleExtra("coefA", 0.0)
        val coefB = intent.getDoubleExtra("coefB", 0.0)
        val coefC = intent.getDoubleExtra("coefC", 0.0)

        val discriminante = coefB * coefB - 4 * coefA * coefC
        val resultText = findViewById<TextView>(R.id.resultText)

        val resultado = if (discriminante > 0) {
            val x1 = (-coefB + sqrt(discriminante)) / (2 * coefA)
            val x2 = (-coefB - sqrt(discriminante)) / (2 * coefA)
            "Las raíces son:\nx1 = ${"%.3f".format(x1)}\nx2 = ${"%.3f".format(x2)}"
        } else if (discriminante == 0.0) {
            val x = -coefB / (2 * coefA)
            "La raíz doble es:\nx = ${"%.3f".format(x)}"
        } else {
            val parteReal = -coefB / (2 * coefA)
            val parteImaginaria = abs(sqrt(-discriminante) / (2 * coefA))
            "Raíces complejas:\nx1 = ${"%.3f".format(parteReal)} + ${"%.3f".format(parteImaginaria)}j" +
                "\nx2 = ${"%.3f".format(parteReal)} - ${"%.3f".format(parteImaginaria)}j"
        }

        resultText.text = resultado
    }
}
