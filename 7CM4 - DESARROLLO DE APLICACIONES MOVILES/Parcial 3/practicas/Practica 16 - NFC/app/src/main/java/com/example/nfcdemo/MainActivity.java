package com.example.nfcdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * NFC con Android: escritura de una URL en modo NDEF (Parte 1), lectura de la etiqueta
 * (Parte 2) y autoinicio de la app al detectar la etiqueta (Parte 3, vía intent-filter
 * NDEF_DISCOVERED en el manifiesto con singleTask).
 *
 * Adaptado de la guía (que usa el API antiguo showDialog/onCreateDialog/onPrepareDialog,
 * ya deprecado) a AlertDialog.Builder mostrado directamente.
 */
public class MainActivity extends Activity {
    private static final String TAG = "NfcDemo";

    private EditText myUrl;
    private TextView tvLog;
    private TextView tvEstadoNfc;
    private boolean mWriteUrl = false;
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        myUrl = findViewById(R.id.myUrl);
        tvLog = findViewById(R.id.tvLog);
        tvEstadoNfc = findViewById(R.id.tvEstadoNfc);
        Button myWriteUrlButton = findViewById(R.id.myWriteUrlButton);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            tvEstadoNfc.setText(R.string.sin_nfc);
            myWriteUrlButton.setEnabled(false);
        } else if (!mNfcAdapter.isEnabled()) {
            tvEstadoNfc.setText(R.string.sin_nfc);
        }

        myWriteUrlButton.setOnClickListener((View v) -> {
            mWriteUrl = true;
            new AlertDialog.Builder(this)
                    .setTitle(R.string.btn_escribir)
                    .setMessage(R.string.esperando_escritura)
                    .setCancelable(true)
                    .setNeutralButton(android.R.string.cancel, (d, which) -> {
                        mWriteUrl = false;
                        d.cancel();
                    })
                    .setOnCancelListener(d -> mWriteUrl = false)
                    .create()
                    .show();
        });

        // Si la app se abrió por el intent-filter NDEF_DISCOVERED (autoinicio), procesarlo:
        resolveIntent(getIntent(), false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter == null) return;
        PendingIntent pi = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_MUTABLE : 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mNfcAdapter.enableForegroundDispatch(this, pi,
                new IntentFilter[]{ndef, tech},
                new String[][]{{"android.nfc.tech.Ndef"}, {"android.nfc.tech.NdefFormatable"}});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent, true);
    }

    /** Si venimos del historial reciente, no reprocesar. */
    private boolean esRelanzamientoDesdeHistorial(Intent data) {
        return (data.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0;
    }

    private void resolveIntent(Intent data, boolean foregroundDispatch) {
        if (esRelanzamientoDesdeHistorial(data)) return;
        String action = data.getAction();
        if (!(NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))) {
            return;
        }
        Tag tag = data.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) return;

        if (foregroundDispatch && mWriteUrl) {
            mWriteUrl = false;
            escribirUrlEnEtiqueta(tag);
        } else {
            leerEtiqueta(tag, data);
        }
    }

    /** Parte 1: escribe la URL del EditText como registro NDEF URI en la etiqueta. */
    private void escribirUrlEnEtiqueta(Tag tag) {
        String urlStr = myUrl.getText().toString();
        NdefRecord urlRecord = NdefRecord.createUri(urlStr);
        NdefMessage msg = new NdefMessage(new NdefRecord[]{urlRecord});
        try {
            Ndef ndefTag = Ndef.get(tag);
            if (ndefTag != null) {
                ndefTag.connect();
                ndefTag.writeNdefMessage(msg);
                ndefTag.close();
            } else {
                NdefFormatable formateable = NdefFormatable.get(tag);
                if (formateable == null) throw new java.io.IOException("Etiqueta no compatible con NDEF");
                formateable.connect();
                formateable.format(msg);
                formateable.close();
            }
            tvLog.setText(getString(R.string.escritura_ok) + "\n" + urlStr);
        } catch (Exception e) {
            Log.e(TAG, "Error al escribir", e);
            tvLog.setText(getString(R.string.escritura_error, e.getMessage()));
        }
    }

    /** Parte 2: lee el UID y los registros NDEF tipo URI de la etiqueta y los muestra. */
    private void leerEtiqueta(Tag tag, Intent data) {
        StringBuilder info = new StringBuilder();
        info.append("UID: ").append(bytesToHex(tag.getId())).append("\n");

        Parcelable[] ndefRaw = data.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (ndefRaw != null) {
            for (Parcelable p : ndefRaw) {
                NdefMessage msg = (NdefMessage) p;
                for (NdefRecord record : msg.getRecords()) {
                    if (record.getTnf() == NdefRecord.TNF_WELL_KNOWN
                            && Arrays.equals(record.getType(), NdefRecord.RTD_URI)) {
                        String uri = record.toUri().toString();
                        info.append("URI: ").append(uri).append("\n");
                    }
                }
            }
        } else {
            info.append("(La etiqueta no trae un mensaje NDEF, o no se pudo leer).\n");
        }

        tvLog.setText(info.toString());
        new AlertDialog.Builder(this)
                .setTitle(R.string.etiqueta_detectada)
                .setMessage(info.toString())
                .setCancelable(true)
                .setNeutralButton(android.R.string.ok, (d, which) -> d.dismiss())
                .create()
                .show();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
