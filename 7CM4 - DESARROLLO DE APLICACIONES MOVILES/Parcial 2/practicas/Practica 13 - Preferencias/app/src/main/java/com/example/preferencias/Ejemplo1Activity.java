package com.example.preferencias;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

/** Ejemplo 1: nombre, eje X y eje Y persistentes en el archivo preferencias.xml. */
public class Ejemplo1Activity extends Activity {
    SharedPreferences sp;
    EditText jetn, jetx, jety;
    String s;
    float x, y;

    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_preferencias);
        jetn = (EditText) findViewById(R.id.xetn);
        jetx = (EditText) findViewById(R.id.xetx);
        jety = (EditText) findViewById(R.id.xety);
        sp = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        s = sp.getString("titulo", "ESCOM");
        x = sp.getFloat("x", 0);
        y = sp.getFloat("y", 0);
        jetn.setText(s);
        jetx.setText("" + x);
        jety.setText("" + y);
    }

    protected void onPause() {
        super.onPause();
        try {
            s = jetn.getText().toString();
            x = Float.parseFloat(jetx.getText().toString());
            y = Float.parseFloat(jety.getText().toString());
        } catch (NumberFormatException e) {
            // Un eje vacío o inválido no debe cerrar la app: se conservan los valores anteriores.
            Toast.makeText(this, "Eje X o Y inválido: no se guardó", Toast.LENGTH_LONG).show();
            return;
        }
        SharedPreferences.Editor miEditor = sp.edit();
        miEditor.putString("titulo", s);
        miEditor.putFloat("x", x);
        miEditor.putFloat("y", y);
        miEditor.commit();
        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_LONG).show();
    }
}
