package com.example.preferencias;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Dibuja en un Canvas la imagen guardada en el almacén interno. */
public class MostrarImagenActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout raiz = new LinearLayout(this);
        raiz.setOrientation(LinearLayout.VERTICAL);
        int margen = (int) (16 * getResources().getDisplayMetrics().density);
        raiz.setPadding(margen, margen, margen, margen);

        TextView nombre = new TextView(this);
        nombre.setText(R.string.nombre_alumno);
        nombre.setTextSize(18);
        nombre.setTypeface(null, Typeface.BOLD);
        raiz.addView(nombre);

        Bitmap bmp = BitmapFactory.decodeFile(
                getFileStreamPath(ImagenActivity.ARCHIVO).getAbsolutePath());
        raiz.addView(new Lienzo(this, bmp), new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f));
        // El FrameLayout recibe las barras del sistema; el LinearLayout conserva su margen de 16 dp.
        FrameLayout marco = new FrameLayout(this);
        marco.setFitsSystemWindows(true);
        marco.addView(raiz, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        setContentView(marco);
    }

    /** Vista que dibuja el bitmap centrado y escalado al espacio disponible. */
    static class Lienzo extends View {
        final Bitmap bmp;
        final Paint pincel = new Paint(Paint.FILTER_BITMAP_FLAG);

        Lienzo(Context c, Bitmap bmp) {
            super(c);
            this.bmp = bmp;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (bmp == null) return;
            float escala = Math.min((float) getWidth() / bmp.getWidth(),
                    (float) getHeight() / bmp.getHeight());
            float w = bmp.getWidth() * escala, h = bmp.getHeight() * escala;
            float x = (getWidth() - w) / 2, y = (getHeight() - h) / 2;
            canvas.drawBitmap(bmp, null, new RectF(x, y, x + w, y + h), pincel);
        }
    }
}
