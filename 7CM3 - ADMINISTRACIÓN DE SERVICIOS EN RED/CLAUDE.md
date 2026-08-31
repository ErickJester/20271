# ASR — Administración de servicios en red

## Identidad de la materia
- Carpeta del repo: `7CM3 - ADMINISTRACIÓN DE SERVICIOS EN RED` (grupo 7CM3).
- IPN, ESCOM/UPIIZ, Ingeniería en Sistemas Computacionales, plan 2020, semestre VII.
- Tipo: **teórica-práctica / obligatoria**. TEPIC 7.5 · SATCA 6.3. Vigente desde enero 2023.
- Carga: 3.0 h teoría/semana · 1.5 h práctica/semana · 54 h teoría + 27 h práctica + 25 h aprendizaje
  autónomo = 81 h/semestre.
- Rediseñada por la Academia de Sistemas Distribuidos.
- Fuente de verdad: [`administracionServiciosRed_ISC2020.pdf`](administracionServiciosRed_ISC2020.pdf) —
  programa sintético + programa de estudios oficial. Cualquier duda sobre temario, numeración de
  unidades o relación de prácticas se resuelve leyendo ese PDF, **no** inventando contenido.
- Propósito oficial: «Implementa servicios de red de computadoras, con base en las mejores prácticas
  de administración y monitorización.»
- Relaciones: antecedente con *Aplicaciones para comunicaciones en red*; lateral con *Sistemas
  distribuidos*.

## Temario oficial (numeración a respetar punto por punto)

### Unidad I — Fundamentos de los servicios de red (9.0 h con docente + 3.0 h AA)
*Competencia: analiza las características de operatividad de los elementos de una red de servicios
informáticos de acuerdo con los estándares aplicables.*
- 1.1 Servicios de red — 1.1.1 Clasificación · 1.1.2 Características
- 1.2 Análisis de requerimientos para implementar los servicios de red — 1.2.1 Requerimientos de
  software · 1.2.2 Requerimientos de hardware · 1.2.3 Diseño de políticas
- 1.3 Tecnologías de telecomunicaciones — 1.3.1 PDH y SDH · 1.3.2 DWDM · 1.3.3 GSM y GPRS
- 1.4 Ética informática — 1.4.1 Código de ética

### Unidad II — Temas avanzados de conectividad (14.0 h con docente + 6.5 h AA)
*Competencia: construye una infraestructura de servicios de conectividad con base en el control de
tráfico, traducciones de red y redes virtuales.*
- 2.1 Configuración avanzada de conectividad del switch y ruteador — 2.1.1 Balanceo de carga ·
  2.1.2 Alta disponibilidad
- 2.2 Listas de Control de Acceso — 2.2.1 Estándar / extendidas · 2.2.2 De entrada y salida
- 2.3 Traducción de direcciones de red (NAT) — 2.3.1 NAT estática · 2.3.2 NAT dinámica · 2.3.3 PAT
- 2.4 Redes de Área Local Virtuales (VLANs) — 2.4.1 Configuración de puertos troncales ·
  2.4.2 Etiquetado
- 2.5 Redes definidas por software

### Unidad III — SNMP, monitoreo y calidad (12.0 h con docente + 5.0 h AA)
*Competencia: construye una herramienta de software de almacenamiento de información de los
parámetros de servicios en red con base en el protocolo SNMP.*
- 3.1 Administración de la red — 3.1.1 Protocolo de administración de red (SNMP) · 3.1.2 Bitácoras ·
  3.1.3 Sistemas administradores de red
- 3.2 Temas avanzados de calidad de servicio — 3.2.1 Conformación de tráfico · 3.2.2 Servicios
  diferenciados · 3.2.3 Parametrización de los servicios
- 3.3 Monitorización para la administración de redes — 3.3.1 El proceso y principios de
  monitorización · 3.3.2 Recolección, análisis y notificación

### Unidad IV — Implementación de los servicios de red (10.0 h con docente + 7.0 h AA)
*Competencia: mide el nivel de recursos utilizados en la prestación de servicios estandarizados en
red con base en el protocolo SNMP.*
- 4.1 Administración de servicios de alto nivel con base en SNMP — 4.1.1 Hipertexto, transferencia de
  archivos y correo electrónico · 4.1.2 Mensajería instantánea, acceso remoto y Voz/IP ·
  4.1.3 Sistema de archivos de red
- 4.2 Servicios de bajo nivel — 4.2.1 Asignación dinámica de direcciones IP · 4.2.2 Servicios de
  nombres · 4.2.3 Servidor proxy

### Unidad V — Gestión de la seguridad y el desempeño (9.0 h con docente + ~7.0 h AA)
*Competencia: evalúa los niveles de seguridad y desempeño implementados en una red de comunicaciones
con base en políticas, mecanismos y elementos de seguridad.*
- 5.1 Seguridad básica — 5.1.1 Elementos de seguridad · 5.1.2 Tipos de riesgo · 5.1.3 Políticas,
  mecanismos y elementos de seguridad
- 5.2 Resolución de problemas — 5.2.1 Mejorar el desempeño · 5.2.2 Tolerancia a fallos ·
  5.2.3 Recuperación
- 5.3 Auditoría informática — 5.3.1 Objetivos y criterios · 5.3.2 Planeación de la auditoría ·
  5.3.3 Seguimiento y reportes

