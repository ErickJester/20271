# Clases — reconstrucción y seguimiento de sesiones (7CM3, Servicios en Red)

Esta carpeta guarda **un artifact por sesión de clase**: la clase real reconstruida desde su
grabación (pizarrón, diapositivas o laboratorio), no un resumen del programa. Sirve para estudiar
esa sesión concreta y para no perderle la pista a las tareas y prácticas que el profesor fue
dictando.

Cuando en una sesión de trabajo se traiga una grabación o transcripción de clase, **el entregable
es un artifact de clase siguiendo este archivo**. El contenido de estudio por unidad
(`../unidadN-*.html`) es otra cosa — ver la distinción más abajo. Esta carpeta y sus reglas son la
adaptación directa de la que ya funciona en Web Client
([`7CM2.../Primer parcial/clases/CLAUDE.md`](../../7CM2%20-%20DES.%20DE%20APLIC%28WEB%20CLIENT%20AND%20BACKEND/Primer%20parcial/clases/CLAUDE.md))
— mismo esqueleto, identidad visual propia, y un tipo de badge nuevo porque aquí sí hay prácticas
de laboratorio numeradas y oficiales.

---

## Seguimiento de clases

Las clases se guardan **por fecha**, no por número: el profesor no las numera y la transcripción
no siempre dice cuál sesión es. Una carpeta por sesión, nombrada como se dice la fecha en español
(`2 de septiembre`), con el HTML y la transcripción adentro.

