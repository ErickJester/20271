package com.example.webview

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

private const val PAGINA_INICIO = "https://www.google.com/"

/** Ejercicio 1: el navegador del ejemplo base, convertido a Jetpack Compose. */
class WebViewComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    Navegador(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun Navegador(modifier: Modifier = Modifier) {
    var direccion by rememberSaveable { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var navegador by remember { mutableStateOf<WebView?>(null) }
    var puedeRetroceder by remember { mutableStateOf(false) }
    var puedeAvanzar by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val mensajeUrlInvalida = stringResource(R.string.url_error)

    fun actualizarHistorial(view: WebView) {
        puedeRetroceder = view.canGoBack()
        puedeAvanzar = view.canGoForward()
    }

    fun abrirUrl() {
        val url = normalizarUrl(direccion)
        if (url == null) {
            error = mensajeUrlInvalida
            return
        }
        error = null
        direccion = url
        focusManager.clearFocus()
        navegador?.loadUrl(url)
    }

    BackHandler(enabled = puedeRetroceder) {
        navegador?.goBack()
    }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.opcion),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Button(
                onClick = { navegador?.goBack() },
                enabled = puedeRetroceder,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.back))
            }
            Button(
                onClick = {
                    error = null
                    direccion = PAGINA_INICIO
                    focusManager.clearFocus()
                    navegador?.loadUrl(PAGINA_INICIO)
                },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.home))
            }
            Button(
                onClick = { navegador?.goForward() },
                enabled = puedeAvanzar,
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.forward))
            }
            Button(
                onClick = { abrirUrl() },
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(stringResource(R.string.go))
            }
        }
        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
                error = null
            },
            label = { Text(stringResource(R.string.url)) },
            placeholder = { Text(stringResource(R.string.url_hint)) },
            singleLine = true,
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = { abrirUrl() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        error?.let { mensaje ->
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            factory = { context ->
                WebView(context).apply {
                    settings.apply {
                        builtInZoomControls = true
                        displayZoomControls = false
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        useWideViewPort = true
                        loadWithOverviewMode = true
                    }
                    webViewClient = object : WebViewClient() {
                        override fun doUpdateVisitedHistory(
                            view: WebView,
                            url: String?,
                            isReload: Boolean
                        ) {
                            super.doUpdateVisitedHistory(view, url, isReload)
                            actualizarHistorial(view)
                        }

                        override fun onPageFinished(view: WebView, url: String?) {
                            super.onPageFinished(view, url)
                            if (url != null) {
                                direccion = url
                            }
                            actualizarHistorial(view)
                        }
                    }
                    navegador = this
                    loadUrl(PAGINA_INICIO)
                }
            },
            onRelease = { view ->
                navegador = null
                view.stopLoading()
                view.destroy()
            }
        )
    }
}

private fun normalizarUrl(texto: String): String? {
    val entrada = texto.trim()
    if (entrada.isEmpty() || entrada.any { it.isWhitespace() }) {
        return null
    }
    val uriInicial = Uri.parse(entrada)
    val url = if (uriInicial.scheme == null) {
        "https://$entrada"
    } else {
        entrada
    }
    val uri = Uri.parse(url)
    val esquemaValido =
        uri.scheme.equals("https", ignoreCase = true) ||
            uri.scheme.equals("http", ignoreCase = true)
    return if (esquemaValido && !uri.host.isNullOrBlank()) {
        url
    } else {
        null
    }
}