## Evaluación y proyecto

Estrategia declarada: **Aprendizaje Basado en Problemas**; método de enseñanza deductivo. Portafolio
de evidencias exigido por el programa:

1. Presentación (exposición grupal a partir de indagación documental)
2. Reporte del debate en formato digital
3. Ejercicios y problemas resueltos
4. Reporte del proyecto
5. Reporte de prácticas
6. Evaluación escrita

El **proyecto** del semestre va por fases:
- Fase 1: Análisis de requerimientos del entorno de red
- Fase 2: Diseño del sistema de comunicaciones y disponibilidad
- Fase 3: Diseño de los servicios de redes de computadoras
- Fase 4: Implementación de los sistemas de monitorización de la red

## Relación de prácticas (13, en el Laboratorio de Redes — 27 h)

1. Instalación y configuración de GNS3
2. Balanceo de carga con routers/switches y alta disponibilidad con routers
3. Listas de control de acceso
4. Traducciones de direcciones de red
5. Implementación de SNMP
6. Programación de SNMP
7. Monitoreo de la red
8. Servicios diferenciados
9. Mensajería instantánea
10. Transferencia de archivos
11. Voz sobre IP
12. Tolerancia a fallos
13. Recuperación de la red

> **Nota sobre el PDF**: la tabla de prácticas viene desalineada en el original (la columna de unidades
> temáticas no cuadra fila por fila y llega a mencionar una "unidad VI" que no existe en el temario).
> La correspondencia razonable es: práctica 1 → U I; 2–4 → U II; 5–8 → U III; 9–11 → U IV; 12–13 → U V.
> Confirmar con el profesor antes de asumirla en cualquier entregable.

## Herramientas previstas
- **GNS3** (práctica 1) como emulador de red — se asume trabajo con imágenes de IOS/routers Cisco a lo
  largo de las unidades II–V. Packet Tracer puede servir de respaldo para ACL/NAT/VLAN, pero el
  programa nombra GNS3 explícitamente.
- **SNMP** es la columna vertebral de las unidades III y IV: agente/gestor, MIB, OID, polling y traps.
  La práctica 6 ("Programación de SNMP") implica escribir código, no solo configurar — probablemente
  Python (`pysnmp`) o Net-SNMP.
- Bibliografía básica: Harpreet (Cisco), LaCroix (*Mastering Linux Network Administration*), Molina
  (*Servicios de red e internet*), Stallings (*Foundations of Modern Networking*: SDN, NFV, QoE, IoT,
  Cloud), Limoncelli (*The Practice of System and Network Administration*). Complementaria clave para
  U III–V: Mauro & Schmidt *Essential SNMP*, Lucas *SNMP Mastery*, Bejtlich *The Practice of Network
  Security Monitoring*, Alani *Guide to Cisco Routers Configuration*, Alvarez *QoS for IP/MPLS
  Networks*, Bishop *Computer Security: Art and Science*.

## Reglas para el material de estudio de esta carpeta

Mismas convenciones que el resto del repo (ver el `CLAUDE.md` de `7CM2`):

**Formato**: guías de estudio como HTML autocontenido, publicado como Artifact en claude.ai y
**copiado además a esta carpeta** como archivo local (`unidadN-tema.html`), para que el material viva
en el repo aunque el link del Artifact no se comparta.

**Alcance**: extensa y detallada, no un resumen. Seguir la numeración oficial punto por punto y añadir
aparte:
- Sección de prerrequisitos ("lo que hay que venir estudiado") cuando la unidad lo amerite — en esta
  materia eso significa repasar modelo TCP/IP, direccionamiento IPv4/subnetting y ruteo básico.
- Diagramas propios en SVG inline para lo estructural (jerarquía PDH/SDH, plano de control vs. plano
  de datos en SDN, arquitectura gestor/agente/MIB de SNMP, flujo de evaluación de una ACL, tabla de
  traducción NAT/PAT…).
- Recuadros tipificados: *Del programa oficial* (cita textual del PDF), *Para el examen*, *Trampa
  común*, *En la práctica de laboratorio* (ligar cada tema con la práctica de GNS3 que le toca).
- Banco de preguntas con respuesta modelo, glosario y plan de estudio con fechas.
- Cuando aplique, comandos de configuración reales (IOS y/o Linux) verificados, no pseudocódigo.

**Idioma**: español, salvo términos técnicos que no se traducen (switch, trunk, trap, polling…).

**Diseño del HTML**: seguir `artifact-design`; identidad visual propia por unidad, tema claro y
oscuro, nada de plantillas genéricas.

## Progreso de guías

| Unidad | Archivo | Estado |
|---|---|---|
| I — Fundamentos de los servicios de red | — | Pendiente |
| II — Temas avanzados de conectividad | — | Pendiente |
| III — SNMP, monitoreo y calidad | — | Pendiente |
| IV — Implementación de los servicios de red | — | Pendiente |
| V — Gestión de la seguridad y el desempeño | — | Pendiente |

## Pendientes por confirmar con el profesor
- Ponderación real de cada evidencia del portafolio (el programa las lista, no las pondera).
- Si el proyecto por fases se entrega en equipo y sobre qué escenario de red.
- Correspondencia práctica ↔ unidad (ver nota arriba) y si se usa GNS3, Packet Tracer o equipo físico.
- Lenguaje esperado en la práctica 6 (programación de SNMP).
