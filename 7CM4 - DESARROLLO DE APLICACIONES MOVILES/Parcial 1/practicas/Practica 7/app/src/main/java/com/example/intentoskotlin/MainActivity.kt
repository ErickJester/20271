package com.example.intentoskotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coefA = findViewById<EditText>(R.id.coefA)
        val coefB = findViewById<EditText>(R.id.coefB)
        val coefC = findViewById<EditText>(R.id.coefC)
        val solveButton = findViewById<Button>(R.id.solveButton)

        solveButton.setOnClickListener {
            val a = coefA.text.toString().toDoubleOrNull()
            val b = coefB.text.toString().toDoubleOrNull()
            val c = coefC.text.toString().toDoubleOrNull()

            if (a != null && b != null && c != null) {
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra("coefA", a)
                    putExtra("coefB", b)
                    putExtra("coefC", c)
                }
                startActivity(intent)
            } else {
                coefA.error = "Por favor, ingresa un número válido"
                coefB.error = "Por favor, ingresa un número válido"
                coefC.error = "Por favor, ingresa un número válido"
            }
        }
    }
}
