package com.example.radiocheckbox;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// PARTE 2: seleccion multiple con CheckBox.
// Ejercicio 2: al confirmar, el Toast muestra todas las opciones seleccionadas.
public class CheckActivity extends AppCompatActivity {
    private CheckBox jchb, csoccer, camericano, cbeisbol, ctenis;
    private TextView tv;
    private Button bn;
    private String s = "";

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_check);
        jchb = (CheckBox) findViewById(R.id.xchotro);
        tv = findViewById(R.id.xetotro);
        bn = (Button) findViewById(R.id.xbn);
        csoccer = findViewById(R.id.xchbsoccer);
        camericano = findViewById(R.id.xchbamericano);
        cbeisbol = findViewById(R.id.xchbbeisbol);
        ctenis = findViewById(R.id.xchbtenis);
        jchb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tv.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void opcion(View v) {
        s = "";
        if (csoccer.isChecked()) s += csoccer.getText().toString() + ", ";
        if (camericano.isChecked()) s += camericano.getText().toString() + ", ";
        if (cbeisbol.isChecked()) s += cbeisbol.getText().toString() + ", ";
        if (ctenis.isChecked()) s += ctenis.getText().toString() + ", ";
        if (jchb.isChecked()) s += "Otro: " + tv.getText().toString() + ", ";
        if (s.isEmpty()) {
            s = "Ninguna opción seleccionada";
        } else {
            s = "Seleccionadas: " + s.substring(0, s.length() - 2);
        }
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
