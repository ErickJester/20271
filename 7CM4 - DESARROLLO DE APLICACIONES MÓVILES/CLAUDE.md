# DAMN — Desarrollo de Aplicaciones Móviles Nativas

## Identidad de la materia
- Carpeta del repo: `7CM4 - DESARROLLO DE APLICACIONES MÓVILES` (grupo **7CM4**).
- IPN, ESCOM/UPIIZ, Ingeniería en Sistemas Computacionales, plan 2020, semestre VII.
- Área terminal y de integración. Tipo: **teórica-práctica / obligatoria**. TEPIC 7.5 · SATCA 6.3.
  Vigente desde enero 2023.
- Carga: 3.0 h teoría/semana · 1.5 h práctica/semana · 54 h teoría + 27 h práctica + 24 h aprendizaje
  autónomo = 81 h/semestre.
- Rediseñada por la Academia de Ciencias Sociales (según el programa oficial).
- Propósito oficial: «Desarrolla aplicaciones móviles nativas escalables, seguras y confiables con
  base en los componentes de la plataforma de desarrollo Android alimentada por fuentes de datos
  locales y remotos.»
- Relaciones: antecedente con *Tecnologías para el desarrollo de aplicaciones web* y *Bases de
  datos*; lateral con *Administración de servicios en red* (7CM3) y *Trabajo Terminal I*.

## Fuentes de verdad
1. [`desarrolloAplicacionesMovilesNativas_ISC2020.pdf`](desarrolloAplicacionesMovilesNativas_ISC2020.pdf)
   — programa sintético + programa de estudios oficial del IPN. Temario, numeración de unidades y
   relación de prácticas se resuelven leyendo este PDF, **no** inventando contenido.
2. [`DAMN Rubrica 2027_1.pdf`](DAMN%20Rubrica%202027_1.pdf) — rúbrica del profesor para el semestre
   2027-1. Manda en todo lo operativo (entregas, nomenclatura, ponderación, plataformas). Ante
   conflicto con el PDF oficial, para logística gana la rúbrica; para temario gana el programa
   oficial.

## Temario oficial (numeración a respetar punto por punto)

### Unidad I — Aspectos básicos en el desarrollo de aplicaciones móviles (6.0 h T + 2.0 h P + 2.5 h AA)
*Competencia: identifica los elementos de las aplicaciones móviles con base en el ecosistema móvil.*
- 1.1 Historia del dispositivo móvil
- 1.2 Elementos de las aplicaciones móviles (ecosistema móvil)
- 1.3 Tipos de aplicaciones móviles — 1.3.1 Web · 1.3.2 Nativas · 1.3.3 Híbridas
- 1.4 Ecosistema Android — 1.4.1 Arquitectura de Android · 1.4.2 Niveles de API · 1.4.3 Entornos de
  desarrollo · 1.4.4 Emuladores Android

### Unidad II — Estructura y componentes de la interfaz de usuario (5.5 h T + 5.0 h P + 5.0 h AA)
*Competencia: distingue los elementos involucrados en el desarrollo de una aplicación móvil a partir
de la API de Android.*
- 2.1 Fundamentos del lenguaje
- 2.2 Estructura de la aplicación
- 2.3 Vistas, distribución de componentes (layout) y temas
- 2.4 Formas de diseño de la interfaz de usuario — 2.4.1 Asistente · 2.4.2 XML · 2.4.3 Código
- 2.5 Actividades, intents, fragmentos

### Unidad III — Almacenamiento y manejo de datos (6.0 h T + 6.0 h P + 6.0 h AA)
*Competencia: describe los diferentes mecanismos de almacenamiento y manejo de datos para
aplicaciones móviles de acuerdo con la API de Android.*
- 3.1 Permisos
- 3.2 Preferencias compartidas (shared preferences)
- 3.3 Archivos — 3.3.1 Texto plano · 3.3.2 XML · 3.3.3 JSON
- 3.4 Bases de datos — 3.4.1 Motor de datos · 3.4.2 Bases de datos relacionales · 3.4.3 Mapeo
  objeto-relacional · 3.4.4 Base de datos NoSQL
- 3.5 Proveedores de contenido

