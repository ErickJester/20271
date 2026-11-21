package com.example.webview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.*;
import android.widget.*;

/**
 * Ejercicio 2: igual que el ejemplo, pero cada botón (Back/Home/Forward/Go) dispara su acción
 * en un hilo propio con un tiempo de espera previo, para evitar congestionar la carga si el
 * usuario pulsa varios botones seguidos. Mientras un hilo espera, los cuatro botones se
 * deshabilitan; la acción real sobre el WebView siempre se ejecuta de vuelta en el hilo de UI
 * (WebView no es thread-safe).
 */
public class Ejercicio2Activity extends Activity implements OnClickListener {

    private static final long ESPERA_MS = 800; // tiempo de espera simulado antes de ejecutar

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

    private void setBotonesHabilitados(boolean habilitados) {
        xbn1.setEnabled(habilitados);
        xbn2.setEnabled(habilitados);
        xbn3.setEnabled(habilitados);
        xbn4.setEnabled(habilitados);
    }

    /** Ejecuta la acción del botón en un hilo propio, con espera previa, y la aplica en la UI. */
    private void ejecutarConHilo(Runnable accionSobreWebView) {
        setBotonesHabilitados(false);
        final long inicio = System.currentTimeMillis();
        new Thread(() -> {
            try {
                Thread.sleep(ESPERA_MS);
            } catch (InterruptedException ignored) {
            }
            final long transcurrido = System.currentTimeMillis() - inicio;
            runOnUiThread(() -> {
                accionSobreWebView.run();
                setBotonesHabilitados(true);
                Toast.makeText(this, "Hilo ejecutado tras " + transcurrido + " ms", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.xbn1) {
            ejecutarConHilo(() -> wv.goBack());
        } else if (id == R.id.xbn2) {
            ejecutarConHilo(() -> wv.loadUrl(s));
        } else if (id == R.id.xbn3) {
            ejecutarConHilo(() -> wv.goForward());
        } else if (id == R.id.xbn4) {
            final String destino = xet.getText() + "";
            ejecutarConHilo(() -> wv.loadUrl(destino));
        }
    }
}
