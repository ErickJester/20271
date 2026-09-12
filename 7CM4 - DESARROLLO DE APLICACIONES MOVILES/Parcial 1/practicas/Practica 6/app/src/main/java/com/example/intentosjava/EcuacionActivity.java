package com.example.intentosjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EcuacionActivity extends Activity {

    private EditText editTextA, editTextB, editTextC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecuacion);

        editTextA = (EditText) findViewById(R.id.editTextA);
        editTextB = (EditText) findViewById(R.id.editTextB);
        editTextC = (EditText) findViewById(R.id.editTextC);
        Button buttonCalculate = (Button) findViewById(R.id.buttonCalculate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double a, b, c;
                try {
                    a = Double.parseDouble(editTextA.getText().toString());
                    b = Double.parseDouble(editTextB.getText().toString());
                    c = Double.parseDouble(editTextC.getText().toString());
                } catch (NumberFormatException e) {
                    editTextA.setError("Ingresa los tres coeficientes");
                    return;
                }

                if (a == 0) {
                    editTextA.setError("El coeficiente a no puede ser 0");
                    return;
                }

                Intent intent = new Intent(EcuacionActivity.this, ResultActivity.class);
                intent.putExtra("a", a);
                intent.putExtra("b", b);
                intent.putExtra("c", c);
                startActivity(intent);
            }
        });
    }
}