### Unidad IV — Servicios y comunicaciones (7.5 h T + 6.5 h P... [ver nota] + AA)
*Competencia: desarrolla aplicaciones móviles a partir de los sensores del dispositivo, los servicios
web y de la nube.*
- 4.1 Sensores — 4.1.1 Bluetooth · 4.1.2 Puerto USB · 4.1.3 Geolocalización · 4.1.4 Acelerómetro ·
  4.1.5 Cámara y micrófono · 4.1.6 Giroscopio
- 4.2 Servicios — 4.2.1 Servicios web · 4.2.2 Arquitectura orientada a servicios (SOA) · 4.2.3 RESTful API
- 4.3 Servicios en la nube — 4.3.1 NoSQL (Cloud Firestore) · 4.3.2 Autenticación · 4.3.3 Cloud
  Storage · 4.3.4 Realtime Database · 4.3.5 Cloud Messaging
- 4.4 Notificaciones push

### Unidad V — Publicación y API's (6.0 h T + 4.0 h P + AA)
*Competencia: ejemplifica la forma de publicar una aplicación en la tienda de Google con base en las
políticas establecidas.*
- 5.1 Tienda de aplicaciones Android (Play Store)
- 5.2 Publicación de la aplicación — 5.2.1 Creación del APK · 5.2.2 Firma de desarrollador · 5.2.3 Publicidad
- 5.3 Interfaz de programación de aplicaciones — 5.3.1 Realidad aumentada · 5.3.2 QR · 5.3.3 Otras API
- 5.4 Vestibles (wearables) — 5.4.1 Reloj inteligente · 5.4.2 Pulseras inteligentes

## Evaluación (rúbrica del profesor, 2027-1)
- **Tareas / Laboratorios 10% · Proyecto 40% · Examen 50%.**
- Asistencia mínima 80% para aprobar. Escolaridad presencial; inasistencia se justifica con receta
  IMSS o ISSSTE.
- Tareas y prácticas de laboratorio: entrega **semanal y puntual, a más tardar el viernes** de la
  semana correspondiente.
- Reportes y archivos de proyecto: en las **fechas indicadas de cada examen parcial**.
- Todo se envía a **avionica252@yahoo.com** (a reserva de otros medios por confirmar).

### Nomenclatura obligatoria de entregas
`NombreDeAlumno_TipoDeTrabajo_Grupo.zip` — p. ej. `BenitoJuarez_TareaUno_7CM4.zip`.

### Documentación de proyectos (estructura exigida)
1. **Carátula**: escuela, asignatura, alumno, tema, fecha.
2. **Introducción**: breve, concisa, con objetivos.
3. **Desarrollo**: descripción detallada paso a paso, con diagramas, imágenes y código básico.
4. **Conclusiones**: personales y objetivas.
5. **Bibliografía**: documentación de las aplicaciones usadas.
6. **Código**: carpeta comprimida con el software necesario para ejecutar correctamente cada app.

## Relación de prácticas (9, en laboratorio de cómputo — 27 h)

| # | Práctica | Unidad |
|---|---|---|
| 1 | Instalación y funcionamiento de los entornos móviles | I |
| 2 | Aplicación móvil básica | II |
| 3 | Aplicaciones nativas | II |
| 4 | Manejo de archivos en Android | III |
| 5 | Consulta de base de datos vía API's | III |
| 6 | Manejo de sensores del dispositivo móvil | IV |
| 7 | Desarrollo de una aplicación que consuma servicios de la nube | IV |
| 8 | Publicación de la aplicación en la tienda de Android | V |
| 9 | Aplicación para un vestible | V |

## Estructura de carpetas de esta materia

```
Parcial 1/   proyecto/  practicas/  tareas/
Parcial 2/   proyecto/  practicas/  tareas/
Parcial 3/   proyecto/  practicas/  tareas/
```

Mapeo **tentativo** unidad ↔ parcial (confirmar con el profesor):
- **Parcial 1** → Unidades I–II · prácticas 1–3.
- **Parcial 2** → Unidades III–IV · prácticas 4–7.
- **Parcial 3** → Unidad V · prácticas 8–9 + cierre de proyecto.

Cada `proyecto/` guarda el reporte y el código del entregable de proyecto de ese parcial; `practicas/`
los reportes + código de las prácticas de laboratorio; `tareas/` las tareas semanales.

