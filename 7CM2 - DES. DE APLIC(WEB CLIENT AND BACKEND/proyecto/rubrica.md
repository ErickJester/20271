# Rúbrica Proyecto — Avance 1: Diseño e Implementación de Base de Datos

En esta actividad, el alumno, organizado en equipos de máximo 4 integrantes, iniciará el desarrollo del proyecto que será trabajado durante el curso.

El equipo deberá identificar una problemática que pueda ser atendida mediante el diseño e implementación de una base de datos. A partir de esta problemática, desarrollará el primer avance del proyecto, estableciendo las entidades, atributos y relaciones que conformarán la base de datos.

El proyecto deberá considerar como mínimo 5 entidades, sin contabilizar las entidades relacionadas con autenticación y autorización, ya que estos elementos serán trabajados de manera obligatoria durante el curso.

Como parte de este avance, el equipo deberá elaborar el diccionario de datos, modelo Entidad-Relación, representación relacional y script SQL de creación y poblado de la base de datos, considerando un mínimo de 3 registros por cada tabla.

Todo el trabajo deberá documentarse mediante un reporte elaborado en LaTeX, incluyendo las evidencias correspondientes del proceso y de los resultados obtenidos.

La actividad será evaluada mediante una rúbrica general con un valor de 10 puntos.

## Instrucciones

1. Formen un equipo de máximo 4 integrantes.
2. Identifiquen una problemática que será trabajada durante el curso. La problemática deberá ser suficientemente clara para justificar la creación de una base de datos como parte de una posible solución.
3. Definan el proyecto de base de datos, considerando como mínimo 5 entidades.
   - Las entidades relacionadas con autenticación y autorización no se contabilizan dentro de las 5 entidades.
   - La implementación de autenticación y autorización será obligatoria y se desarrollará durante el curso.
4. Elaboren el Diccionario de Datos de las entidades identificadas. Como mínimo deberá incluir:
   - Tabla o entidad.
   - Atributos/campos.
   - Tipo de dato.
   - Longitud, cuando corresponda.
   - Descripción.
   - Clave primaria.
   - Claves foráneas.
   - Restricciones relevantes.
5. Elaboren el Modelo Entidad-Relación (E-R) de la base de datos, identificando:
   - Entidades.
   - Atributos.
   - Relaciones.
   - Cardinalidades.
   - Claves correspondientes.
6. Elaboren la Representación Relacional a partir del modelo E-R, indicando claramente:
   - Tablas.
   - Claves primarias.
   - Claves foráneas.
   - Relaciones entre las tablas.
7. Elaboren el Script SQL que permita:
   - Crear la base de datos.
   - Crear las tablas.
   - Definir claves primarias y foráneas.
   - Establecer las restricciones necesarias.
   - Poblar las tablas.
   - Incluir al menos 3 registros por cada tabla.
8. Ejecuten y validen el script SQL. Deberán comprobar que la base de datos se crea correctamente y que los registros fueron insertados. Incluyan capturas de pantalla como evidencia.
9. Elaboren un reporte en LaTeX con la siguiente estructura:
   1. **Portada** — Incluir institución, asignatura, nombre de la actividad, nombre del proyecto, integrantes, docente y fecha.
   2. **Introducción — 2 puntos** — Presentar la problemática, contexto, justificación y propósito del proyecto.
   3. **Desarrollo — 4 puntos** — Incluir la descripción de la problemática y solución propuesta, entidades, diccionario de datos, modelo E-R, representación relacional, script SQL y evidencias del trabajo realizado. En caso de que la actividad incluya instalación y configuración de herramientas, documentar el proceso de descarga, instalación, configuración de variables de entorno y evidencia del resultado obtenido.
   4. **Conclusiones — 3 puntos** — Analizar los resultados obtenidos, aprendizajes, dificultades encontradas y posibles errores durante la instalación o configuración de las herramientas, así como las acciones realizadas para solucionarlos.
   5. **Referencias bibliográficas — 1 punto** — Incluir las fuentes de información utilizadas, empleando un formato de referencia consistente.
10. Verifiquen la consistencia de la información antes de realizar la entrega. Las entidades y atributos del diccionario de datos deberán coincidir con el modelo E-R, la representación relacional y el script SQL.

## Entregables

Subir a Moodle:

- Reporte final en PDF, generado a partir de LaTeX.
- Archivos fuente del reporte en LaTeX (`.tex` y archivos necesarios para su compilación).
- Script SQL de creación y poblado de la base de datos.
- Archivos adicionales que sean necesarios para comprobar el funcionamiento del proyecto.

## Criterios de evaluación

La actividad será evaluada mediante una rúbrica general, con un valor total de 10 puntos:

- Introducción y planteamiento de la problemática: 2 puntos.
- Desarrollo y documentación del proyecto: 4 puntos.
- Conclusiones y análisis de dificultades: 3 puntos.
- Referencias bibliográficas: 1 punto.

**Importante**: El incumplimiento de los requisitos técnicos del proyecto (mínimo 5 entidades, diccionario de datos, modelo E-R, representación relacional, script SQL y mínimo 3 registros por tabla) afectará la valoración del apartado de Desarrollo.

## Rúbrica de calificación

### Justificación y Alcance de Entidades

| Puntos | Descripción |
|---|---|
| 0 | No justifica el problema o no presenta entidades válidas. |
| 0.5 | Planteamiento vago; define menos de 3 entidades válidas o incluye autenticación en el conteo base. |
| 1 | Justifica la problemática pero presenta de 3 a 4 entidades, o el alcance no refleja un dominio relacional claro. |
| 1.5 | Plantea y justifica claramente la problemática; define un mínimo de 5 entidades de negocio sin contabilizar módulos de autenticación/autorización. |

### Diccionario de Datos

| Puntos | Descripción |
|---|---|
| 0 | No incluye diccionario de datos. |
| 0.5 | Diccionario incompleto (omite tipos, longitudes o la mayoría de atributos clave) en más del 50% de las tablas. |
| 1 | Incluye diccionario para todas las entidades, pero omite descripciones semánticas o definiciones de PK/FK en varias tablas. |
| 1.5 | Diccionario completo con detalles menores en tipos de datos o precisiones de longitud. |
| 2 | Documenta tabla, atributos, tipos de datos, longitudes, descripciones claras, PKs y FKs exhaustivas para todas las entidades. |

### Modelado Conceptual y Relacional

| Puntos | Descripción |
|---|---|
| 0 | No entrega modelo E-R ni esquema relacional. |
| 0.5 | Entrega diagramas inconsistentes, sin cardinalidades claras ni llaves primarias/foráneas identificables. |
| 1 | Presenta modelo E-R o relacional con errores estructurales severos (dependencias transitivas o relaciones mal mapeadas). |
| 2 | Presenta ambos modelos con cardinalidades correctas y normalización básica; presenta discrepancias mínimas entre ambos modelos. |
| 2.5 | Diagrama E-R y esquema relacional normalizados, perfectamente consistentes entre sí, con atributos, llaves y cardinalidades explícitas. |

### Script SQL (DDL y DML)

| Puntos | Descripción |
|---|---|
| 0 | No entrega script SQL o contiene errores de sintaxis irrecuperables. |
| 0.5 | Script incompleto: define tablas pero omite llaves foráneas/restricciones y no incluye datos de prueba. |
| 1 | Crea las tablas correctamente pero no puebla los datos requeridos (menos de 3 registros por tabla o fallas de integridad referencial). |
| 2 | Crea la base de datos y puebla al menos 3 registros por tabla, pero con fallas leves en tipos de datos o constraints. |
| 2.5 | Script DDL y DML ejecutable y sin errores; define constraints (PK, FK, nullability) y puebla ≥3 registros consistentes por tabla. |

### Reporte en LaTeX

| Puntos | Descripción |
|---|---|
| 0 | No utiliza LaTeX o entrega un archivo no compilable sin estructura formal. Utiliza LaTeX pero el documento carece de estructura académica, código desbordado o imágenes sin referenciar. |
| 1 | Documento estructurado en LaTeX pero omite evidencias clave de ejecución/resultados de los scripts. |
| 2 | Reporte formal en LaTeX con tipografía adecuada, estructura clara, código fuente legible y evidencias completas del proceso y ejecución. |
