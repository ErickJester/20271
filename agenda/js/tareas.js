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
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "En clase 2 solo pidió leerlo; en clase 3 subió a reporte de lectura calificado. Ya está redactado y compilado en el repo (Actividad2.pdf/.tex) — falta confirmar que se subió a la plataforma.",
    entregable: "Reporte en la plantilla LaTeX del curso",
    pasos: ["Leer el documento", "Sacar ideas clave", "Redactar el reporte", "Compilar PDF", "Subir"],
    origen: "Clase 3 · lista oficial de tareas (tentativa 11 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/actividades/actividad 2"
  },
  {
    id: "wad-lectura-jdbc",
    curso: "7CM2",
    titulo: "Reporte de lectura — Lectura rápida JDBC",
    tipo: "lectura",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Segundo reporte de lectura pedido en clase 3, sobre JDBC; reconfirmado en clase 4. Ya está redactado y compilado en el repo (Actividad3.pdf/.tex) — falta confirmar que se subió a la plataforma.",
    entregable: "Reporte en la plantilla LaTeX del curso",
    pasos: ["Leer el documento", "Sacar ideas clave", "Redactar el reporte", "Compilar PDF", "Subir"],
    origen: "Clase 3 · lista oficial de tareas (tentativa 11 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/actividades/actividad 3"
  },
  {
    id: "wad-inv-dto",
    curso: "7CM2",
    titulo: "Investigación — Patrón DTO",
    tipo: "investigacion",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Formato de siempre del profe: qué es, para qué sirve, con qué se come y cómo se adereza.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 3 · lista oficial de tareas (tentativa 11 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-dao",
    curso: "7CM2",
    titulo: "Investigación — Patrón DAO",
    tipo: "investigacion",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Mismo formato de cuatro preguntas. Conecta con el CarreraDAO que empezó en el pizarrón.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 3 · lista oficial de tareas (tentativa 11 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-soft-delete",
    curso: "7CM2",
    titulo: "Investigación — Soft delete",
    tipo: "investigacion",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Mismo formato de cuatro preguntas, más un quinto punto: cómo lo aplican. Motivado por la digresión de clase 4 sobre retención legal de datos (la ley de transparencia marca 12 años, no 5-10). Es para el proyecto, no solo para entregar.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Cómo lo aplican", "Compilar PDF", "Subir"],
    origen: "Clase 4 · lista oficial de tareas (tentativa 11 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-arch-cebolla",
    curso: "7CM2",
    titulo: "Investigación — Arquitectura de cebolla",
    tipo: "investigacion",
    entrega: "2026-09-18T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "En clase 2 la pidió junto con 'arquitecturas limpias' (puntos 1.3-1.5 del temario) sin fecha fija ('para mañana'). La lista oficial de tareas ahora solo menciona 'de cebolla', con fecha concreta 18 de septiembre — no está claro si 'limpias' se cayó o sigue viva sin fecha; confirmar con el profe o el jefe de grupo. Explicar capas, regla de dependencia para aislar la lógica de negocio, y ventajas para estructurar el backend.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Capas y regla de dependencia", "Ventajas para el backend", "Compilar PDF", "Subir"],
    origen: "Clase 2 · lista oficial de tareas (18 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-java-sonarqube",
    curso: "7CM2",
    titulo: "Investigación — Java moderno y code smells (SonarQube)",
    tipo: "investigacion",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "No salió en ninguna de las transcripciones de clase que se procesaron (1-4) — viene de la lista oficial de tareas. Características y mejores prácticas de Java 17+, más cómo identificar y resolver code smells con SonarQube.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Características de Java 17+", "Mejores prácticas", "Qué es un code smell", "SonarQube: cómo detectarlos y corregirlos", "Compilar PDF", "Subir"],
    origen: "Lista oficial de tareas (tentativa 11 sept) — sin origen en clase confirmado",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-solid",
    curso: "7CM2",
    titulo: "Investigación — Principios SOLID",
    tipo: "investigacion",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Tampoco salió en las transcripciones de clase 1-4 — viene de la lista oficial. Los 5 principios SOLID de diseño orientado a objetos, con ejemplos prácticos de cómo aplicarlos para un diseño mantenible, escalable y robusto.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Single Responsibility", "Open/Closed", "Liskov Substitution", "Interface Segregation", "Dependency Inversion", "Ejemplos de cada uno", "Compilar PDF", "Subir"],
    origen: "Lista oficial de tareas (tentativa 11 sept) — sin origen en clase confirmado",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-inv-builder",
    curso: "7CM2",
    titulo: "Investigación — Patrón Builder",
    tipo: "investigacion",
    entrega: "2026-09-07T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Salió de la clase 5, de la anotación @Builder de Lombok que escribió al recapitular. Las cuatro preguntas de siempre. Sin fecha: \"para algún día, váyanlo trabajando\". El documento ya está redactado y compilado en el repo — falta confirmar que se subió.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 5 (minuto ~44:00) · lista oficial de tareas",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas/investigacion patron builder"
  },
  {
    id: "wad-inv-http",
    curso: "7CM2",
    titulo: "Investigación — Códigos y protocolos HTTP(S)",
    tipo: "investigacion",
    entrega: "2026-09-07T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Salió de la clase 5, al mostrar el controlador con ResponseEntity. Para qué sirven y cuáles son los tipos de respuesta del protocolo. Los que ya usó en clase: 201, 200, 204, 400, 404 y 500. Sin fecha, pero la Unidad II es de APIs, así que se van a necesitar pronto.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Para qué sirve HTTP(S)", "Familias de códigos de respuesta (1xx-5xx)", "Ejemplos de cada familia", "Compilar PDF", "Subir"],
    origen: "Clase 5 (minuto ~46:20) · lista oficial de tareas",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas"
  },
  {
    id: "wad-gui-carrera",
    curso: "7CM2",
    titulo: "Ejercicio — Interfaz gráfica de escritorio para Carrera",
    tipo: "practica",
    entrega: "2026-09-07T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Swing o JavaFX, no web ni móvil. Al cargar, solo el botón 'Nuevo' está habilitado; el resto deshabilitado. Nuevo oculta idCarrera, habilita nombre/descripción y 'Guardar'; al guardar vuelve al estado inicial. 'Buscar' hace lo simétrico. Reusa el CarreraDAO tal cual, sin tocarlo.",
    entregable: "Código fuente de la interfaz (sin reporte)",
    pasos: ["Diseñar ventana con los botones del CRUD", "Estado inicial: solo Nuevo habilitado", "Flujo Nuevo → llenar → Guardar", "Flujo Buscar", "Conectar con CarreraDAO existente"],
    origen: "Clase 4 · lista oficial de tareas (hoy, 7 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/practicas/gui carrera"
  },
  {
    id: "wad-ejercicio-callable",
    curso: "7CM2",
    titulo: "Ejercicio — Cambiar PreparedStatement por CallableStatement",
    tipo: "practica",
    entrega: "2026-09-07T23:00",
    fechaPorConfirmar: true,
    parcial: 1,
    estado: "pendiente",
    descripcion: "El profesor dijo en clase 4 'para la próxima semana'; la lista oficial trae esta fila mezclada con un 'Instalación de Java' que parece un pegado de otra fila — la fecha (hoy) se toma con cautela. Requiere antes crear los 5 stored procedures en MySQL (solo el de insertar quedó en el pizarrón) y haber leído JDBC. Los índices de parámetro siguen empezando en 1; cambia prepareStatement() por prepareCall() y el texto por \"{call sp_nombre(?, ?)}\".",
    entregable: "CarreraDAO modificado + los 5 stored procedures en SQL",
    pasos: ["Escribir los 5 stored procedures en MySQL", "Cambiar create() a CallableStatement", "Cambiar update()", "Cambiar delete()", "Cambiar read() y readAll()", "Probar contra la base"],
    origen: "Clase 4 · lista oficial de tareas (hoy, 7 sept — fecha con reserva)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/practicas/callable statement"
  },
  {
    id: "wad-minisitio-escom",
    curso: "7CM2",
    titulo: "Réplica del minisitio de nuevo ingreso ESCOM",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Copia fiel de https://www.escom.ipn.mx/nuevoingreso27_1/ (estructura, estilos, componentes), pensada para servir después desde un contenedor en localhost:3500. Ya está en el repo con Dockerfile propio (tarea 2) — falta confirmar si se le cambió el título a 'Mi minisitio de <tu nombre>' como pidió, y si ya se entregó.",
    entregable: "index.html + Dockerfile",
    pasos: ["Clonar estructura, estilos y componentes", "Cambiar título con tu nombre", "Dockerfile para servir en :3500", "Subir"],
    origen: "Clase 2 · lista oficial de tareas",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas/tarea 2"
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
  {
    id: "wad-proyecto-avance1",
    curso: "7CM2",
    titulo: "Avance 1er parcial (Proyecto) — modelo E-R, script SQL, diccionario",
    tipo: "proyecto",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Propuesta de proyecto con: modelo entidad-relación, script SQL, representación relacional de las tablas aceptada por los modeladores, y diccionario de datos de al menos 5 tablas sin contar Usuarios (esa va de cajón, aparte). Sin fecha fija en la lista oficial todavía.",
    entregable: "Modelo E-R + script SQL + representación relacional + diccionario de datos",
    pasos: ["Modelo entidad-relación", "Script SQL (MySQL)", "Representación relacional de las tablas", "Diccionario de datos (≥5 tablas, sin Usuarios)"],
    origen: "Clase 2 · lista oficial de tareas",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial"
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
