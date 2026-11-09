package com.example.preferencias

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

/** Ejercicio 3: el Ejemplo 1 convertido a Kotlin (archivo preferencias_kotlin.xml). */
class Ejercicio3Activity : Activity() {

    private lateinit var sp: SharedPreferences
    private lateinit var jetn: EditText
    private lateinit var jetx: EditText
    private lateinit var jety: EditText

    private var s: String = ""
    private var x: Float = 0f
    private var y: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        jetn = findViewById<EditText>(R.id.xetn)
        jetx = findViewById<EditText>(R.id.xetx)
        jety = findViewById<EditText>(R.id.xety)

        sp = getSharedPreferences("preferencias_kotlin", Context.MODE_PRIVATE)

        s = sp.getString("titulo", "ESCOM") ?: "ESCOM"
        x = sp.getFloat("x", 0f)
        y = sp.getFloat("y", 0f)

        jetn.setText(s)
        jetx.setText(x.toString())
        jety.setText(y.toString())
    }

    override fun onPause() {
        super.onPause()

        val nuevoX = jetx.text.toString().toFloatOrNull()
        val nuevoY = jety.text.toString().toFloatOrNull()
        if (nuevoX == null || nuevoY == null) {
            Toast.makeText(this, "Eje X o Y inválido: no se guardó", Toast.LENGTH_LONG).show()
            return
        }
        s = jetn.text.toString()
        x = nuevoX
        y = nuevoY

        val miEditor = sp.edit()
        miEditor.putString("titulo", s)
        miEditor.putFloat("x", x)
        miEditor.putFloat("y", y)
        miEditor.commit()

        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_LONG).show()
    }
}
