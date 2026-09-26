package com.example.radiocheckbox;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.Locale;

// PARTE 1: seleccion con RadioButton y RadioGroup (calculadora basica).
// Ejercicio 1: se agregan cociente, potencia, raiz, seno, coseno y tangente.
public class MainActivity extends AppCompatActivity {
    private TextView tv1;
    private EditText et1, et2;
    private RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tv1 = findViewById(R.id.xtv1);
        r1 = findViewById(R.id.r1);
        r2 = findViewById(R.id.r2);
        r3 = findViewById(R.id.r3);
        r4 = findViewById(R.id.r4);
        r5 = findViewById(R.id.r5);
        r6 = findViewById(R.id.r6);
        r7 = findViewById(R.id.r7);
        r8 = findViewById(R.id.r8);
        r9 = findViewById(R.id.r9);
    }

    // Quita el ".0" cuando el resultado es entero; si no, deja 4 decimales.
    private String fmt(double v) {
        if (v == Math.rint(v) && Math.abs(v) < 1e15) {
            return String.valueOf((long) v);
        }
        return String.format(Locale.US, "%.4f", v);
    }

    public void calcular(View v) {
        String a = et1.getText().toString();
        String b = et2.getText().toString();
        String t = "Solución";
        if (a.isEmpty() || (b.isEmpty() && !(r6.isChecked() || r7.isChecked() || r8.isChecked() || r9.isChecked()))) {
            tv1.setText("Ingresa los valores necesarios");
            return;
        }
        int x = Integer.parseInt(a);
        int y = b.isEmpty() ? 0 : Integer.parseInt(b);
        if (r1.isChecked() == true) {
            int s = x + y;
            String r = String.valueOf(s);
            tv1.setText(t + ": " + x + " + " + y + " = " + r);
        } else
        if (r2.isChecked() == true) {
            int d = x - y;
            String r = String.valueOf(d);
            tv1.setText(t + ": " + x + " - " + y + " = " + r);
        } else
        if (r3.isChecked() == true) {
            int p = x * y;
            String r = String.valueOf(p);
            tv1.setText(t + ": " + x + " * " + y + " = " + r);
        } else
        // ----- Ejercicio 1: operaciones agregadas -----
        if (r4.isChecked() == true) {
            if (y == 0) {
                tv1.setText(t + ": no se puede dividir entre cero");
            } else {
                tv1.setText(t + ": " + x + " / " + y + " = " + fmt((double) x / y));
            }
        } else
        if (r5.isChecked() == true) {
            tv1.setText(t + ": " + x + " ^ " + y + " = " + fmt(Math.pow(x, y)));
        } else
        if (r6.isChecked() == true) {
            if (x < 0) {
                tv1.setText(t + ": no hay raíz real de un número negativo");
            } else {
                tv1.setText(t + ": √" + x + " = " + fmt(Math.sqrt(x)));
            }
        } else
        if (r7.isChecked() == true) {
            tv1.setText(t + ": sen(" + x + "°) = " + fmt(Math.sin(Math.toRadians(x))));
        } else
        if (r8.isChecked() == true) {
            tv1.setText(t + ": cos(" + x + "°) = " + fmt(Math.cos(Math.toRadians(x))));
        } else
        if (r9.isChecked() == true) {
            if (x % 180 == 90) {
                tv1.setText(t + ": tan(" + x + "°) no está definida");
            } else {
                tv1.setText(t + ": tan(" + x + "°) = " + fmt(Math.tan(Math.toRadians(x))));
            }
        } else {
            tv1.setText("Selecciona una operación");
        }
    }
}
