# 7CM3 — Administración de servicios en red

## Identidad de la materia
- Clave del repo/agenda: `7CM3` (no hay clave oficial tipo "C70x" en el programa sintético; el grupo es la que se usa en todo el sistema — ver `agenda/js/datos.js`).
- IPN, ESCOM/UPIIZ, Ingeniería en Sistemas Computacionales, plan 2020, semestre VII, obligatoria teórico-práctica.
- Fuente de verdad: [`administracionServiciosRed_ISC2020.pdf`](administracionServiciosRed_ISC2020.pdf) — programa sintético oficial. Cualquier duda sobre contenido, numeración de unidades o prácticas se resuelve leyendo ese PDF, no inventando temario.
- Cinco unidades temáticas: I Fundamentos de los servicios de red · II Temas avanzados de conectividad · III SNMP, monitoreo y calidad · IV Implementación de los servicios de red · V Gestión de la seguridad y el desempeño.
- El programa numera **13 prácticas de laboratorio** (Instalación de GNS3, balanceo de carga y alta disponibilidad, ACLs, NAT, SNMP —instalación y programación—, monitoreo, servicios diferenciados, mensajería instantánea, transferencia de archivos, VoIP, tolerancia a fallos, recuperación de red). Son deliverables distintos de las tareas de investigación/lectura — ver la distinción de badges en [`clases/CLAUDE.md`](clases/CLAUDE.md).

## Herramientas del curso
- **GNS3** — simulador de red, es la Práctica 1 del programa. Instalado el 2026-08-26 desde el release de GitHub (`GNS3-2.2.61-all-in-one.exe`, [github.com/GNS3/gns3-gui/releases](https://github.com/GNS3/gns3-gui/releases)) para no depender de la cuenta que exige gns3.com.

## Reglas para el material de estudio de esta carpeta

**Formato**: guías de estudio como HTML autocontenido, publicado como Artifact en claude.ai y copiado además a esta carpeta como archivo local (`unidadN-tema.html`). Así el material vive en el repo aunque el link del Artifact no se comparta.

**Alcance**: cuando se pida una guía de una unidad, debe ser **extensa y detallada**, no un resumen corto. Sigue la numeración oficial del programa punto por punto (1.1, 1.2, 1.2.1…) y añade, aparte:
- Una sección de prerrequisitos cuando la unidad lo amerite (esta materia asume Redes de Computadoras y Sistemas Operativos, aunque el programa no lo diga explícito).
- Diagramas propios (SVG inline) para topologías, flujos de protocolo y arquitecturas — no solo texto. Es una materia visual por naturaleza (diagramas de red, tablas de ACL, formatos de paquete).
- Recuadros tipificados: *Del programa oficial* (cita textual del PDF), *Para el examen*, *Trampa común*, *En tu proyecto/laboratorio*.
- Banco de preguntas con respuesta modelo, glosario, y un plan de estudio con fechas.

**Idioma**: español, salvo términos técnicos y comandos que no se traducen (switch, VLAN, `show running-config`, etc.).

**Diseño del HTML**: sigue las pautas de `artifact-design`. Cada guía puede tener su propia identidad visual — no hace falta reutilizar la misma paleta entre unidades.

## Artifacts de clase

Distinto del material por unidad: hay **un artifact por sesión de clase**, que reconstruye esa clase real desde su grabación (no sigue el programa, sigue el pizarrón o la práctica de laboratorio que se haya hecho). Viven en [`clases/`](clases/) y su pipeline, seguimiento y reglas de diseño están en [`clases/CLAUDE.md`](clases/CLAUDE.md) — ese archivo manda cuando se traiga una grabación o transcripción de clase.

**Nota de estructura**: a diferencia de 7CM2 (que separa por `Primer parcial/`), aquí `clases/`, `practicas/`, `tareas/` y `actividades/` van directo en la raíz de la materia mientras no haya evidencia de que el profesor divide por parcial. Si aparece esa división, ahí se anida — no antes.

## Progreso de guías

| Unidad | Archivo | Estado |
|---|---|---|
| I — Fundamentos de los servicios de red | — | Pendiente |
| II — Temas avanzados de conectividad | — | Pendiente |
| III — SNMP, monitoreo y calidad | — | Pendiente |
| IV — Implementación de los servicios de red | — | Pendiente |
| V — Gestión de la seguridad y el desempeño | — | Pendiente |

## Progreso de clases

Ver la tabla de seguimiento en [`clases/CLAUDE.md`](clases/CLAUDE.md) — vacía hasta la primera grabación.

## Decisiones de laboratorio para el proyecto del semestre

Aún no confirmadas con el profesor. El programa es agnóstico de fabricante en el papel, pero las 13 prácticas y la bibliografía (Harpreet/Packt sobre Cisco, LaCroix sobre Linux) apuntan a **GNS3 con IOS de Cisco** como entorno de laboratorio estándar del curso.
