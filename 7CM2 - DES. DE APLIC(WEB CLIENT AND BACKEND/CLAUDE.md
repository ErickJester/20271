# C704 — Web client and backend development frameworks

## Identidad de la materia
- Clave oficial: C704. Carpeta actual del repo: `7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND` (grupo 7CM2).
- IPN, ESCOM/UPIIZ, Ingeniería en Sistemas Computacionales, plan 2020, semestre VII, optativa teórico-práctica.
- Fuente de verdad: [`webClientAndBackendDevelopmentFrameworks.pdf`](webClientAndBackendDevelopmentFrameworks.pdf) — programa sintético oficial. Cualquier duda sobre contenido, numeración de unidades o prácticas se resuelve leyendo ese PDF, no inventando temario.
- Cinco unidades temáticas: I Arquitecturas de desarrollo web · II Desarrollo de APIs y servicios web · III Desarrollo de aplicaciones del lado del servidor · IV Desarrollo de aplicaciones del lado del cliente · V Servidores y contenedores de aplicación en la nube.
- El semestre converge en un proyecto único: backend + frontend publicados en un PaaS o servidor local, evaluado con rúbrica.

## Reglas para el material de estudio de esta carpeta

**Formato**: guías de estudio como HTML autocontenido, publicado como Artifact en claude.ai y copiado además a esta carpeta como archivo local (`unidadN-tema.html`). Así el material vive en el repo aunque el link del Artifact no se comparta.

**Alcance**: cuando se pida una guía de una unidad, debe ser **extensa y detallada**, no un resumen corto — este es el estilo que se espera en esta materia. Sigue la numeración oficial del programa punto por punto (1.1, 1.2, 1.2.1...) y añade, aparte:
- Una sección de prerrequisitos ("lo que hay que venir estudiado") cuando la unidad lo amerite.
- Diagramas propios (SVG inline) para los conceptos estructurales — no solo texto.
- Recuadros tipificados: *Del programa oficial* (cita textual del PDF), *Para el examen*, *Trampa común*, *En tu proyecto*.
- Banco de preguntas con respuesta modelo, glosario, y un plan de estudio con fechas.

**Idioma**: español, salvo términos técnicos que no se traducen (endpoint, framework, etc.).

**Diseño del HTML**: sigue las pautas de `artifact-design` (tipografía pareada, paleta propia por tema, ambos temas claro/oscuro, nada de plantillas genéricas de IA). Cada guía puede tener su propia identidad visual — no hace falta reutilizar la misma paleta entre unidades.

## Artifacts de clase

Distinto del material por unidad: hay **un artifact por sesión de clase**, que reconstruye esa clase
desde su grabación (no sigue el programa, sigue el pizarrón). Viven en
[`Primer parcial/clases/`](Primer%20parcial/clases/) y su reconstrucción, seguimiento y reglas de
diseño están en [`Primer parcial/clases/CLAUDE.md`](Primer%20parcial/clases/CLAUDE.md). Cuando se
traiga una grabación o transcripción de clase, ese archivo manda.

## Progreso de guías

| Unidad | Archivo | Estado |
|---|---|---|
| I — Arquitecturas de desarrollo web | [`unidad1-arquitecturas.html`](unidad1-arquitecturas.html) | Lista |
| II — APIs y servicios web | — | Pendiente |
| III — Aplicaciones del lado del servidor | — | Pendiente |
| IV — Aplicaciones del lado del cliente | — | Pendiente |
| V — Servidores y contenedores en la nube | — | Pendiente |

## Decisiones de stack para el proyecto del semestre
Aún no confirmadas con el profesor. El programa admite dos rutas según la bibliografía:
- **JavaScript**: Node + Express + MongoDB/Postgres + React o Angular (Holmes, Zammeti, Biswas).
- **Java**: Spring Boot + Hibernate/JPA + Postgres (Ottinger, Varanasi, Shmeling).

Preguntar en la primera clase cuál exige el profesor antes de comprometerse con una ruta en el proyecto.
