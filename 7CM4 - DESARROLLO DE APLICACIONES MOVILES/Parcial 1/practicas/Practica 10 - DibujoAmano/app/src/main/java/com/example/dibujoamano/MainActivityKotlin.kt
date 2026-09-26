package com.example.dibujoamano

import android.os.Bundle
import android.app.Activity

// EJEMPLO 2 (Kotlin): dibujo a mano con Canvas y Paint.
class MainActivityKotlin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ml = MiLienzoKotlin(this)
        setContentView(ml)
    }
}
