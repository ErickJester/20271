package com.example.intentosjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView textViewResult = (TextView) findViewById(R.id.textViewResult);

        double a = getIntent().getDoubleExtra("a", 0);
        double b = getIntent().getDoubleExtra("b", 0);
        double c = getIntent().getDoubleExtra("c", 0);
        double discriminante = b * b - 4 * a * c;

        String resultado;
        if (discriminante > 0) {
            double x1 = (-b + Math.sqrt(discriminante)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminante)) / (2 * a);
            resultado = "a = " + a + ", b = " + b + ", c = " + c
                    + "\n\nDos raíces reales:\nx1 = " + x1 + "\nx2 = " + x2;
        } else if (discriminante == 0) {
            double x = -b / (2 * a);
            resultado = "a = " + a + ", b = " + b + ", c = " + c
                    + "\n\nRaíz doble:\nx = " + x;
        } else {
            // Ejercicio 1: raíces complejas con notación a + jb.
            double parteReal = -b / (2 * a);
            double parteImaginaria = Math.abs(Math.sqrt(-discriminante) / (2 * a));
            resultado = "a = " + a + ", b = " + b + ", c = " + c
                    + "\n\nRaíces complejas:\nx1 = " + parteReal + " + " + parteImaginaria + "j"
                    + "\nx2 = " + parteReal + " - " + parteImaginaria + "j";
        }

        textViewResult.setText(resultado);
    }
}
