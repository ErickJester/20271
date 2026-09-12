/* ============================================================
   datos.js — Configuración fija del semestre.
   Aquí NO van las tareas (esas viven en js/tareas.js).
   Aquí va lo que casi no cambia: materias, colores, unidades,
   parciales y horario de clases.
   ============================================================ */

const CONFIG = {
  semestre: "2027-1",
  periodo: "agosto 2026 – enero 2027",
  alumno: "Angel Frausto Robles",
  boleta: "2019630177",
  // Regla del profe de 7CM2: las entregas se cierran el domingo 23:00.
  horaEntregaPorDefecto: "23:00"
};

const CURSOS = [
  {
    clave: "7CM2",
    nombre: "Web Client & Backend",
    nombreOficial: "Web client and backend development frameworks",
    semestre: "VII",
    color: "#4f46e5",
    carpeta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND",
    unidades: [
      "I. Arquitecturas de desarrollo web",
      "II. Desarrollo de API´s y servicios web",
      "III. Desarrollo de aplicaciones del lado del servidor",
      "IV. Desarrollo de aplicaciones del lado del cliente",
      "V. Servidores y contenedores de aplicación en la nube"
    ],
    reglas: [
      "Entregas: domingo 23:00.",
      "Stack base: Java + Spring (backend, fijo) + Angular (frontend).",
      "Se puede cambiar UNA sola pieza del stack, y solo en el proyecto.",
      "Proyecto en equipos de 4; una persona sube la entrega.",
      "Los reportes usan la plantilla LaTeX de la plataforma.",
      "Todos los exámenes son teórico-prácticos.",
      "Cursos externos (Huawei, Red Hat, Google) dan +1 en el examen."
    ]
  },
  {
    clave: "7CM3",
    nombre: "Servicios en Red",
    nombreOficial: "Administración de servicios en red",
    semestre: "VII",
    color: "#0d9488",
    carpeta: "7CM3 - ADMINISTRACIÓN DE SERVICIOS EN RED",
    unidades: [
      "I. Fundamentos de los servicios de red",
      "II. Temas avanzados de conectividad",
      "III. SNMP, monitoreo y calidad",
      "IV. Implementación de los servicios de red",
      "V. Gestión de la seguridad y el desempeño"
    ],
    reglas: []
  },
  {
    clave: "7CM4",
    nombre: "Apps Móviles",
    nombreOficial: "Desarrollo de aplicaciones móviles nativas",
    semestre: "VII",
    color: "#d97706",
    carpeta: "7CM4 - DESARROLLO DE APLICACIONES MOVILES",
    unidades: [
      "I. Aspectos básicos en el desarrollo de aplicaciones móviles",
      "II. Estructura y componentes de la interfaz de usuario",
      "III. Almacenamiento y manejo de datos",
      "IV. Servicios y comunicaciones",
      "V. Publicación y API´s"
    ],
    reglas: ["Debe ser nativo (Kotlin / Swift). Nada de React Native."]
  },
  {
    clave: "8CM1",
    nombre: "Gestión Empresarial",
    nombreOficial: "Gestión empresarial",
    semestre: "VIII",
    color: "#7c3aed",
    carpeta: "8CM1 - GESTIÓN EMPRESARIAL",
    unidades: [
      "I. Aspectos básicos de la gestión empresarial",
      "II. Gestión estratégica en las empresas de innovación tecnológica",
      "III. Gestión operativa de la empresa",
      "IV. Gestión del capital humano y la calidad",
      "V. Tendencias en la gestión empresarial"
    ],
    reglas: []
  },
  {
    clave: "8CM2",
    nombre: "Liderazgo Personal",
    nombreOficial: "Liderazgo personal",
    semestre: "VIII",
    color: "#9d174d",
    carpeta: "8CM2 - LIDERAZGO PERSONAL",
    unidades: [
      "I. Fundamentos teóricos del liderazgo",
      "II. Conceptualización de la gestión del liderazgo y desarrollo humano",
      "III. Liderazgo social"
    ],
    reglas: [
      "Evidencias: informe de discusión dirigida, reporte de sociometrías,",
      "solución de casos, mapas mentales, ensayos, reportes de prácticas y evaluación escrita.",
      "Entregables como infografía: pocas palabras, mucho diagrama."
    ]
  }
];

/* Parciales. Cuando sepas las fechas reales, ponlas aquí como
   "2026-08-25" y el calendario pintará el rango en la vista de mes. */
const PERIODOS = [
  { nombre: "Primer parcial",  inicio: null, fin: null },
  { nombre: "Segundo parcial", inicio: null, fin: null },
  { nombre: "Tercer parcial",  inicio: null, fin: null }
];

/* Horario de clases. Formato:
   { curso: "7CM2", dia: 1, inicio: "07:00", fin: "08:30" }
   dia: 1 = lunes … 7 = domingo.
   Si lo llenas, cada día del calendario muestra un punto por clase. */
const HORARIO = [];

/* Tipos de entregable y su etiqueta corta */
const TIPOS = {
  tarea:         "Tarea",
  actividad:     "Actividad",
  practica:      "Práctica",
  investigacion: "Investigación",
  lectura:       "Reporte de lectura",
  proyecto:      "Proyecto",
  examen:        "Examen",
  estudio:       "Estudio"
};

/* Los cinco estados por los que pasa una entrega.
   OJO: solo "confirmada" habilita borrar la tarea del archivo. */
const ESTADOS = {
  pendiente:  { etiqueta: "Pendiente",  color: "#94a3b8" },
  "en-curso": { etiqueta: "En curso",   color: "#2563eb" },
  hecha:      { etiqueta: "Hecha",      color: "#d97706" },
  entregada:  { etiqueta: "Entregada",  color: "#16a34a" },
  confirmada: { etiqueta: "Confirmada", color: "#0f766e" }
};
