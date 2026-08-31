# Agenda del semestre 2027-1

Calendario y control de entregas de las cinco materias. Es HTML, CSS y JavaScript
plano: **se abre con doble clic en `index.html`**, no necesita servidor, ni Node,
ni internet.

```
agenda/
  index.html          la página
  css/agenda.css      todo el diseño
  js/datos.js         materias, colores, unidades, parciales, horario
  js/tareas.js        ← EL REGISTRO DE ENTREGAS (el archivo aparte)
  js/estado.js        lo que guarda el navegador (tu avance)
  js/menu.js          el menú de tres niveles
  js/calendario.js    dibuja mes, semana, listas, materias y archivo
  js/app.js           filtros, panel de detalle, alta y exportación
```

---

## La regla de las tareas

**Toda tarea nueva se escribe en `js/tareas.js`.** Ese archivo es la fuente de
verdad y no se mezcla con el código de la app.

**Una tarea solo se borra de ahí cuando queda confirmada.** Confirmada significa
que en la web palomeaste las dos casillas:

- ☑ Ya la hice
- ☑ Ya la entregué

Marcarla como *hecha* no la borra. Marcarla como *entregada* tampoco. Nada más la
confirmación de las dos cosas la manda a **Archivo**, y hasta entonces se puede
quitar del archivo.

Cómo se quita, en la práctica:

1. Abre la entrega en la web y ponla en **Confirmar entrega** (las dos casillas).
2. Ve a la vista **Archivo**.
3. Botón **«Descargar tareas.js sin las archivadas»**.
4. Reemplaza `agenda/js/tareas.js` con el archivo que se descargó.

Ese archivo generado conserva el estado en que iba cada tarea viva, así que no
pierdes avance al reemplazarlo.

---

## Los tres niveles del menú

El botón de las tres rayas, arriba a la izquierda:

| Gesto | Nivel | Qué hace |
|---|---|---|
| — | **cerrado** | Riel angosto, puros iconos. |
| 1 clic | **flotante** | El panel se abre *encima* de la página. La página no se mueve. Un clic en cualquier otro lado lo cierra. |
| 2 clics seguidos | **fijo** | El panel se queda y **empuja toda la página a un lado**. Un clic afuera ya no lo cierra. |

Otro doble clic (o un clic en el botón) lo regresa a cerrado. `Esc` también cierra,
y la tecla `M` abre y cierra. El nivel **fijo** se recuerda entre sesiones; el
flotante no, porque abrir la página con un panel encima sería una sorpresa.

---

## Vistas

- **Calendario** — el mes completo, con las entregas dentro de cada día. Clic en un
  día para ver qué cae ahí; clic en una entrega para abrirla.
- **Semana** — los siete días con todo lo que cae en cada uno.
- **Entregas** — la lista, agrupada en vencidas / hoy / próximos 7 días / más
  adelante / sin fecha / terminadas sin confirmar.
- **Materias** — una tarjeta por curso, con sus entregas, las unidades del programa
  y las reglas del profesor.
- **Archivo** — lo confirmado y los botones de exportación.

---

## Agregar una entrega

Dos caminos:

**Desde la web** (botón `+ Entrega`). Se guarda en el navegador. Para que quede en
el repo, exporta `tareas.js` desde Archivo y reemplaza el archivo.

**A mano en `js/tareas.js`**, que es lo más directo. Copias un bloque, le cambias el
`id` (tiene que ser único) y lo pegas dentro del arreglo:

```js
{
  id: "asr-practica-1",
  curso: "7CM3",
  titulo: "Práctica 1 — Configuración de DHCP",
  tipo: "practica",
  entrega: "2026-09-14T23:00",
  fechaPorConfirmar: false,
  parcial: 1,
  estado: "pendiente",
  descripcion: "Qué hay que hacer, en tus palabras.",
  entregable: "Reporte PDF + captura de la configuración",
  pasos: ["Montar la topología", "Configurar", "Probar", "Redactar"],
  origen: "Clase del 8 de septiembre",
  ruta: "7CM3 - ADMINISTRACIÓN DE SERVICIOS EN RED"
}
```

`entrega: null` si todavía no tiene fecha. `fechaPorConfirmar: true` cuando la
fecha es una suposición: la web la marca en amarillo para que la verifiques.

---

## Cosas que puedes ajustar en `js/datos.js`

- **`PERIODOS`** — las fechas de los tres parciales, cuando las tengas.
- **`HORARIO`** — tu horario de clases. Si lo llenas, cada día del calendario
  muestra un puntito del color de la materia que toca:
  ```js
  const HORARIO = [
    { curso: "7CM2", dia: 1, inicio: "07:00", fin: "08:30" }  // 1 = lunes
  ];
  ```
- **Colores y nombres cortos** de cada materia.

---

## Dónde vive tu avance

En `localStorage` del navegador, bajo la llave `agenda-20271-v1`: estados, pasos
palomeados, notas, tareas creadas desde la web y el nivel del menú. Vive **en esta
computadora y en este navegador**. Si vas a reinstalar o cambiar de máquina, baja
el **respaldo JSON** desde Archivo.

## Fechas que faltan por confirmar

Las cuatro tareas de la clase 3 de Web Client (los dos reportes de lectura y las
dos investigaciones de DTO y DAO) están puestas el **lunes 31 de agosto de 2026 a
las 23:00** y marcadas como *fecha por confirmar*. El profesor dijo «para el lunes»;
la hora es la regla general del curso. Verifícalo y quita la marca en `tareas.js`.
