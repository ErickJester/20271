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
    estado: "hecha",
    descripcion: "En clase 2 solo pidió leerlo; en clase 3 subió a reporte de lectura calificado. Verificado en el repo: redactado y compilado (Actividad2.pdf, 339 KB, junto al PDF fuente LecturaRapidaJava.pdf) — falta confirmar que se subió a la plataforma.",
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
    estado: "hecha",
    descripcion: "Segundo reporte de lectura pedido en clase 3, sobre JDBC; reconfirmado en clase 4. Verificado en el repo: redactado y compilado (Actividad3.pdf, 375 KB, junto al PDF fuente JDBCLecturaRapida.pdf) — falta confirmar que se subió a la plataforma.",
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
    id: "wad-instalacion-java",
    curso: "7CM2",
    titulo: "Instalación del Java Development Kit (JDK)",
    tipo: "tarea",
    entrega: "2026-09-11T18:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Reporte LaTeX calificado (10 pts) con rúbrica propia, entregado a las 6pm de hoy. Completo: portada, Introducción (2 pts), Desarrollo (4 pts, con 4 capturas reales — descarga, instalación registrada en Windows, JAVA_HOME en Variables de entorno, verificación en terminal), Conclusiones (3 pts, 5 errores comunes de instalación con propuestas de solución), Referencias (1 pt, 3 fuentes: Oracle + Adoptium). Instalado y verificado: Eclipse Temurin (Adoptium) OpenJDK 21.0.12.1 LTS, JAVA_HOME en C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.12.101-hotspot\\ como variable de sistema persistente. Compilado sin errores (InstalacionJava.pdf, 7 páginas) — falta subir a la plataforma. Pendiente sin resolver: si el profesor exige el instalador oficial de Oracle en vez de un build certificado de OpenJDK (Adoptium/Temurin), habría que rehacerlo.",
    entregable: "Reporte LaTeX (PDF) con portada, introducción, desarrollo con evidencia gráfica, conclusiones y referencias",
    pasos: ["Confirmar si debe ser el instalador de Oracle o si Adoptium/Temurin ya instalado es válido", "Tomar capturas: página de descarga, instalador, ventana de Variables de entorno con JAVA_HOME, verificación en terminal", "Redactar Introducción (2 pts)", "Redactar Desarrollo con las capturas (4 pts)", "Redactar Conclusiones sobre errores comunes (3 pts)", "Referencias, mínimo 2 fuentes (1 pt)", "Compilar con MiKTeX (pdflatex + biber)", "Subir antes de las 18:00"],
    origen: "Lista oficial de tareas (rúbrica completa pegada por el usuario, 11 sept 18:00)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas/instalacion java"
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
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Salió de la clase 5, de la anotación @Builder de Lombok que escribió al recapitular. Las cuatro preguntas de siempre. En clase sonó como \"para algún día, váyanlo trabajando\", pero la lista oficial de tareas la trae con fecha fija: 7 de septiembre — ya vencida. Verificado en el repo: InvestigacionBuilder.pdf redactado y compilado — falta confirmar que se subió.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Qué es", "Para qué sirve", "Con qué se come", "Cómo se adereza", "Compilar PDF", "Subir"],
    origen: "Clase 5 (minuto ~44:00) · confirmada en la lista oficial de tareas (7 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/tareas/investigacion patron builder"
  },
  {
    id: "wad-inv-http",
    curso: "7CM2",
    titulo: "Investigación — Códigos y protocolos HTTP(S)",
    tipo: "investigacion",
    entrega: "2026-09-17T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "en-curso",
    descripcion: "Salió de la clase 5, al mostrar el controlador con ResponseEntity. Para qué sirven y cuáles son los tipos de respuesta del protocolo. Los que ya usó en clase: 201, 200, 204, 400, 404 y 500. La lista oficial de tareas la fecha para el 17 de septiembre (corregido: antes se estimaba 7 sept por error). Verificado en el repo: InvestigacionHTTP.tex SÍ está compilado (InvestigacionHTTP.pdf existe), pero se quedó dentro de la carpeta build/ — a diferencia de las demás investigaciones, nunca se copió el PDF a la raíz de la carpeta. Falta ese paso y confirmar que se subió.",
    entregable: "Documento en la plantilla LaTeX del curso",
    pasos: ["Para qué sirve HTTP(S)", "Familias de códigos de respuesta (1xx-5xx)", "Ejemplos de cada familia", "Compilar PDF", "Copiar el PDF de build/ a la raíz de la carpeta", "Subir"],
    origen: "Clase 5 (minuto ~46:20) · lista oficial de tareas (17 sept)",
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
    estado: "hecha",
    descripcion: "Swing o JavaFX, no web ni móvil. Al cargar, solo el botón 'Nuevo' está habilitado; el resto deshabilitado. Nuevo oculta idCarrera, habilita nombre/descripción y 'Guardar'; al guardar vuelve al estado inicial. 'Buscar' hace lo simétrico. Reusa el CarreraDAO tal cual, sin tocarlo. Verificado en el repo: VentanaCarrera.java implementa exactamente ese flujo, y los .class están compilados (fechados 7 sept, el día de entrega).",
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
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "El profesor dijo en clase 4 'para la próxima semana'. Confirmado: la lista oficial de tareas trae esta fila literalmente mezclada con 'Instalación de Java' (no es un error de lectura — el renglón real de la plataforma dice \"Instalación de Java. Ejercicio dos cambiar el ejercicios con prepareStatement y ponerlo con CalledeStatemnt\"), con fecha 7 de septiembre — ya vencida. Requiere antes crear los 5 stored procedures en MySQL (solo el de insertar quedó en el pizarrón) y haber leído JDBC. Los índices de parámetro siguen empezando en 1; cambia prepareStatement() por prepareCall() y el texto por \"{call sp_nombre(?, ?)}\". Verificado en el repo: CarreraDAO.java (en practicas/gui carrera/) SIGUE usando PreparedStatement en los 5 métodos, no hay ni un CallableStatement, y no existe ningún stored procedure en SQL en todo el repo. No se ha empezado.",
    entregable: "CarreraDAO modificado + los 5 stored procedures en SQL",
    pasos: ["Escribir los 5 stored procedures en MySQL", "Cambiar create() a CallableStatement", "Cambiar update()", "Cambiar delete()", "Cambiar read() y readAll()", "Probar contra la base"],
    origen: "Clase 4 · confirmada en la lista oficial de tareas (7 sept, en fila mezclada con Instalación de Java)",
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
    descripcion: "Copia fiel de https://www.escom.ipn.mx/nuevoingreso27_1/ (estructura, estilos, componentes), pensada para servir después desde un contenedor en localhost:3500. Ya está en el repo con Dockerfile propio (tarea 2). CONFLICTO SIN RESOLVER: se marcó 'hecha' porque en clase 6 el profesor dijo que el grupo ya la había entregado — pero la lista oficial de tareas la sigue trayendo con fecha pendiente ('lunes, miércoles o jueves de la próxima semana'). Puede ser una entrega distinta (con más requisitos) o que la plataforma no se haya actualizado. Confirmar antes de dar esto por cerrado.",
    entregable: "index.html + Dockerfile",
    pasos: ["Clonar estructura, estilos y componentes", "Cambiar título con tu nombre", "Dockerfile para servir en :3500", "Subir"],
    origen: "Clase 2 · lista oficial de tareas (fecha en conflicto con clase 6)",
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
    descripcion: "Propuesta de proyecto con: portada, diccionario de datos (atributo, tipo, restricciones y descripción), modelo entidad-relación, script SQL de creación de la base, y su representación relacional (se puede generar por ingeniería inversa desde el script con Workbench/DBeaver/Navicat). Al menos 5 tablas sin contar el módulo de gestión de usuarios (usuarios + roles + su tabla de unión van aparte, de cajón), y la base en al menos Tercera Forma Normal (3FN). En clase 6 dijo 'asuman que todo se entrega el 11 de septiembre' de forma general, pero la lista oficial de tareas trae esta fila SIN fecha — se le quita la fecha inventada y se deja sin fecha, como dice la fuente autoritativa.",
    entregable: "Portada + diccionario de datos + modelo E-R + script SQL + representación relacional",
    pasos: ["Portada", "Diccionario de datos (≥5 tablas, sin Usuarios, 3FN)", "Script SQL (MySQL)", "Modelo entidad-relación", "Representación relacional de las tablas"],
    origen: "Clase 2 · reconfirmado en clase 6 · sin fecha en la lista oficial de tareas",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial"
  },
  {
    id: "wad-docker-apache-nginx",
    curso: "7CM2",
    titulo: "Ejercicio — Publicar el minisitio en Apache (:8080) y Nginx (:8081)",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "pendiente",
    descripcion: "Reusar el minisitio de nuevo ingreso (tarea 2, ya con Dockerfile para :3500) y duplicarlo en dos servidores distintos: uno basado en httpd expuesto en el puerto 8080, y otro basado en nginx expuesto en el puerto 8081. El único cambio real entre los dos Dockerfiles es la carpeta de destino del COPY: /usr/local/apache2/htdocs/ en Apache, /usr/share/nginx/html/ en Nginx. Se hizo en vivo durante la clase 6, sin fecha de entrega explícita — confirmar si es solo un ejercicio de práctica o si también se sube a algún lado.",
    entregable: "Dos imágenes/contenedores corriendo (Apache :8080, Nginx :8081) o sus dos Dockerfiles",
    pasos: ["Dockerfile de Apache (ya existe, tarea 2)", "Dockerfile de Nginx (FROM nginx, COPY a /usr/share/nginx/html/)", "docker build de ambas imágenes", "docker run -p 8080:80 (Apache)", "docker run -p 8081:80 (Nginx)"],
    origen: "Clase 6 · práctica de laboratorio",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/practicas/docker apache nginx"
  },
  {
    id: "wad-api-temperaturas",
    curso: "7CM2",
    titulo: "API REST — Conversor de temperaturas (Celsius/Fahrenheit/Kelvin)",
    tipo: "practica",
    entrega: "2026-09-18T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "en-curso",
    descripcion: "Proyecto Spring Boot (api7cm2, com.ipn.mx) con un TemperaturaController (@RestController, /api/temperatura) que expone las 6 conversiones entre Celsius, Fahrenheit y Kelvin, cada una como un @GetMapping que recibe ?valor= y devuelve un ConversionResponse (record con valorOriginal, unidadOriginal, valorRespuesta, unidadRespuesta) envuelto en ResponseEntity.ok(). En la clase del 9 de septiembre (número de clase por confirmar) el profesor hizo dos como ejemplo: Celsius→Fahrenheit (F = C*9/5+32) y Celsius→Kelvin (K = C+273.15). Faltan las otras cuatro. Verificado: el proyecto NO está en el repo — probablemente sigue solo en la carpeta de proyectos de IntelliJ en la máquina. Falta copiarlo/inicializarlo aquí para no perderlo.",
    entregable: "Proyecto Spring Boot con los 6 endpoints de conversión",
    pasos: ["Crear proyecto Spring Boot (Web + DevTools, server.port=8082)", "record ConversionResponse", "Celsius → Fahrenheit (hecho en clase)", "Celsius → Kelvin (hecho en clase)", "Fahrenheit → Celsius: C = (F-32)*5/9", "Fahrenheit → Kelvin: K = (F-32)*5/9+273.15", "Kelvin → Celsius: C = K-273.15", "Kelvin → Fahrenheit: F = (K-273.15)*9/5+32", "Probar los 6 con curl/Postman"],
    origen: "Clase del 9 de septiembre (transcripción sin numerar aún) · lista oficial de tareas (18 sept)",
    ruta: "7CM2 - DES. DE APLIC(WEB CLIENT AND BACKEND/Primer parcial/practicas/api temperaturas"
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
  },

  /* ---------- 7CM4 · Desarrollo de Aplicaciones Móviles Nativas ---------- */
  {
    id: "damn-practica4-numeros",
    curso: "7CM4",
    titulo: "Práctica 4 — Números especiales en Kotlin (primo, Fibonacci, maravilloso, Kaprekar)",
    tipo: "practica",
    entrega: null,
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Del PDF 'DAMN Kotlin Practica4' (operadores y estructuras de control): partiendo del verificador de números primos del profesor, Ejercicio 1 pide agregar si el número es Fibonacci y si es 'número maravilloso' (conjetura de Collatz: par->n/2, impar->3n+1, hasta llegar a 1); Ejercicio 2 pide que, si el número es de 3 o 4 dígitos, se verifique si corresponde a la constante de Kaprekar (495 o 6174). Código, capturas y reporte ya están completos y compilados: AngelFraustoRoblesKotlinNumeros7CM4.pdf (550 KB, generado 12 sept) junto al reporte.html fuente. Commit 4366f62 ya lo subió al repo. Falta solo armar el zip con la nomenclatura de la rúbrica y enviarlo. Sin fecha propia en la lista oficial de tareas; la rúbrica del profesor marca entrega semanal de prácticas de laboratorio, a más tardar el viernes de la semana correspondiente, al correo avionica252@yahoo.com (a reserva de otro medio por confirmar) — confirmar el viernes exacto con el profesor.",
    entregable: "NombreAlumno_KotlinNumeros_7CM4.zip con el reporte PDF + código fuente (carpeta Parcial 1/practicas/Practica 4)",
    pasos: ["Completar esPrimo + esFibonacci + esMaravilloso (Ejercicio 1)", "Completar constante de Kaprekar para 3-4 dígitos (Ejercicio 2)", "Probar con varios números y capturar pantallas", "Redactar reporte (carátula, introducción, desarrollo, conclusiones, bibliografía, código)", "Comprimir con la nomenclatura NombreAlumno_TipoDeTrabajo_Grupo.zip", "Confirmar fecha límite (viernes) con el profesor", "Enviar al correo indicado"],
    origen: "DAMN Kotlin Practica4.pdf (Dr. Alejandro Cifuentes A.) · rúbrica del profesor 2027-1",
    ruta: "7CM4 - DESARROLLO DE APLICACIONES MOVILES/Parcial 1/practicas/Practica 4"
  },
  {
    id: "damn-tarea-5-maravilloso",
    curso: "7CM4",
    titulo: "Tarea 5 — Número maravilloso",
    tipo: "tarea",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Tarea de la plataforma, aparte de la Práctica 4. Confirmado con el usuario: app Android standalone nueva en Java (independiente del proyecto de Practica 4, que es Kotlin; aunque comparte el algoritmo de Collatz), documentada con la estructura de proyectos de la rúbrica (carátula, introducción, desarrollo con capturas y código, conclusiones, bibliografía, carpeta de código). App, capturas y reporte ya completos: AngelFraustoRoblesTarea5Maravilloso7CM4.pdf (307 KB, generado 12 sept), subido al repo en el commit 4366f62. Se debía el viernes 11 de septiembre — se terminó con un día de retraso; falta comprimir con la nomenclatura de la rúbrica y enviar al correo.",
    entregable: "Reporte con la estructura de la rúbrica + carpeta de código comprimida de la app standalone",
    pasos: ["Crear proyecto Android standalone en Java (número maravilloso)", "Probar la app", "Redactar reporte (carátula, introducción, desarrollo, conclusiones, bibliografía)", "Comprimir con la nomenclatura NombreAlumno_TipoDeTrabajo_Grupo.zip", "Enviar al correo indicado"],
    origen: "Plataforma del curso — entrega semanal de los viernes",
    ruta: "7CM4 - DESARROLLO DE APLICACIONES MOVILES/Parcial 1/tareas/Tarea 5"
  },
  {
    id: "damn-tarea-6-kaprekar",
    curso: "7CM4",
    titulo: "Tarea 6 — Constante de Kaprekar",
    tipo: "tarea",
    entrega: "2026-09-11T23:00",
    fechaPorConfirmar: false,
    parcial: 1,
    estado: "hecha",
    descripcion: "Tarea de la plataforma, aparte de la Práctica 4. Confirmado con el usuario: app Android standalone nueva en Java (independiente del proyecto de Practica 4, que es Kotlin; aunque comparte la rutina de Kaprekar), documentada con la estructura de proyectos de la rúbrica (carátula, introducción, desarrollo con capturas y código, conclusiones, bibliografía, carpeta de código). App, capturas y reporte ya completos: AngelFraustoRoblesTarea6Kaprekar7CM4.pdf (274 KB, generado 12 sept), subido al repo en el commit 4366f62. Se debía el viernes 11 de septiembre — se terminó con un día de retraso; falta comprimir con la nomenclatura de la rúbrica y enviar al correo.",
    entregable: "Reporte con la estructura de la rúbrica + carpeta de código comprimida de la app standalone",
    pasos: ["Crear proyecto Android standalone en Java (constante de Kaprekar)", "Probar la app", "Redactar reporte (carátula, introducción, desarrollo, conclusiones, bibliografía)", "Comprimir con la nomenclatura NombreAlumno_TipoDeTrabajo_Grupo.zip", "Enviar al correo indicado"],
    origen: "Plataforma del curso — entrega semanal de los viernes",
    ruta: "7CM4 - DESARROLLO DE APLICACIONES MOVILES/Parcial 1/tareas/Tarea 6"
  }

  /* 7CM3 y 8CM1 todavía no tienen entregas registradas.
     En cuanto salga la primera, se agrega aquí. */
];
