package com.example.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
    }

    // Escucha asignada con android:onClick="verValor" en el Button.
    public void verValor(View v) {
        EditText jet1 = (EditText) findViewById(R.id.xet1);
        Toast.makeText(this, jet1.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
