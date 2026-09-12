package com.example.kaprekar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

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
            xresultado.setText("Ingresa un numero de 3 o 4 digitos.");
            return;
        }

        int numero;
        try {
            numero = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            xresultado.setText("Ese valor no es un numero valido.");
            return;
        }

        int digitos = String.valueOf(numero).length();
        if (digitos != 3 && digitos != 4) {
            xresultado.setText("Numero analizado: " + numero
                    + "\n\nEsta rutina solo aplica a numeros de 3 o 4 digitos.");
            return;
        }

        int objetivo = (digitos == 3) ? 495 : 6174;

        // Rutina de Kaprekar: (digitos en orden descendente) - (digitos en orden ascendente).
        StringBuilder pasos = new StringBuilder();
        pasos.append(numero);
        int n = numero;
        int contador = 0;
        while (n != objetivo && n != 0 && contador < 20) {
            n = pasoKaprekar(n, digitos);
            pasos.append(" -> ").append(n);
            contador++;
        }

        String resultado;
        if (n == objetivo) {
            resultado = "Numero analizado: " + numero
                    + "\n\nSI llega a la constante de Kaprekar (" + objetivo + ")."
                    + "\n\nPasos:\n" + pasos
                    + "\n\nCantidad de pasos: " + contador;
        } else {
            resultado = "Numero analizado: " + numero
                    + "\n\nNO llega a la constante (digitos repetidos, la rutina llega a 0)."
                    + "\n\nPasos:\n" + pasos;
        }

        xresultado.setText(resultado);
    }

    private int pasoKaprekar(int numero, int digitos) {
        char[] cifras = String.format("%0" + digitos + "d", numero).toCharArray();

        char[] ascendente = cifras.clone();
        Arrays.sort(ascendente);

        char[] descendente = cifras.clone();
        Arrays.sort(descendente);
        invertir(descendente);

        int menor = Integer.parseInt(new String(ascendente));
        int mayor = Integer.parseInt(new String(descendente));
        return mayor - menor;
    }

    private void invertir(char[] arreglo) {
        int izquierda = 0;
        int derecha = arreglo.length - 1;
        while (izquierda < derecha) {
            char temporal = arreglo[izquierda];
            arreglo[izquierda] = arreglo[derecha];
            arreglo[derecha] = temporal;
            izquierda++;
            derecha--;
        }
    }
}
