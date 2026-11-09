package com.example.camara;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/** Menú de la práctica: abre los dos ejemplos en Java y el ejercicio en Compose. */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bEjemplo1).setOnClickListener(v ->
                startActivity(new Intent(this, Ejemplo1Activity.class)));
        findViewById(R.id.bEjemplo2).setOnClickListener(v ->
                startActivity(new Intent(this, Ejemplo2Activity.class)));
        findViewById(R.id.bCompose).setOnClickListener(v ->
                startActivity(new Intent(this, CamaraComposeActivity.class)));
    }
}
