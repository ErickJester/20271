package com.example.camara;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

/** Ejemplo 2: vista previa, imagen completa o foto elegida de la galería. */
public class Ejemplo2Activity extends Activity {
    Button jbn;
    RadioButton jrb0, jrb1, jrb2;
    Intent i;
    int TAKE_PREVIEW = 0;
    int TAKE_PICTURE = 1;
    int SELECT_PICTURE = 2;
    Uri foto;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_ejemplo2);
        jbn = (Button) findViewById(R.id.xbn1);
        jrb0 = (RadioButton) findViewById(R.id.xrb0);
        jrb1 = (RadioButton) findViewById(R.id.xrb1);
        jrb2 = (RadioButton) findViewById(R.id.xrb2);
        jbn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                int code = TAKE_PREVIEW;
                if (jrb1.isChecked()) {
                    foto = Fotos.nueva(Ejemplo2Activity.this);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, foto);
                    code = TAKE_PICTURE;
                } else if (jrb2.isChecked()) {
                    // ACTION_PICK con INTERNAL_CONTENT_URI (guía) ya no muestra la galería.
                    i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.setType("image/*");
                    code = SELECT_PICTURE;
                }
                startActivityForResult(i, code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            if (requestCode == TAKE_PICTURE) Fotos.descartar(this, foto);
            return;
        }
        ImageView iv = (ImageView) findViewById(R.id.xiv1);
        try {
            if (requestCode == TAKE_PREVIEW) {
                if (data != null && data.hasExtra("data")) {
                    iv.setImageBitmap((Bitmap) data.getParcelableExtra("data"));
                } else {
                    Toast.makeText(this, "La cámara no devolvió vista previa", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == TAKE_PICTURE) {
                iv.setImageBitmap(Fotos.leer(getContentResolver(), foto));
            } else if (requestCode == SELECT_PICTURE && data != null) {
                iv.setImageBitmap(Fotos.leer(getContentResolver(), data.getData()));
            }
        } catch (IOException e) {
            Toast.makeText(this, "No se pudo abrir la imagen", Toast.LENGTH_SHORT).show();
        }
    }
}
