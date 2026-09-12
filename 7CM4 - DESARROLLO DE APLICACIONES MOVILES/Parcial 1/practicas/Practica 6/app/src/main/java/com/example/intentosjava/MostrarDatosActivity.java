package com.example.intentosjava;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MostrarDatosActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        TextView textViewDatos = (TextView) findViewById(R.id.textViewDatos);

        String nombre = getIntent().getStringExtra("nombre");
        String apellido = getIntent().getStringExtra("apellido");
        String correo = getIntent().getStringExtra("correo");
        String telefono = getIntent().getStringExtra("telefono");

        String datos = "Nombre: " + nombre
                + "\nApellido: " + apellido
                + "\nCorreo: " + correo
                + "\nTeléfono: " + telefono;

        textViewDatos.setText(datos);
    }
}
