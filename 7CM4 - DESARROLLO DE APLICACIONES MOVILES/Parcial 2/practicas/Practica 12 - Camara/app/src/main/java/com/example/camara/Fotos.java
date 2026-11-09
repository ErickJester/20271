package com.example.camara;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** Utilidades compartidas por los ejemplos: crear la foto en la galería y leer imágenes. */
final class Fotos {
    private Fotos() {}

    /**
     * Crea una entrada vacía en Pictures/misfotos (visible en la Galería) y devuelve su Uri.
     * La app de cámara escribe en ese Uri (EXTRA_OUTPUT). En API 29+ no pide permisos.
     */
    static Uri nueva(Context c) {
        ContentValues v = new ContentValues();
        v.put(MediaStore.Images.Media.DISPLAY_NAME, codigo() + ".jpg");
        v.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        v.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/misfotos");
        return c.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, v);
    }

    /** Borra la entrada creada con nueva() cuando el usuario cancela la cámara. */
    static void descartar(Context c, Uri uri) {
        if (uri != null) c.getContentResolver().delete(uri, null, null);
    }

    /** Decodifica el Uri reduciendo el tamaño (máx. ~1024 px) y respetando la rotación EXIF. */
    static Bitmap leer(ContentResolver cr, Uri uri) throws IOException {
        ImageDecoder.Source fuente = ImageDecoder.createSource(cr, uri);
        return ImageDecoder.decodeBitmap(fuente, (decoder, info, src) -> {
            int lado = Math.max(info.getSize().getWidth(), info.getSize().getHeight());
            decoder.setTargetSampleSize(Math.max(1, lado / 1024));
            decoder.setMutableRequired(false);
        });
    }

    /** Nombre pic_AAAAMMDDHHMMSS (en la guía el patrón usa mm = minutos en lugar de MM = mes). */
    static String codigo() {
        return "pic_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
    }
}
