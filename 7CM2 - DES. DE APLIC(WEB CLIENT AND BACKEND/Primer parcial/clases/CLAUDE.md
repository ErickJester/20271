# Clases — reconstrucción y seguimiento de sesiones (C704)

Esta carpeta guarda **un artifact por sesión de clase**: la clase real reconstruida desde su
grabación, no un resumen del programa. Sirve para estudiar esa sesión concreta y para no perderle
la pista a las tareas que el profesor fue dictando.

Cuando en una sesión de trabajo se traiga una grabación o transcripción de clase, **el entregable
es un artifact de clase siguiendo este archivo**. El contenido de estudio por unidad
(`../../unidadN-*.html`) es otra cosa — ver la distinción más abajo.

---

## Seguimiento de clases

| # | Tema | Artifact publicado | Copia local | Tareas que salieron |
|---|---|---|---|---|
| 1 | Presentación, stack del curso, jerga · reconstruida de 2 grabaciones corruptas | [`cce92340`](https://claude.ai/code/artifact/cce92340-3530-45fb-bb80-7afb992b7077) | **falta** | (encuadre; sin entregables propios) |
| 2 | Contenedores/Docker, arquitectura N-capas y 3-capas | [`f5f6f4a5`](https://claude.ai/code/artifact/f5f6f4a5-5f80-4df8-a8d0-6ef6a6dd0d75) | **falta** | 12 pendientes (checklist en el artifact) |
| 3 | Taller: clase `Carrera` desde UML, JavaBean, DTO, `Statement`/`PreparedStatement`/`CallableStatement`, CRUD en SQL, inicio de `CarreraDAO` | [`80cfa706`](https://claude.ai/code/artifact/80cfa706-6afe-4df4-96c8-669fe594f007) | **falta** (solo el código: [`clase 3/`](clase%203/)) | Lectura rápida Java · investigación DTO · investigación DAO · Lectura rápida JDBC · (repaso BD, no calificado) |
| 4 | `CarreraDAO` completo en el pizarrón: los 5 métodos del CRUD, `Principal`, stored procedure MySQL | [`c88de2f6`](https://claude.ai/code/artifact/c88de2f6-f18f-44af-ba67-d4347ecd9ffa) | [`clase 4/clase4-crud-dao.html`](clase%204/clase4-crud-dao.html) | soft delete · reporte "Lectura rápida JDBC" · GUI de escritorio · `PreparedStatement`→`CallableStatement` (**con fecha: próxima semana**) · subir datos al Excel del curso externo |
| 5 | Arquitectura hexagonal (puertos y adaptadores) y microservicios · **cierra la Unidad I** | [`bbce12dc`](https://claude.ai/code/artifact/bbce12dc-3214-4224-ac48-861fcf209233) | [`clase 5/clase5-hexagonal-microservicios.html`](clase%205/clase5-hexagonal-microservicios.html) | investigación patrón Builder · códigos de respuesta HTTP (las dos **sin fecha**) |
| 6 | Práctica pura: entregables del avance de proyecto (diccionario, E-R, script SQL, relacional, 3FN), `docker run` vs `Dockerfile`, publicar el minisitio en Apache (:8080) y Nginx (:8081), Docker Compose | [`d371b25f`](https://claude.ai/code/artifact/d371b25f-6040-4812-beba-ea3044fb968c) | [`clase 6/clase6-apache-nginx-docker.html`](clase%206/clase6-apache-nginx-docker.html) | práctica Apache :8080 + Nginx :8081 (sin fecha propia) · avance de proyecto (fecha tentativa 11 sept) · revisar ~15 actividades de plataforma (vencen 11 sept) |

**Pendiente de mantenimiento**: bajar la copia local de las clases 1–3 (`Artifact` con `action:"read"`
sobre cada URL, guardar como `clase N/claseN-tema.html`). La regla del curso es que el material vive
en el repo aunque el link no se comparta; hoy solo la clase 4 la cumple.

**Artifacts de apoyo** (no son clases, pero se citan desde ellas): Decodificador Web
([`b9611f34`](https://claude.ai/code/artifact/b9611f34-059b-43bf-9cab-e0249b1eecec)), Cimientos Web
([`f52978cd`](https://claude.ai/code/artifact/f52978cd-a79e-4f29-9024-b5876e9d610e)), y la guía de unidad I
"Del Monolito al Microservicio" ([`e5b53bff`](https://claude.ai/code/artifact/e5b53bff-9b87-4043-aa0d-809d38e8f851)).

---

## De dónde sale el contenido: el pipeline

1. **La grabación llega como PDF autotranscrito** que mastica los términos en inglés y los vuelve
   palabras en español. Ese PDF lo trae el usuario por sesión; **no vive en el repo**.
2. **Extraer con `pdftotext -layout -enc UTF-8`** (binario en `/mingw64/bin`). Nunca leer las
   páginas como imagen — sale mucho más caro y no hace falta.
3. **Decodificar la jerga** contra la tabla de sustituciones de [[project-web-client-course]].
   Las más frecuentes: `llave`/`llama`/`llamar` = Java, `sprint` = Spring, `dago` = DAO,
   `"d t 2"` = DTO, `Insomnio` = Insomnia, `Postres` = Postman, `vmail` = UML, `soly` = SOLID,
   `"coche tanto"`/`caballero` = Tomcat.
4. **Si hay dos grabaciones de la misma clase, cruzarlas.** En la clase 1 eso resolvió 4 términos
   dudosos que una sola no fijaba.
5. **Marcar lo irrecuperable como tal.** Si un pasaje quedó ilegible, se dice; no se inventa lo que
   el profesor "seguramente" dijo. Los porcentajes de calificación de la clase 1 se dejaron como
   "pregúntale al profesor" por esto mismo.
6. **Reconstruir** en un HTML autocontenido con la estructura de abajo.

---

## Artifact de clase ≠ guía de unidad

| | Guía de unidad (`unidadN-*.html`) | Artifact de clase (`clase N/*.html`) |
|---|---|---|
| Qué reconstruye | El temario oficial de una unidad | **Una sesión real**, minuto a minuto |
| Orden | La numeración del programa (1.1, 1.2.1…) | El orden en que pasó en el pizarrón |
| Voz | Explicativa, de manual | Fiel al profesor: **citas textuales** de lo que dijo |
| Recuadros | `.box` tipados: *Del programa oficial*, *Para el examen*, *Trampa común*, *En tu proyecto* | `.quote` (frase literal), `details.dio` (glosario "?"), `.task` (tarea con minuto) |
| Alcance | Extensa, cubre toda la unidad | Cubre exactamente lo de esa clase, ni más ni menos |

Las dos comparten el sistema tipográfico y las reglas de `artifact-design`. No se mezclan: una guía
de unidad no lleva minutos de clase, un artifact de clase no sigue la numeración del programa.

---

## Estructura obligatoria de un artifact de clase

- **Acción primero.** Las tareas y pendientes tienen que verse sin llegar hasta el fondo de la
  página. El usuario lo pidió explícito: los entregables se surfacean, no se esconden.
- **Un `<section>` por bloque temático** de la clase. Eyebrow con el rango de minutos real:
  `Bloque N · minuto MM:SS a MM:SS`. Las digresiones y las preguntas de alumnos que se llevaron
  tiempo también son secciones (`Digresión · …`, `Pregunta de clase · …`).
- **Práctica intercalada.** Ejercicios interactivos **entre** los bloques, no solo texto. El usuario
  lo pidió para las clases 2, 3 y 4 — es el estilo por defecto ya. Copiar el pizarrón sin ejercitar
  no sirve.
- **Cita textual del profesor** (`.quote`) en los momentos que definen un concepto. Entre comillas,
  literal. Ej.: *"para nosotros solo hay dos: `executeUpdate()` y san se acabó"*.
- **Glosario in situ** (`details.dio` con botón `?`): un expando por cada término que el profesor
  dio por sabido. No interrumpe la lectura; se abre quien lo necesite.
- **Tareas** (`.task`): badge (`Tarea N`, `Pendiente administrativo`), `.task-when` con el **minuto
  exacto** en que se dictó, y la cita `said` del profesor. Si una tarea venía arrastrándose de una
  clase anterior, decirlo.
- **Cierre con checklist** de **todas** las tareas + pendientes admin, con barra de progreso,
  guardado en `localStorage` bajo una llave propia (`claseN-tareas`). `try/catch` en cada lectura
  y escritura de `localStorage`.
- **Si la clase fue de código**: sección final "de corrido" con el archivo completo y **corregido**,
  más un `details.dio` que liste en qué se diferencia esa versión de lo que quedó literal en el
  pizarrón (cabos que el profesor dejó sueltos por tiempo, no correcciones a lo que dijo).

---

## Motor de ejercicios: reusar, no reinventar

`clase 4/clase4-crud-dao.html` ya trae piezas genéricas listas para copiar y adaptar:

- **`quiz(rootId, items, goId, rsId, fbId, allGood)`** — quiz de opción múltiple con explicación por
  reactivo. `items` = `[{q, opts, ans, why}]`. Valida que estén todas contestadas, marca cada fila
  en verde/rojo y muestra el `why` debajo.
- **Caza-errores** — spans `.bug` clicables sobre un bloque de código, `.clean` para las líneas
  correctas (avisan "esa está bien"). Cada bug tiene su explicación; al encontrar todos, remate.
- **Constructor de líneas en orden** — banco de chips revueltos + zona de armado. Valida al vuelo,
  da la pista del paso que toca, deja quitar líneas ya puestas.
- **Input validado con regex + hints por error** — el de `CALL sp_crear(...)` normaliza la entrada
  y responde distinto según el error (atrapa `EXEC` y contesta con la corrección literal del
  profesor). Este patrón es el que más engancha: úsalo para sintaxis que el profesor corrigió en vivo.

---

## Diseño del HTML

- **Base del sistema**: IBM Plex Sans + Source Serif 4 (tipografía pareada). Es la del Decodificador,
  Cimientos y las clases 1–3.
- **Identidad propia por clase cuando el contenido lo pida.** La clase 4 usa **paleta de marcadores
  de pizarrón** (azul `create`, verde `update`, rojo `delete`, negro `read`) porque el profesor
  literalmente escribió cada método en un color distinto — el color es el dispositivo estructural
  de la página (chip de swatch + riel izquierdo por método). No forzar la misma paleta entre clases.
- **Tres estados de tema**, obligatorio (ver `artifact-design`): paleta completa en `:root` a secas;
  redefinir solo en `@media (prefers-color-scheme: dark)` con guard `:root:not([data-theme="light"])`;
  y otra vez en `:root[data-theme="dark"]`. `body` con `background` explícito de token.
- **Autocontenido**: CSS y JS inline, sin `fetch` en runtime, fuentes desde Google Fonts. Sin
  `type="module"` si además se quiere poder abrir con doble clic desde `file://`.
- Tablas, bloques de código y diagramas anchos: cada uno en su `overflow-x:auto`; el body nunca
  scrollea en horizontal.

Antes de escribir el HTML, cargar la skill `artifact-design`.

---

## Publicar y versionar

- **Publicar como Artifact.** Título `Clase N · <tema corto>`. Favicon estable por clase
  (1 🔍 · 2 📦 · 3 🗃️ · 4 🖍️ · 5 🔌); en un redeploy se omite el favicon para no cambiarlo.
- **Actualizar un artifact existente**: pasar su URL de la tabla de arriba como `url`, o republicar
  el mismo `file_path`. Publicar sin `url` crea un artifact aparte — recuperar la URL, no anunciar
  un link nuevo.
- **Guardar copia local siempre** en `clase N/claseN-tema.html`. El repo manda.
- **Commit por clase**, desde la raíz del repo (`D:\Escuela\20271` — ahí se hace todo Git, ver
  [CLAUDE.md raíz](../../../CLAUDE.md)). Mensaje al estilo del historial (`Add Clase N artifact (WAD)`),
  imperativo, **sin trailers de autoría ni referencias a IA**.

---

## Las tareas de una clase van también a la agenda del semestre

Cada tarea nueva que aparezca en una clase se registra **además** como bloque en
`../../../agenda/js/tareas.js` (ver [[project-agenda-semestre]]): archivo aparte, con todos los
campos que ya usa (`id`, `curso`, `titulo`, `tipo`, `entrega`, `fechaPorConfirmar`, `parcial`,
`estado`, `descripcion`, `entregable`, `pasos`, `origen`, `ruta`). No se borra de ahí hasta que el
usuario confirme *hecha* **y** *entregada*. Cuando la fecha es una suposición ("para el lunes"),
`fechaPorConfirmar: true` en vez de inventar certeza.

El checklist dentro del artifact y la agenda son dos cosas distintas: el artifact es para estudiar
esa clase; la agenda es el control global de entregas de las cinco materias.

---

## Memoria relacionada

- [[project-web-client-course]] — tabla de sustituciones de la transcripción, stack real del curso
  (Java + Spring + Angular, backend fijo), lista de todos los artifacts construidos, reglas de los
  reportes de lectura.
- [[project-agenda-semestre]] — la web de entregas y la regla de vida de las tareas.
- [[env-render-toolchain]] — `pdftotext` y el pipeline de render; no hay Node ni Python en la máquina.
- [[user-web-dev-background]] — por qué el material explica de más y no suelta respuestas sin contexto.
