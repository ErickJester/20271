package com.example.dibujoamano;

import android.content.Context;
import android.graphics.*; // Canvas, Color, Paint, Path;
import android.view.*;     // MotionEvent, View;

class MiLienzo extends View {
    float x = 50, y = 50;
    String s = "";
    Path pa = new Path();

    public MiLienzo(Context c) {
        super(c);
    }

    @Override
    protected void onDraw(Canvas c) {
        c.drawColor(Color.rgb(200, 200, 200));
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(6);
        p.setColor(Color.RED);
        if (s == "abajo") pa.moveTo(x, y);
        if (s == "mover") pa.lineTo(x, y);
        c.drawPath(pa, p);

        // Encabezado con el nombre del alumno (visible en cada captura)
        Paint pn = new Paint();
        pn.setColor(Color.BLACK);
        pn.setTextSize(52);
        pn.setFakeBoldText(true);
        pn.setTextAlign(Paint.Align.CENTER);
        c.drawText("Angel Frausto Robles", getWidth() / 2f, 190, pn);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        x = me.getX();
        y = me.getY();
        if (me.getAction() == MotionEvent.ACTION_DOWN) s = "abajo";
        if (me.getAction() == MotionEvent.ACTION_MOVE) s = "mover";
        invalidate();
        return true;
    }
}
