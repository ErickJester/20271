package com.example.myapplication;

import android.os.*;
import android.app.Activity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    TextView itv;
    EditText jet;
    Button jbn;

    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        itv = (TextView) findViewById(R.id.xtv);
        jet = (EditText) findViewById(R.id.xet);
        jbn = (Button) findViewById(R.id.xbn);

        jbn.setOnClickListener(this);
    }

    public void onClick(View v) {
        itv.setText("Instituto Politecnico Nacional");
    }
}
