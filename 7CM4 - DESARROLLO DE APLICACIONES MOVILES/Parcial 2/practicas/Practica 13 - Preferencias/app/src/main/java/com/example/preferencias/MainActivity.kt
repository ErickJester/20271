package com.example.preferencias

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

/** Menú de la práctica: un botón por ejemplo/ejercicio. */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        abrir(R.id.bEjemplo1, Ejemplo1Activity::class.java)
        abrir(R.id.bEj1, CoordenadasActivity::class.java)
        abrir(R.id.bEj2, ImagenActivity::class.java)
        abrir(R.id.bEj3, Ejercicio3Activity::class.java)
    }

    private fun abrir(id: Int, destino: Class<*>) {
        findViewById<Button>(id).setOnClickListener {
            startActivity(Intent(this, destino))
        }
    }
}
