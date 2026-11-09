package com.example.preferencias;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/** Ejercicio 1: pide latitud y longitud de la CDMX, las guarda y las muestra con un Intent. */
public class CoordenadasActivity extends Activity {
    EditText etLatitud, etLongitud;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_coordenadas);
        etLatitud = findViewById(R.id.etLatitud);
        etLongitud = findViewById(R.id.etLongitud);
        sp = getSharedPreferences("coordenadas", Context.MODE_PRIVATE);

        findViewById(R.id.bGuardar).setOnClickListener(v -> guardar());
        findViewById(R.id.bMostrar).setOnClickListener(v ->
                startActivity(new Intent(this, MostrarCoordenadasActivity.class)));
    }

    private void guardar() {
        try {
            float lat = Float.parseFloat(etLatitud.getText().toString());
            float lon = Float.parseFloat(etLongitud.getText().toString());
            SharedPreferences.Editor e = sp.edit();
            e.putString("ciudad", "Ciudad de México");
            e.putFloat("latitud", lat);
            e.putFloat("longitud", lon);
            e.commit();
            Toast.makeText(this, "Coordenadas guardadas", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException ex) {
            Toast.makeText(this, "Escribe latitud y longitud como números", Toast.LENGTH_SHORT).show();
        }
    }
}
