package com.example.intentosjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.xbtnEcuacion)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnDatos)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xbtnEcuacion) {
            startActivity(new Intent(this, EcuacionActivity.class));
        } else if (id == R.id.xbtnDatos) {
            startActivity(new Intent(this, DatosActivity.class));
        }
    }
}