## Plataformas, lenguajes y hardware previstos
- **Android Studio** + bibliotecas + AVD (Virtual Devices) es la plataforma central. La rúbrica
  también admite NetBeans, Xamarin, IntelliJ, AIDE, Flutter/FlutterFlow, React Native, App Inventor.
- Lenguajes: **Java, Kotlin**, C++, XML.
- Nube: **Firebase** (Firestore, Authentication, Cloud Storage, Realtime Database, Cloud Messaging) y
  Google Cloud Platform para las APIs de la Unidad IV.
- Hardware para prácticas de sensores / vestibles: Arduino Nano/Mini/Micro/Uno, Raspberry Pi 3,
  Bluetooth HC-05/HC-06, Wi-Fi, lentes de RV económicos, etiquetas NFC, componentes electrónicos
  básicos.

## Bibliografía
Oficial (programa IPN):
- Blake, G. & Laird, D. (2018). *Android: Programming Android Database Applications for the Enterprise*.
- Griffiths, D. (2017). *Head First Android Development: A Brain-Friendly Guide*. O'Reilly.
- Kumar, A. (2018). *Android apps with Firebase*. Packt.
- Chandra, D. (2017). *NoSQL Database for Storage and Retrieval Data in Cloud*. Chapman & Hall/CRC.
- Tomás et al. (2016). *Dispositivos Wearable, Visión Artificial, Google Glass y Android TV*. Marcombo.
- Tomás et al. (2017). *El gran libro de Android Avanzado*. Marcombo.
- Recursos: developer.android.com/guide · firebase.google.com/docs · console.cloud.google.com/apis

Recomendada por el profesor (rúbrica):
- Salvador Gómez Oliver — sgoliver.net/blog/curso-de-programacion-android + *Curso de Programación
  Android* (Kindle, **recomendado**).
- J. E. Amaro Soriano — *El gran libro de programación avanzada con Android* (Alfaomega) y *Android:
  programación de dispositivos móviles a través de ejemplos* (Marcombo).
- Jesús Tomás Gironés — *El gran libro de Android* (7ª ed., Marcombo).
- Joshua J. Drake — *Android Hacker's Handbook* (Wiley).

## Reglas para el material de estudio de esta carpeta
Mismas convenciones que el resto del repo (ver `CLAUDE.md` de `7CM2`):
- **Formato**: guías de estudio como HTML autocontenido, publicado como Artifact y **copiado además a
  esta carpeta** como `unidadN-tema.html`.
- **Alcance**: extensa y detallada, no un resumen. Seguir la numeración oficial punto por punto.
  Añadir prerrequisitos (POO en Java/Kotlin, XML, HTTP/REST, SQL), diagramas propios en SVG inline
  (arquitectura de Android, ciclo de vida de Activity/Fragment, flujo de un Intent, esquema
  Room/SQLite, gestor de sensores, arquitectura Firebase) y recuadros tipificados: *Del programa
  oficial*, *Para el examen*, *Trampa común*, *En la práctica de laboratorio*.
- Código real verificado (Kotlin/Java + XML), no pseudocódigo.
- **Idioma**: español, salvo términos técnicos (layout, intent, fragment, activity, listener…).
- **Diseño del HTML**: seguir `artifact-design`; identidad visual propia por unidad, tema claro y
  oscuro.

## Progreso de guías

| Unidad | Archivo | Estado |
|---|---|---|
| I — Aspectos básicos | — | Pendiente |
| II — Interfaz de usuario | — | Pendiente |
| III — Almacenamiento y manejo de datos | — | Pendiente |
| IV — Servicios y comunicaciones | — | Pendiente |
| V — Publicación y API's | — | Pendiente |

## Pendientes por confirmar con el profesor
- Mapeo definitivo unidad ↔ parcial y qué prácticas/entregables caen en cada examen.
- Tema y alcance del proyecto del semestre; si es individual o en equipo.
- Fechas de los tres exámenes parciales y de las entregas de proyecto.
- Plataforma obligatoria (¿Android Studio nativo obligado, o se acepta Flutter/React Native?).
- Lenguaje esperado (Java vs. Kotlin) en prácticas y proyecto.
- Confirmar el correo de entrega y si hay medio alterno (Classroom, Teams, etc.).
- En el PDF oficial la Unidad IV trae los subtotales de horas prácticas desalineados; confirmar carga
  real.
