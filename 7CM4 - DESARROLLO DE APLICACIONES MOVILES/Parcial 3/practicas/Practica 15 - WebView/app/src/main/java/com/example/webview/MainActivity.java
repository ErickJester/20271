package com.example.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/** Menú de la práctica: abre el ejemplo en Java, el ejercicio 1 en Compose y el ejercicio 2 (hilos). */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bEjemplo).setOnClickListener(v ->
                startActivity(new Intent(this, EjemploActivity.class)));
        findViewById(R.id.bEjercicio1).setOnClickListener(v ->
                startActivity(new Intent(this, WebViewComposeActivity.class)));
        findViewById(R.id.bEjercicio2).setOnClickListener(v ->
                startActivity(new Intent(this, Ejercicio2Activity.class)));
    }
}
