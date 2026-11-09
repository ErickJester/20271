package com.example.preferencias;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/** Lee las coordenadas guardadas en SharedPreferences y las muestra en un TextView. */
public class MostrarCoordenadasActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_mostrar_coordenadas);
        SharedPreferences sp = getSharedPreferences("coordenadas", Context.MODE_PRIVATE);
        TextView tv = findViewById(R.id.tvDatos);
        if (!sp.contains("latitud")) {
            tv.setText("No hay coordenadas guardadas.");
            return;
        }
        tv.setText(sp.getString("ciudad", "")
                + "\nLatitud: " + sp.getFloat("latitud", 0)
                + "\nLongitud: " + sp.getFloat("longitud", 0));
    }
}
