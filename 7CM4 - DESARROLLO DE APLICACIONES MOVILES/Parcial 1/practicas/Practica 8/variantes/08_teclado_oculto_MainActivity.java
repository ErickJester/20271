package com.example.edittext;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        final EditText jet1 = (EditText) findViewById(R.id.xet1);
        Button jbn1 = (Button) findViewById(R.id.xbn1);
        jbn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(jet1.getWindowToken(), 0);
            }
        });
    }
}
