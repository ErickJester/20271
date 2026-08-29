# 20271 — Raíz del repositorio (coordinación de Git)

Esta carpeta raíz (`D:\Escuela\20271`) se usa **solo para operaciones de Git**: `pull`, `push`,
`commit`, `merge`, resolución de conflictos, ramas y estado del árbol de trabajo — para **todas** las
carpetas de materia que contiene.

El trabajo de contenido de cada materia (guías de estudio, tareas, infografías, LaTeX, etc.) se hace
**dentro de la subcarpeta correspondiente**, siguiendo el `CLAUDE.md` propio de esa carpeta. Cuando la
sesión arranca en esta raíz, el trabajo es versionar, no redactar material.

## Estructura

- **Monorepo único.** No hay submódulos. Un solo `.git` en la raíz cubre las 6 materias.
- **Remoto**: `origin` → `https://github.com/ErickJester/20271.git`
- **Rama**: `master` (única; se trabaja directo sobre ella).
- Carpetas de materia, una por asignatura del semestre 20271:
  - `7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND` (clave C704)
  - `7CM3 - ADMINISTRACIÓN DE SERVICIOS EN RED`
  - `7CM4 - DESARROLLO DE APLICACIONES MÓVILES`
  - `8CM1 - GESTIÓN EMPRESARIAL`
  - `8CM2 - LIDERAZGO PERSONAL`
  - `8CX1 - TRABAJO TERMINAL II`

## Flujo de trabajo

- **Pull**: usar `git pull --ff-only origin master`. Si no avanza en fast-forward, avisar antes de
  hacer merge o rebase.
- **Antes de tocar nada**: `git fetch` + `git status` para ver si el local está atrás del remoto.
- **Commits**: agrupar por materia y por entregable. Un commit no debería mezclar cambios de dos
  materias salvo que sean parte del mismo movimiento (p. ej. renombrar carpetas).
- **Push**: `git push origin master` una vez que los commits estén listos. Confirmar con el usuario
  antes de hacer push (acción hacia afuera).
- **`.gitignore`** ya cubre: artefactos de compilación de LaTeX (`*.aux`, `*.bbl`, `build/`, …),
  basura de macOS (`.DS_Store`), y `7CM2/ReporteWAD/` (excluido a propósito). Antes de agregar
  binarios grandes o carpetas de build nuevas, revisar si deben ir al `.gitignore`.

## Convenciones de mensajes de commit

- **Idioma**: como el historial existente — imperativo, con mayúscula inicial, español/inglés según
  el término. Ejemplos reales: `Add Tarea 1 (WAD) and Asignación 1 (Liderazgo) infografía`,
  `Rename course folders to use group codes`.
- Referir la materia por su abreviatura o clave cuando ayude a ubicar el cambio.
- **Nunca** incluir referencias a Claude, Anthropic o IA en el mensaje, ni trailers de autoría
  (`Co-Authored-By`, `Signed-off-by` con modelos). Los commits deben parecer escritos enteramente por
  el desarrollador.
