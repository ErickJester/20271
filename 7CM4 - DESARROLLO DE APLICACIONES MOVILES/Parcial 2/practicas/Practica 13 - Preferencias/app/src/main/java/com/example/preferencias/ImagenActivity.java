package com.example.preferencias;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/** Ejercicio 2: guarda en el almacén interno de la app una imagen elegida de la galería. */
public class ImagenActivity extends Activity {
    static final String ARCHIVO = "imagen_guardada.png";
    static final int PEDIR_IMAGEN = 1;
    TextView tvEstado;
    ImageView ivVista;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_imagen);
        tvEstado = findViewById(R.id.tvEstado);
        ivVista = findViewById(R.id.ivVista);

        findViewById(R.id.bElegir).setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/*");
            startActivityForResult(i, PEDIR_IMAGEN);
        });
        // El Canvas se muestra en otra Activity, a la que se llega con un Intent.
        findViewById(R.id.bMostrar).setOnClickListener(v -> {
            if (!getFileStreamPath(ARCHIVO).exists()) {
                Toast.makeText(this, "Primero elige una imagen", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, MostrarImagenActivity.class));
        });
        actualizarEstado();
    }

    @Override
    protected void onActivityResult(int codigo, int resultado, Intent datos) {
        super.onActivityResult(codigo, resultado, datos);
        if (codigo != PEDIR_IMAGEN || resultado != RESULT_OK || datos == null) return;
        Uri uri = datos.getData();
        try {
            Bitmap bmp = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri),
                    (decoder, info, src) -> {
                        int lado = Math.max(info.getSize().getWidth(), info.getSize().getHeight());
                        decoder.setTargetSampleSize(Math.max(1, lado / 1600));
                    });
            try (FileOutputStream fos = openFileOutput(ARCHIVO, MODE_PRIVATE)) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            }
            Toast.makeText(this, "Imagen guardada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
        }
        actualizarEstado();
    }

    private void actualizarEstado() {
        File f = getFileStreamPath(ARCHIVO);
        if (f.exists()) {
            tvEstado.setText("Imagen guardada en el almacén interno:\n" + f.getAbsolutePath());
            ivVista.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
        }
    }
}
