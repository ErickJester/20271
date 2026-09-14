# Planteamiento del proyecto — Sistema de Inventario y Gestión de Equipo de Cómputo (UPIIZ)

## Datos generales

- **Institución**: Instituto Politécnico Nacional — Unidad Profesional Interdisciplinaria de Ingeniería, Campus Zacatecas (UPIIZ).
- **Asignatura**: C704 — Desarrollo de aplicaciones web cliente y backend (7CM2).
- **Actividad**: Avance 1 del proyecto de semestre — Diseño e implementación de base de datos.
- **Equipo**: *(pendiente — máximo 4 integrantes)*

## Problemática

Los laboratorios de cómputo y cubículos docentes de la UPIIZ acumulan, a lo largo de cada semestre, un parque de equipo de cómputo cada vez más grande y heterogéneo: computadoras de distintas marcas y modelos, adquiridas en distintas fechas y por distintos proveedores, cada una con su propio sistema operativo y su propio conjunto de paquetes de software instalados. Actualmente no existe un sistema centralizado que permita saber, en un momento dado:

- Qué software está instalado en cada equipo y si su licencia sigue vigente.
- Qué equipos ya superaron su periodo de garantía o requieren actualización.
- El historial de mantenimientos realizados a cada computadora (fecha, tipo, costo, si fue cubierto por garantía).
- A qué proveedor se le compró cada equipo y cuál ha sido el volumen de compras con él.
- Quién es la persona de soporte responsable de cada paquete de software instalado.

Esta información hoy se encuentra dispersa en hojas de cálculo, notas del personal de soporte y facturas físicas, lo que dificulta auditorías de cumplimiento de licenciamiento, la planeación de reemplazos de equipo y el seguimiento de mantenimientos.

## Justificación

Una base de datos relacional permite centralizar el inventario de hardware y software, sus relaciones de instalación, el historial de mantenimiento y los proveedores, eliminando la duplicidad de información y permitiendo consultas que hoy son prácticamente imposibles de hacer a mano (por ejemplo: "¿qué computadoras tienen instalado software cuya licencia ya venció?" o "¿cuánto se ha gastado en mantenimiento de equipos fuera de garantía este año?").

## Propósito y alcance de este avance

Este primer avance cubre únicamente:

1. El planteamiento de la problemática (este documento).
2. La identificación de las entidades del dominio (mínimo 5, sin contar autenticación/autorización).

Quedan para avances posteriores del proyecto: el diccionario de datos completo, el modelo Entidad-Relación, la representación relacional y el script SQL de creación y poblado — así como, más adelante en el curso, el desarrollo del backend, el frontend y el módulo obligatorio de autenticación y autorización (no incluido en el conteo de entidades de este avance).

## Entidades identificadas (8, sin contar autenticación)

| # | Entidad | Descripción |
|---|---|---|
| 1 | **Computadora** | Cada equipo de cómputo del inventario: marca, modelo, fecha de compra, garantía, edificio/campus donde se ubica. |
| 2 | **EdificioCampus** | Los edificios o espacios físicos de la UPIIZ donde se localizan las computadoras. |
| 3 | **SistemaOperativo** | Catálogo de sistemas operativos que pueden estar instalados en una computadora (una PC puede tener más de uno, ej. dual boot). |
| 4 | **Software** | Catálogo de paquetes de software (título, versión, editorial) que pueden instalarse en una o varias computadoras. |
| 5 | **CategoriaSoftware** | Clasificación de los paquetes de software (ofimática, desarrollo, diseño, etc.). |
| 6 | **ExpertoSoporte** | Personal de soporte/mantenimiento responsable de dar seguimiento a cada paquete de software. |
| 7 | **Distribuidor** | Proveedores a quienes se les compra el equipo, con su historial de compras. |
| 8 | **Mantenimiento** | Registro de cada servicio de mantenimiento realizado a una computadora (fecha, tipo, costo, si aplicó garantía). |

Adicionalmente se contemplan dos entidades asociativas para resolver las relaciones muchos-a-muchos: **Computadora–Software** (qué software está instalado en qué equipo) y **Computadora–SistemaOperativo** (un equipo puede tener más de un sistema operativo instalado).

La autenticación y autorización de usuarios del sistema se desarrollará de forma obligatoria más adelante en el curso y no se contabiliza dentro de estas 8 entidades.
