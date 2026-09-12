package com.example.maravilloso;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText xnumero;
    private TextView xresultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xnumero = (EditText) findViewById(R.id.xnumero);
        xresultado = (TextView) findViewById(R.id.xresultado);
        Button xboton = (Button) findViewById(R.id.xboton);
        xboton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String texto = xnumero.getText().toString().trim();

        if (texto.isEmpty()) {
            xresultado.setText("Ingresa un numero natural.");
            return;
        }

        long numero;
        try {
            numero = Long.parseLong(texto);
        } catch (NumberFormatException e) {
            xresultado.setText("Ese valor no es un numero valido.");
            return;
        }

        if (numero < 1) {
            xresultado.setText("El numero debe ser mayor o igual a 1.");
            return;
        }

        // Conjetura de Collatz: par -> n/2, impar -> 3n+1, hasta llegar a 1.
        StringBuilder secuencia = new StringBuilder();
        secuencia.append(numero);
        long n = numero;
        int pasos = 0;
        while (n != 1 && pasos < 1000) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            secuencia.append(" -> ").append(n);
            pasos++;
        }

        boolean esMaravilloso = (n == 1);

        String resultado = "Numero analizado: " + numero + "\n\n"
                + (esMaravilloso
                    ? numero + " SI es un numero maravilloso."
                    : numero + " NO llego a 1 en 1000 pasos.")
                + "\n\nSecuencia:\n" + secuencia.toString()
                + "\n\nPasos: " + pasos;

        xresultado.setText(resultado);
    }
}