| Fecha | Tema | Artifact publicado | Copia local | Tareas/Prácticas que salieron |
|---|---|---|---|---|
| 2 sep | Direccionamiento IPv4: clases A–E, máscaras, broadcast, IANA, atajo de bits | [`36c4d3dc`](https://claude.ai/code/artifact/36c4d3dc-1e56-4de0-958e-33e6d5527bb6) | [`2 de septiembre/`](2%20de%20septiembre/) | ninguna calificada; repasar apuntes subidos · un ejercicio proyectado quedó pendiente |

---

## De dónde sale el contenido: el pipeline

1. **La grabación llega como PDF autotranscrito** (si este profesor también graba y transcribe
   como el de Web Client) o como notas/transcripción limpia. El usuario la trae por sesión; **no
   vive en el repo**.
2. **Extraer con `pdftotext -layout -enc UTF-8`** (binario en `/mingw64/bin`, ver
   [[env-render-toolchain]]). Nunca leer las páginas como imagen.
3. **Decodificar la jerga si hace falta.** **Hasta ahora no ha hecho falta**: la transcripción del
   2 de septiembre llegó limpia y ya diferenciada por interlocutor (profesor / alumnos), sin la
   jerga mascada que sí sufre Web Client. Si en alguna sesión futura llega en crudo y mangled,
   construir la tabla de sustituciones igual que allá y guardarla en una memoria nueva
   `project-administracion-redes-course` (análoga a [[project-web-client-course]]).
4. **Si hay dos grabaciones de la misma clase, cruzarlas.**
5. **Marcar lo irrecuperable como tal.** No inventar lo que el profesor "seguramente" dijo.
6. **Reconstruir** en un HTML autocontenido con la estructura de abajo.

---

## Artifact de clase ≠ guía de unidad

| | Guía de unidad (`../unidadN-*.html`) | Artifact de clase (`<fecha>/*.html`) |
|---|---|---|
| Qué reconstruye | El temario oficial de una unidad | **Una sesión real**, minuto a minuto |
| Orden | La numeración del programa (1.1, 1.2.1…) | El orden en que pasó en el pizarrón/laboratorio |
| Voz | Explicativa, de manual | Fiel al profesor: **citas textuales** de lo que dijo |
| Recuadros | `.box` tipados: *Del programa oficial*, *Para el examen*, *Trampa común*, *En tu proyecto* | `.quote` (frase literal), `details.dio` (glosario "?"), `.task` (tarea o práctica, con minuto) |
| Alcance | Extensa, cubre toda la unidad | Cubre exactamente lo de esa clase, ni más ni menos |

Las dos comparten las reglas de `artifact-design`. No se mezclan.

---

## Estructura obligatoria de un artifact de clase

- **Acción primero.** Tareas, prácticas y pendientes se ven sin llegar hasta el fondo de la
  página.
- **Un `<section>` por bloque temático**, con eyebrow de minutos real: `Bloque N · minuto MM:SS a
  MM:SS`. Digresiones y preguntas de alumnos que se llevaron tiempo son secciones aparte
  (`Digresión · …`, `Pregunta de clase · …`).
- **Práctica intercalada.** Ejercicios interactivos entre bloques, no solo texto — ver el motor
  reusable más abajo. En una materia de redes esto rinde más que en cualquier otra: subneteo,
  tablas de ACL, banderas de comandos, orden de pasos en una configuración.
- **Cita textual del profesor** (`.quote`), literal, entre comillas.
- **Glosario in situ** (`details.dio` con botón `?`): un expando por cada término, protocolo o
  sigla que el profesor dio por sabido (SNMP, NAT, PAT, DWDM, QoS…).
- **Tareas y prácticas** (`.task`), con badge según el tipo — **este curso separa los dos, a
  diferencia de Web Client**:
  - `Tarea N` — investigación o lectura. Mismo formato de siempre si el profesor repite el patrón
    de las cuatro preguntas (*qué es, para qué sirve, con qué se come, cómo se adereza*).
  - `Práctica N` — laboratorio, y **cuando corresponda a una de las 13 numeradas en el programa
    oficial** (ver [`../CLAUDE.md`](../CLAUDE.md)), citar ese número exacto, no inventar uno
    nuevo. Si el profesor hace una práctica que no está en la lista oficial, decirlo explícito.
  - `Pendiente administrativo` — igual que en Web Client.
  - Todas con `.task-when` (minuto exacto) y la cita `said` del profesor. Si algo viene
    arrastrándose de una clase anterior, decirlo.
- **Cierre con checklist** de todas las tareas + prácticas + pendientes, con barra de progreso,
  guardado en `localStorage` bajo `claseN-tareas`. `try/catch` en cada lectura y escritura.
- **Si la clase fue de laboratorio/configuración**: sección final "de corrido" con la
  configuración completa y **corregida** (bloque de IOS, tabla de ACL, archivo de config —lo que
  aplique), más un `details.dio` que liste en qué se diferencia esa versión de lo que quedó
  literal en pantalla (cabos sueltos por tiempo, no correcciones a lo que dijo el profesor).
- **Cuando la transcripción venga incompleta** (el caso normal), el artifact **sí** se completa
  hasta ser una clase entera, pero lo añadido va marcado con el recuadro `.comp` de borde naranja
  punteado y el rótulo *"Completado — no salió en clase"*. La regla es doble y no se negocia: no
  se deja un hueco donde el alumno necesita el tema, y no se le hace creer que el profesor dijo
  algo que no dijo. El pie de página declara qué parte de la grabación se perdió. Estrenado en el
  artifact del 2 de septiembre — copiar de ahí el patrón (`.comp` en CSS, y el párrafo de cierre
  en `footer.page`).

---

## Motor de ejercicios: reusar, no reinventar

Las piezas genéricas ya existen y están escritas en JS plano, sin dependencias — se copian del
artifact de Web Client que las estrenó y se adaptan:
[`clase 4/clase4-crud-dao.html`](../../7CM2%20-%20DES.%20DE%20APLIC%28WEB%20CLIENT%20AND%20BACKEND/Primer%20parcial/clases/clase%204/clase4-crud-dao.html).

- **`quiz(rootId, items, goId, rsId, fbId, allGood)`** — opción múltiple con explicación por
  reactivo. Sirve tal cual para "¿qué comando usarías?", "¿ACL estándar o extendida?", "¿este
  puerto queda `up` o `down`?".
- **Caza-errores** (`.bug` clicables sobre un bloque de código) — ideal para una configuración de
  router con errores de sintaxis IOS a propósito.
- **Constructor de líneas en orden** (chips revueltos + zona de armado) — perfecto para el orden
  de comandos de una configuración (`enable` → `configure terminal` → `interface ...` → …), igual
  que se usó para armar el `cURL` en la clase 5 de Web Client.
- **Input validado con regex + hints por error** — para sintaxis exacta: una ACL, una dirección IP
  con máscara, un comando `show`. El patrón que más engancha porque atrapa el error específico
  (`EXEC` en vez de `CALL`, en el ejemplo de Web Client) y contesta con la corrección literal.

Vale la pena un ejercicio nuevo que Web Client no necesitó: **calculadora/validadora de
subneteo** (dada una IP y una máscara o un CIDR, pedir red, broadcast, rango de hosts, o al
revés). Cuando aparezca, dejarlo aquí documentado para que las clases futuras lo reusen igual que
el resto.

---

## Diseño del HTML

- **Identidad propia de esta materia, distinta de las otras dos que ya existen en el repo**
  (Web Client: IBM Plex Sans + Source Serif 4, acento azul; Liderazgo: Italiana + Crimson Pro +
  DM Mono, acento guinda IPN). Para esta serie:
  - **Títulos, eyebrows y etiquetas**: Martian Mono. Es una mono ancha y mecánica, con cara de
    etiqueta de equipo de red; se usa **sólo** en display y rótulos, nunca en párrafos.
  - **Cuerpo de texto**: Newsreader.
  - **Código, CLI, direcciones y tablas numéricas** (aquí hay mucho: IOS, `show`, ACLs, IPs):
    JetBrains Mono.
  - *(Corrección del 9 de septiembre: la primera versión de este archivo proponía Space Grotesk +
    Lora. Se cambió al construir el primer artifact real, porque Space Grotesk es una de las caras
    que `artifact-design` marca como cliché de diseño generado por IA. Martian Mono llega al mismo
    registro técnico sin ese problema.)*
  - **Acento**: el teal `#0d9488` que la agenda del semestre ya usa para "Servicios en Red" en
    `agenda/js/datos.js` — reusarlo aquí en vez de inventar uno da continuidad visual entre la
    agenda y el material de estudio, y ya está lo bastante lejos del azul de Web Client y del
    guinda de Liderazgo.
  - Tokens exactos (hover, soft, dark) se resuelven al construir, siguiendo `artifact-design`.
- **Identidad propia por clase cuando el contenido lo pida** (siguiendo el ejemplo de Web Client:
  marcador de pizarrón en la clase del CRUD, hexágonos de capa en la de arquitectura hexagonal).
  Aquí hay candidatos obvios: estado de puerto/interfaz (`up` verde, `down` rojo,
  `administratively down` gris), `permit`/`deny` de una ACL, capas OSI, adentro/afuera de un NAT.
  No forzar la misma paleta entre clases si el contenido no la pide.
- **Tres estados de tema**, obligatorio (ver `artifact-design`): paleta completa en `:root` a
  secas; redefinir solo en `@media (prefers-color-scheme: dark)` con guard
  `:root:not([data-theme="light"])`; y otra vez en `:root[data-theme="dark"]`. `body` con
  `background` explícito de token.
- **Autocontenido**: CSS y JS inline, sin `fetch` en runtime, fuentes desde Google Fonts. Sin
  `type="module"` si se quiere abrir con doble clic desde `file://`.
- Tablas, bloques de código/config y diagramas de topología anchos: cada uno en su
  `overflow-x:auto`; el body nunca scrollea en horizontal.

Antes de escribir el HTML, cargar la skill `artifact-design`.

---

## Publicar y versionar

- **Publicar como Artifact.** Título `Clase <fecha corta> · <tema corto>` (p. ej. `Clase 2 sep ·
  Direccionamiento IPv4`). Favicon estable por clase, asignado la primera vez que se construye esa
  clase (2 sep 🧮); en un redeploy se omite el favicon para no cambiarlo.
- **Actualizar un artifact existente**: pasar su URL de la tabla de arriba como `url`, o
  republicar el mismo `file_path`. Publicar sin `url` crea un artifact aparte.
- **Guardar copia local siempre** en `<fecha>/clase-<fecha corta>-<tema>.html`, junto con la
  `transcripcion.md` de esa sesión (que sí vive en el repo, a diferencia del PDF original). El
  repo manda.
- **Commit por clase**, desde la raíz del repo (`D:\Escuela\20271` — ahí se hace todo Git, ver
  [CLAUDE.md raíz](../../CLAUDE.md)). Mensaje al estilo del historial, imperativo, **sin
  trailers de autoría ni referencias a IA**.

---

## Las tareas y prácticas de una clase van también a la agenda del semestre

Cada tarea o práctica nueva que aparezca en una clase se registra **además** como bloque en
`../../agenda/js/tareas.js` (ver [[project-agenda-semestre]]): archivo aparte, con todos los
campos que ya usa (`id`, `curso: "7CM3"`, `titulo`, `tipo`, `entrega`, `fechaPorConfirmar`,
`parcial`, `estado`, `descripcion`, `entregable`, `pasos`, `origen`, `ruta`). No se borra de ahí
hasta que el usuario confirme *hecha* **y** *entregada*. Cuando la fecha es una suposición, usar
`fechaPorConfirmar: true` en vez de inventar certeza.

El checklist dentro del artifact y la agenda son dos cosas distintas: el artifact es para estudiar
esa clase; la agenda es el control global de entregas de las cinco materias.

---

## Memoria relacionada

- [[project-web-client-course]] — el patrón del que esta carpeta es adaptación: tabla de
  sustituciones, pipeline de decodificación, lista de artifacts construidos.
- [[project-agenda-semestre]] — la web de entregas y la regla de vida de las tareas.
- [[env-render-toolchain]] — `pdftotext` y el pipeline de render; no hay Node ni Python en la
  máquina.
- [[user-web-dev-background]] — por qué el material explica de más y no suelta respuestas sin
  contexto.
- `project-administracion-redes-course` — **todavía no existe**; crearla en cuanto llegue la
  primera grabación real de esta materia, con los hechos del curso y la tabla de jerga propia.
