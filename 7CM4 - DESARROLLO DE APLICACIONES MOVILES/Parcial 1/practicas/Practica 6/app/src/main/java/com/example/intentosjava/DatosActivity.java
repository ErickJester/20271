package com.example.intentosjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DatosActivity extends Activity {

    private EditText editNombre, editApellido, editCorreo, editTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editCorreo = (EditText) findViewById(R.id.editCorreo);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        Button buttonEnviar = (Button) findViewById(R.id.buttonEnviar);

        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DatosActivity.this, MostrarDatosActivity.class);
                intent.putExtra("nombre", editNombre.getText().toString());
                intent.putExtra("apellido", editApellido.getText().toString());
                intent.putExtra("correo", editCorreo.getText().toString());
                intent.putExtra("telefono", editTelefono.getText().toString());
                startActivity(intent);
            }
        });
    }
}
