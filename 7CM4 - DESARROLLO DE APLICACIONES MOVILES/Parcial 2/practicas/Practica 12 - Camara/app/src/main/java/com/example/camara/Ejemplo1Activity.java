package com.example.camara;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/** Ejemplo 1: toma una foto con el Intent de la cámara y la guarda en Pictures/misfotos. */
public class Ejemplo1Activity extends Activity {
    private Button jbn;
    private Uri foto;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_ejemplo1);
        jbn = (Button) findViewById(R.id.xbn);
        jbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foto = Fotos.nueva(Ejemplo1Activity.this);
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                in.putExtra(MediaStore.EXTRA_OUTPUT, foto);
                startActivityForResult(in, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView estado = (TextView) findViewById(R.id.tvEstado);
        if (resultCode == RESULT_OK && foto != null) {
            try {
                ((ImageView) findViewById(R.id.ivFoto)).setImageBitmap(
                        Fotos.leer(getContentResolver(), foto));
                estado.setText("Foto guardada en Pictures/misfotos:\n" + foto);
                Toast.makeText(this, "Foto guardada", Toast.LENGTH_SHORT).show();
            } catch (IOException ex) {
                Log.e("Error", "Error:" + ex);
                estado.setText("No se pudo leer la foto.");
            }
        } else {
            Fotos.descartar(this, foto);
            estado.setText("No se tomó la foto.");
        }
    }
}
