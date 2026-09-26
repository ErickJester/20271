package com.example.dibujoamano

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

class MiLienzoKotlin(context: Context) : View(context) {
    private var x = 50f
    private var y = 50f
    private var s = ""
    private val pa = Path()

    // Pincel del encabezado con el nombre del alumno (visible en cada captura)
    private val pincelNombre = Paint().apply {
        color = Color.BLACK
        textSize = 52f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.rgb(200, 200, 200))
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 6f
            color = Color.RED
        }
        if (s == "abajo") pa.moveTo(x, y)
        if (s == "mover") pa.lineTo(x, y)
        canvas.drawPath(pa, paint)
        canvas.drawText("Angel Frausto Robles", width / 2f, 190f, pincelNombre)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        x = event.x
        y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> s = "abajo"
            MotionEvent.ACTION_MOVE -> s = "mover"
        }
        invalidate()
        return true
    }
}
