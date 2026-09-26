package com.example.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        EditText jet1 = (EditText) findViewById(R.id.xet1);
        jet1.selectAll();
    }
}
