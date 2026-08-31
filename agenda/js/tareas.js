/* ============================================================
   tareas.js — REGISTRO DE ENTREGAS DEL SEMESTRE
   ------------------------------------------------------------
   Este es el archivo aparte donde vive TODA tarea nueva.
   El código de la app nunca guarda tareas dentro de sí mismo:
   solo lee de aquí.

   REGLA DE BORRADO (la única que importa):
   una tarea NO se elimina de este archivo hasta que en la web
   quede en estado "confirmada", es decir, hasta que confirmes
   con las dos casillas que (1) ya la hiciste y (2) ya la
   entregaste. Marcarla como "hecha" no basta. Marcarla como
   "entregada" tampoco. Solo la confirmación.

   Para agregar una tarea a mano: copia un bloque, cámbiale el
   id (tiene que ser único) y pégalo dentro del arreglo.

   Campos:
     id          texto único, sin espacios
     curso       clave del curso (7CM2, 7CM3, 7CM4, 8CM1, 8CM2)
     titulo      cómo se llama la entrega
     tipo        tarea | actividad | practica | investigacion |
                 lectura | proyecto | examen | estudio
     entrega     "2026-08-31T23:00"  ó  null si no tiene fecha
     fechaPorConfirmar  true si la fecha es una suposición
     parcial     1, 2 ó 3 (null si no aplica)
     estado      estado inicial: pendiente | en-curso | hecha |
                 entregada   (la web guarda los cambios encima)
     descripcion qué hay que hacer, en tus palabras
     entregable  qué se sube exactamente
     pasos       lista de sub-pasos, se vuelven checklist
     origen      de dónde salió (clase, plataforma, syllabus)
     ruta        carpeta del repo donde va el trabajo
   ============================================================ */

const TAREAS = [

  /* ---------- 7CM2 · Web Client & Backend ---------- */
  {
    id: "wad-actividad-1",
    curso: "7CM2",
    titulo: "Actividad 1",
    tipo: "actividad",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Actividad 1 del primer parcial, redactada con la plantilla LaTeX de la plataforma (biblatex/biber).",
    entregable: "Actividad1.pdf",
    pasos: ["Redactar en LaTeX", "Compilar con biber", "Subir a la plataforma"],
    origen: "Plataforma del curso",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/actividades/actividad 1"
  },
  {
    id: "wad-tarea-1",
    curso: "7CM2",
    titulo: "Tarea 1",
    tipo: "tarea",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Tarea 1 del primer parcial, misma plantilla LaTeX.",
    entregable: "Tarea1.pdf",
    pasos: ["Redactar en LaTeX", "Compilar con biber", "Subir a la plataforma"],
    origen: "Plataforma del curso",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas/tarea 1"
  },
  {
    id: "wad-lectura-java",
    curso: "7CM2",
    titulo: "Reporte de lectura — Lectura rápida Java",
    tipo: "lectura",
    entrega: "2026-08-31T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "En clase 2 solo pidió leerlo; en clase 3 subió a reporte de lectura calificado.",
    entregable: "Reporte en la plantilla LaTeX del curso",
    pasos: ["Leer el documento", "Sacar ideas clave", "Redactar el reporte", "Compilar PDF", "Subir"],
    origen: "Clase 3",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-lectura-jdbc",
    curso: "7CM2",
    titulo: "Reporte de lectura — Lectura rápida JDBC",
    tipo: "lectura",
    entrega: "2026-08-31T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Segundo reporte de lectura pedido en clase 3, sobre JDBC.",
    entregable: "Reporte en la plantilla LaTeX del curso",
    pasos: ["Leer el documento", "Sacar ideas clave", "Redactar el reporte", "Compilar PDF", "Subir"],
    origen: "Clase 3",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-dto",
    curso: "7CM2",
    titulo: "Investigación — Patrón DTO",
    tipo: "investigacion",
    entrega: "2026-08-31T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Formato de siempre del profe: qué es, para qué sirve, con qué se come y cómo se adereza.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 3",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-dao",
    curso: "7CM2",
    titulo: "Investigación — Patrón DAO",
    tipo: "investigacion",
    entrega: "2026-08-31T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Mismo formato de cuatro preguntas. Conecta con el CarreraDAO que empezó en el pizarrón.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 3",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-repaso-bd",
    curso: "7CM2",
    titulo: "Repasar Bases de Datos",
    tipo: "estudio",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "No es calificado, pero el curso es integrador y da por vista toda la materia de Bases de Datos.",
    entregable: "Nada que subir",
    pasos: ["CRUD en SQL", "Joins", "Normalización", "Stored procedures"],
    origen: "Clase 3",
    ruta: ""
  },
  {
    id: "wad-proyecto-equipo",
    curso: "7CM2",
    titulo: "Proyecto — armar equipo de 4 y elegir problema",
    tipo: "proyecto",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Equipos de 4. El problema lo eligen ustedes. El backend queda fijo en Spring; solo se puede cambiar una pieza del stack.",
    entregable: "Propuesta de proyecto (sube una sola persona)",
    pasos: ["Formar equipo", "Elegir problema", "Definir stack", "Redactar propuesta"],
    origen: "Clase 1",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND"
  },

  /* ---------- 8CM2 · Liderazgo Personal ---------- */
  {
    id: "lid-asignacion-1",
    curso: "8CM2",
    titulo: "Asignación 1 — Infografía del concepto de liderazgo",
    tipo: "actividad",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Infografía A3 a partir de la transcripción de YouTube y el documento de ITESCAM.",
    entregable: "PDF + PNG A3",
    pasos: ["Leer las dos fuentes", "Diseñar la infografía", "Exportar PDF y PNG", "Subir"],
    origen: "Profesora de Liderazgo",
    ruta: "8CM2 - LIDERAZGO PERSONAL/asignaciones/Asignacion 1"
  },
  {
    id: "lid-practica-1",
    curso: "8CM2",
    titulo: "Práctica 1 — Análisis del liderazgo",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: null,
    estado: "pendiente",
    descripcion: "Primera de las tres prácticas obligatorias del programa. Aún sin fecha.",
    entregable: "Reporte de práctica",
    pasos: [],
    origen: "Programa de estudios",
    ruta: "8CM2 - LIDERAZGO PERSONAL"
  },
  {
    id: "lid-practica-2",
    curso: "8CM2",
    titulo: "Práctica 2 — Plan de liderazgo personal",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: null,
    estado: "pendiente",
    descripcion: "Segunda práctica obligatoria del programa. Aún sin fecha.",
    entregable: "Reporte de práctica",
    pasos: [],
    origen: "Programa de estudios",
    ruta: "8CM2 - LIDERAZGO PERSONAL"
  },
  {
    id: "lid-practica-3",
    curso: "8CM2",
    titulo: "Práctica 3 — Solución de conflictos",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: null,
    estado: "pendiente",
    descripcion: "Tercera práctica obligatoria del programa. Aún sin fecha.",
    entregable: "Reporte de práctica",
    pasos: [],
    origen: "Programa de estudios",
    ruta: "8CM2 - LIDERAZGO PERSONAL"
  }

  /* 7CM3, 7CM4 y 8CM1 todavía no tienen entregas registradas.
     En cuanto salga la primera, se agrega aquí. */
];
