package com.example.mianalisisforense;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.Telephony;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Extracción lógica forense: metadatos del dispositivo, registro de llamadas (CallLog) y
 * mensajes SMS (Telephony.Sms), vía ContentResolver. Se calcula un hash SHA-256 del reporte
 * para preservar su integridad (cadena de custodia), y el reporte se exporta además como
 * archivo XML en el almacenamiento específico de la app (sin permisos adicionales).
 */
public class MainActivity extends AppCompatActivity {
    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLog = findViewById(R.id.tvLog);
        Button btnExtract = findViewById(R.id.btnExtract);
        btnExtract.setOnClickListener(v -> {
            if (checkPermissions()) {
                runForensicExtraction();
            } else {
                requestPermissions();
            }
        });
    }

    private void runForensicExtraction() {
        StringBuilder report = new StringBuilder();
        // 1. Metadatos del Hardware y Sistema
        report.append("=== INFORMACIÓN DEL DISPOSITIVO ===\n");
        report.append("Fabricante: ").append(Build.MANUFACTURER).append("\n");
        report.append("Modelo: ").append(Build.MODEL).append("\n");
        report.append("Versión Android: ").append(Build.VERSION.RELEASE).append("\n");
        report.append("Parche Seguridad: ").append(Build.VERSION.SECURITY_PATCH).append("\n\n");
        // 2. Extracción de Registro de Llamadas
        report.append("=== REGISTRO DE LLAMADAS ===\n");
        report.append(extractCallLogs()).append("\n");
        // 3. Extracción de Mensajes SMS
        report.append("=== MENSAJES SMS ===\n");
        report.append(extractSms()).append("\n");
        // 4. Cálculo de Hash SHA-256 para Integridad
        String finalData = report.toString();
        String sha256 = calculateSHA256(finalData);
        tvLog.setText(finalData + "\n=== INTEGRIDAD ===\nSHA-256: " + sha256);

        // Paso 6 de la guía: formatear la respuesta a un formato legible tabular (XML).
        String rutaXml = exportarXml(finalData, sha256);
        if (rutaXml != null) {
            tvLog.append("\nReporte XML: " + rutaXml);
        }
    }

    private String extractCallLogs() {
        StringBuilder calls = new StringBuilder();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DATE + " DESC");
        if (cursor != null) {
            int numberIdx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int typeIdx = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int dateIdx = cursor.getColumnIndex(CallLog.Calls.DATE);
            while (cursor.moveToNext()) {
                String number = cursor.getString(numberIdx);
                String type = cursor.getString(typeIdx);
                String date = cursor.getString(dateIdx);
                calls.append("Número: ").append(number)
                        .append(" | Tipo: ").append(type)
                        .append(" | Timestamp: ").append(date).append("\n");
            }
            cursor.close();
        }
        if (calls.length() == 0) calls.append("(Sin registros de llamadas)\n");
        return calls.toString();
    }

    private String extractSms() {
        StringBuilder smsList = new StringBuilder();
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            int addressIdx = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
            int bodyIdx = cursor.getColumnIndex(Telephony.Sms.BODY);
            while (cursor.moveToNext()) {
                String address = cursor.getString(addressIdx);
                String body = cursor.getString(bodyIdx);
                smsList.append("De/Para: ").append(address)
                        .append(" | Mensaje: ").append(body).append("\n");
            }
            cursor.close();
        }
        if (smsList.length() == 0) smsList.append("(Sin mensajes SMS)\n");
        return smsList.toString();
    }

    private String calculateSHA256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return "Error calculando hash";
        }
    }

    /** Escribe el reporte como XML simple en el almacenamiento específico de la app (sin
     *  permisos adicionales desde API 19+) y devuelve la ruta del archivo, o null si falló. */
    private String exportarXml(String reporteTexto, String sha256) {
        try {
            File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            if (dir == null) return null;
            String nombre = "reporte_" +
                    new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".xml";
            File archivo = new File(dir, nombre);
            StringBuilder xml = new StringBuilder();
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xml.append("<reporteForense>\n");
            xml.append("  <dispositivo>\n");
            xml.append("    <fabricante>").append(escaparXml(Build.MANUFACTURER)).append("</fabricante>\n");
            xml.append("    <modelo>").append(escaparXml(Build.MODEL)).append("</modelo>\n");
            xml.append("    <version>").append(escaparXml(Build.VERSION.RELEASE)).append("</version>\n");
            xml.append("  </dispositivo>\n");
            xml.append("  <contenido>").append(escaparXml(reporteTexto)).append("</contenido>\n");
            xml.append("  <sha256>").append(sha256).append("</sha256>\n");
            xml.append("</reporteForense>\n");
            try (FileOutputStream fos = new FileOutputStream(archivo)) {
                fos.write(xml.toString().getBytes(StandardCharsets.UTF_8));
            }
            return archivo.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    private String escaparXml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS
        }, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101 && checkPermissions()) {
            runForensicExtraction();
        }
    }
}
