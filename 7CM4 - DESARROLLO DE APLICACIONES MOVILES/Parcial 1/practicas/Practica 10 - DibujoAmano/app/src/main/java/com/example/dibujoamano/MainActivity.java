package com.example.dibujoamano;

import android.os.*;
import android.app.*;

// EJEMPLO 1 (Java): dibujo a mano con Canvas y Paint.
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        MiLienzo ml = new MiLienzo(this);
        setContentView(ml);
    }
}
