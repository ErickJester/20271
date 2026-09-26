package com.example.edittext;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        // Misma idea que el atributo android:focusable="false" del XML,
        // pero invocada desde Java.
        findViewById(R.id.campo_sin_foco).setFocusable(false);
    }
}
