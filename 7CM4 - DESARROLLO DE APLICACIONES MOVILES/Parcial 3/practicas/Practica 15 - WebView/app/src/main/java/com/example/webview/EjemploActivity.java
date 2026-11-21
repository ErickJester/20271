package com.example.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.*;
import android.widget.*;

/**
 * Ejemplo base de la guía: navegador propio con WebView en Java.
 * Botones: Back (xbn1), Home (xbn2), Forward (xbn3) y Go (xbn4).
 */
public class EjemploActivity extends Activity implements OnClickListener {

    Button xbn1, xbn2, xbn3, xbn4;
    WebSettings ws;
    WebView wv;
    EditText xet;
    String s = "https://www.google.com/";

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_navegador);
        xbn1 = (Button) findViewById(R.id.xbn1);
        xbn1.setOnClickListener(this);
        xbn2 = (Button) findViewById(R.id.xbn2);
        xbn2.setOnClickListener(this);
        xbn3 = (Button) findViewById(R.id.xbn3);
        xbn3.setOnClickListener(this);
        xbn4 = (Button) findViewById(R.id.xbn4);
        xbn4.setOnClickListener(this);
        xet = (EditText) findViewById(R.id.xet);
        wv = (WebView) findViewById(R.id.xwv);
        wv.setWebViewClient(new Cliente());
        ws = wv.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setJavaScriptEnabled(true);
        ws.setUseWideViewPort(true);
    }

    class Cliente extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            xet.setText(url);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xbn1) {
            wv.goBack();
        } else if (id == R.id.xbn2) {
            wv.loadUrl(s);
        } else if (id == R.id.xbn3) {
            wv.goForward();
        } else if (id == R.id.xbn4) {
            wv.loadUrl(xet.getText() + "");
        }
    }
}
