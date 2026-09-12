package com.example.plantillas;

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

        ((Button) findViewById(R.id.xbtnConstraint)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnLinear)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnFrame)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnRelative)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnCoordinator)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnTable)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnGrid)).setOnClickListener(this);
        ((Button) findViewById(R.id.xbtnAbsolute)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Class<?> destino = null;
        int id = v.getId();

        if (id == R.id.xbtnConstraint) {
            destino = ConstraintActivity.class;
        } else if (id == R.id.xbtnLinear) {
            destino = LinearActivity.class;
        } else if (id == R.id.xbtnFrame) {
            destino = FrameActivity.class;
        } else if (id == R.id.xbtnRelative) {
            destino = RelativeActivity.class;
        } else if (id == R.id.xbtnCoordinator) {
            destino = CoordinatorActivity.class;
        } else if (id == R.id.xbtnTable) {
            destino = TableActivity.class;
        } else if (id == R.id.xbtnGrid) {
            destino = GridActivity.class;
        } else if (id == R.id.xbtnAbsolute) {
            destino = AbsoluteActivity.class;
        }

        if (destino != null) {
            startActivity(new Intent(this, destino));
        }
    }
}
