package com.example.suma;

import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class MainActivity extends Activity {
    int x, y, z;
    TextView jtv;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        x = 2;
        y = 2;
        z = x + y;
        jtv = (TextView) findViewById(R.id.xtv);
        jtv.setText(x + "+" + y + "=" + z);
    }
}
