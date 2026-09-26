package com.example.edittext;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static String[] MI_LISTA = {"Android", "Arriba", "Africa", "Asia", "America", "A", "AMOR"};

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        AutoCompleteTextView jactv1 = (AutoCompleteTextView) findViewById(R.id.xactv1);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, MI_LISTA);
        jactv1.setAdapter(aa);
    }
}
