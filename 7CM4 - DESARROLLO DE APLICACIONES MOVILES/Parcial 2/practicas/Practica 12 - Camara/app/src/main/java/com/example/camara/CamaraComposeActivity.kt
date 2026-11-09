package com.example.camara

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Ejercicio: el Ejemplo 2 (vista previa / imagen completa / galería) con Jetpack Compose. */
class CamaraComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { PantallaCamara() } }
    }
}

@Composable
fun PantallaCamara() {
    val contexto = LocalContext.current
    val resolver = contexto.contentResolver
    val opciones = listOf(
        stringResource(R.string.op_preview),
        stringResource(R.string.op_completa),
        stringResource(R.string.op_galeria)
    )
    var opcion by remember { mutableIntStateOf(0) }
    var imagen by remember { mutableStateOf<Bitmap?>(null) }
    var mensaje by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf<Uri?>(null) }

    fun leer(uri: Uri): Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resolver, uri)) { d, info, _ ->
        d.setTargetSampleSize(maxOf(1, maxOf(info.size.width, info.size.height) / 1024))
    }

    // Vista previa: la cámara devuelve una miniatura (Bitmap).
    val vistaPrevia = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bmp ->
        if (bmp != null) { imagen = bmp; mensaje = "Vista previa" }
        else mensaje = "La cámara no devolvió vista previa"
    }
    // Imagen completa: la cámara escribe en el Uri indicado (Pictures/misfotos).
    val completa = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { guardada ->
        val uri = foto
        if (guardada && uri != null) { imagen = leer(uri); mensaje = "Imagen completa: $uri" }
        else { uri?.let { resolver.delete(it, null, null) }; mensaje = "No se tomó la foto" }
    }
    // Galería: el selector del sistema devuelve un Uri.
    val galeria = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) { imagen = leer(uri); mensaje = "Foto de la galería" }
    }

    Column(Modifier.fillMaxSize().systemBarsPadding().padding(16.dp)) {
        Text(stringResource(R.string.nombre_alumno), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(Modifier.weight(1f)) {
                opciones.forEachIndexed { i, texto ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = opcion == i, onClick = { opcion = i })
                        Text(texto)
                    }
                }
            }
            Button(onClick = {
                when (opcion) {
                    0 -> vistaPrevia.launch(null)
                    1 -> { foto = Fotos.nueva(contexto); completa.launch(foto!!) }
                    else -> galeria.launch("image/*")
                }
            }) { Text(stringResource(R.string.toma_foto), maxLines = 1) }
        }
        Text(mensaje, Modifier.padding(vertical = 8.dp))
        imagen?.let { Image(it.asImageBitmap(), contentDescription = "Foto", Modifier.fillMaxWidth()) }
    }
}
