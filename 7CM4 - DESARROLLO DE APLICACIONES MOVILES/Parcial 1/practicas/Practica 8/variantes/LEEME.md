# Cómo usar esta carpeta

El proyecto **Practica 8** ya está armado y compila con el Ejercicio 1 (`EditText` básico). El PDF
`DAMN EditText.pdf` pide **26 ejercicios**, cada uno modificando solo `activity_main.xml` (y a veces
`MainActivity.java`) y tomando una captura del resultado.

En vez de tener 26 proyectos separados, aquí hay un archivo (o par de archivos) por ejercicio, listo
para copiar y pegar.

## Flujo por cada ejercicio

1. Abre el proyecto en Android Studio (`File > Open` sobre la carpeta `Practica 8`).
2. Copia el contenido de `NN_nombre_activity_main.xml` y pégalo reemplazando todo
   `app/src/main/res/layout/activity_main.xml`.
3. Si existe también `NN_nombre_MainActivity.java` para ese número, cópialo reemplazando
   `app/src/main/java/com/example/edittext/MainActivity.java`. Si no existe, deja el `MainActivity.java`
   actual tal cual (ese ejercicio es solo XML).
4. Corre la app (▶ Run) y toma la captura de pantalla.
5. Guarda la captura en `../screenshots/` con el nombre `NN_nombre.png` (mismo número que el ejercicio)
   para que coincida con `../reporte/reporte.html`.
6. Repite con el siguiente número.

## Lista de ejercicios

| # | Tema | Archivos |
|---|---|---|
| 1 | Un componente EditText | ya está en el proyecto base |
| 2 | Obtener el texto del EditText | xml + java |
| 3 | Tipos de entrada (`inputType="phone"`) | xml |
| 4 | Límite de caracteres (`maxLength`) | xml |
| 5 | Una sola línea (`singleLine`) | xml |
| 6 | La propiedad `ems` | xml |
| 7 | Dígitos del 0 al 9 (`digits`) | xml |
| 8 | Ocultar el teclado desde Java | xml + java |
| 9 | EditText no editable (`enabled="false"`) | xml |
| 10 | Manejo del foco (`focusable`) | xml + java |
| 11 | Asignación del foco a un EditText | xml + java |
| 12 | Foco hacia otros componentes (`nextFocusRight/Left`) | xml |
| 13 | Cambio de la posición del cursor (`setSelection`) | xml + java |
| 14 | Obtención de la posición del cursor | xml + java (reutiliza el XML del 13) |
| 15 | Selección dinámica del texto | xml + java |
| 16 | Selección completa (`selectAll`) | xml + java |
| 17 | `imeOptions`/`imeActionLabel` — documentar qué pasa en horizontal | xml |
| 18 | Manejo de eventos (`TextWatcher`) | xml + java |
| 19 | Eventos de botones del teclado (`OnEditorActionListener`) | xml + java |
| 20 | `OnFocusChangeListener` (tinte del ícono) | xml + java |
| 21 | Color del hint (`textColorHint`) | xml |
| 22 | Color del texto seleccionado (`textColorHighlight`) | xml |
| 23 | Centrado del texto (`gravity`) | xml |
| 24 | Color del borde (estilo `miEstilo`, ya en `themes.xml`) | xml |
| 25 | Tamaño de la fuente (`textSize`) | xml |
| 26 | `AutoCompleteTextView` | xml + java |

Los ejercicios 20 y 26 usan `androidx` (`ContextCompat`/`DrawableCompat` y `AppCompatActivity`) porque
así los pide el propio PDF del profesor — es la única excepción a la convención de SDK puro que
usamos en el resto de la materia.
