# Planteamiento del proyecto — Sistema de Inventario y Gestión de Equipo de Cómputo (ESCOM)

## Datos generales

- **Institución**: Instituto Politécnico Nacional — Escuela Superior de Cómputo (ESCOM).
- **Asignatura**: C704 — Desarrollo de aplicaciones web cliente y backend (7CM2).
- **Actividad**: Avance 1 del proyecto de semestre — Diseño e implementación de base de datos.
- **Equipo**: Angel Frausto Robles, Vanesa Rodríguez Verdín Sandoval.

## Fuente del proyecto

El profesor autorizó tomar el proyecto de semestre directamente de un libro de la bibliografía del
curso. Este proyecto adapta el **caso continuo de la CPU** (*Central Pacific University*) del capítulo
13, "Diseño de bases de datos", de Kendall & Kendall, *Análisis y diseño de sistemas* (2011) — el
mismo caso que los autores resuelven a lo largo de 16 episodios siguiendo a los analistas Chip Puller
y Anna Liszt. El Episodio 13 (p. 437) construye, paso a paso, el modelo Entidad-Relación de un
sistema de inventario de equipo de cómputo universitario: exactamente el dominio de este avance. Los
atributos de cada entidad siguen, tan cerca como fue posible, la lista exacta que trae el propio
libro: la figura E13.1 (entidades sin normalizar) para `Computadora`; la figura E13.4 (versión final,
con la cuadrícula de tipos de datos de Microsoft Visio) para `Software` y `ExpertoSoporte`; y los
ejercicios E-6/E-7 para `Mantenimiento` y `Distribuidor`.

La adaptación consiste en:

1. **Sustituir la institución**: *Central Pacific University* (CPU) → Escuela Superior de Cómputo
	 (ESCOM).
2. **Partir del modelo final del caso** (figura E13.3/E13.4 del libro): `Computadora`, `Software`,
	 `SistemaOperativo`, `EdificioCampus`, `ExpertoSoporte` (el "experto de software" del libro),
	 `CategoriaSoftware`, `Departamento` (`Department Codes`, del que depende `ExpertoSoporte`), y la
	 entidad asociativa `Computadora`–`Software`.
3. **Incorporar las extensiones que el propio capítulo plantea como ejercicio** al final (p. 440),
	 en vez de dejarlas sin resolver:
	 - **Ejercicios E-3 y E-7**: agregar la entidad `Distribuidor` (el libro la llama *Vendor*), que
		 garantiza las computadoras, con la lista exacta de atributos que da E-7.
	 - **Ejercicios E-4 y E-6**: agregar la entidad `Mantenimiento` (*Maintenance*), con las
		 reparaciones realizadas a cada computadora.
	 - **Ejercicio E-8**: agregar la entidad asociativa `Computadora`–`SistemaOperativo`, porque una
		 computadora puede tener más de un sistema operativo instalado (arranque dual) y un mismo
		 sistema operativo puede estar en varias computadoras.

Con esas extensiones ya resueltas, el modelo queda con once tablas: las nueve entidades de negocio
descritas más abajo, más las dos entidades asociativas. El diccionario de datos completo, en
[`diccionario-datos.md`](diccionario-datos.md) y en [`avance1/`](avance1/Avance1.tex), documenta para
cada atributo si viene tal cual del libro o si se agregó para la implementación en PostgreSQL (tipos
de dato, longitudes y restricciones, que el libro no especifica a ese nivel de detalle).

## Problemática

Los laboratorios de cómputo y cubículos docentes de la ESCOM acumulan, a lo largo de cada semestre, un parque de equipo de cómputo cada vez más grande y heterogéneo: computadoras de distintas marcas y modelos, adquiridas en distintas fechas y por distintos proveedores, cada una con su propio sistema operativo y su propio conjunto de paquetes de software instalados. Es, punto por punto, la misma problemática que plantea el caso de la CPU: Chip y Anna necesitan saber, en un momento dado:

- Qué software está instalado en cada equipo y si su licencia sigue vigente.
- Qué equipos ya superaron su periodo de garantía o requieren actualización.
- El historial de mantenimientos realizados a cada computadora (fecha, tipo, costo, si fue cubierto por garantía).
- A qué proveedor se le compró cada equipo y cuál ha sido el volumen de compras con él.
- Quién es la persona de soporte responsable de cada paquete de software instalado.

Esta información hoy se encuentra dispersa en hojas de cálculo, notas del personal de soporte y facturas físicas, lo que dificulta auditorías de cumplimiento de licenciamiento, la planeación de reemplazos de equipo y el seguimiento de mantenimientos.

## Justificación

Una base de datos relacional permite centralizar el inventario de hardware y software, sus relaciones de instalación, el historial de mantenimiento y los proveedores, eliminando la duplicidad de información y permitiendo consultas que hoy son prácticamente imposibles de hacer a mano (por ejemplo: "¿qué computadoras tienen instalado software cuya licencia ya venció?" o "¿cuánto se ha gastado en mantenimiento de equipos fuera de garantía este año?"). Es la misma justificación que da el libro: Chip normaliza el modelo precisamente para poder cambiar la versión de un sistema operativo, o la categoría de un software, "una sola vez" en vez de en todos los registros afectados.

## Propósito y alcance de este avance

Este documento cubre el planteamiento de la problemática y la identificación de las entidades del
dominio (mínimo 5, sin contar autenticación/autorización). El resto del Avance 1 que pide la
rúbrica — diccionario de datos completo, modelo Entidad-Relación, representación relacional y script
SQL de creación y poblado, más el reporte en LaTeX — está desarrollado en
[`avance1/`](avance1/Avance1.tex), que es el entregable final de esta actividad.

Quedan para etapas posteriores del curso el desarrollo del backend, el frontend y el módulo
obligatorio de autenticación y autorización (no incluido en el conteo de entidades de este avance).

## Entidades identificadas (9, sin contar autenticación)

| # | Entidad | Descripción |
|---|---|---|
| 1 | **Computadora** | Cada equipo de cómputo del inventario: marca, modelo, fecha de compra, garantía, edificio/campus donde se ubica. |
| 2 | **EdificioCampus** | Los edificios o espacios físicos de la ESCOM donde se localizan las computadoras. |
| 3 | **SistemaOperativo** | Catálogo de sistemas operativos que pueden estar instalados en una computadora (una PC puede tener más de uno, ej. dual boot). |
| 4 | **Software** | Catálogo de paquetes de software (título, versión, editorial, sistema operativo que requiere) que pueden instalarse en una o varias computadoras. |
| 5 | **CategoriaSoftware** | Clasificación de los paquetes de software (ofimática, desarrollo, diseño, etc.). |
| 6 | **Departamento** | Departamentos institucionales a los que pertenece cada experto de soporte. |
| 7 | **ExpertoSoporte** | Personal de soporte/mantenimiento responsable de dar seguimiento a cada paquete de software. |
| 8 | **Distribuidor** | Proveedores a quienes se les compra el equipo, con su historial de compras. |
| 9 | **Mantenimiento** | Registro de cada servicio de mantenimiento realizado a una computadora (fecha, tipo, costo, si aplicó garantía). |

Adicionalmente se contemplan dos entidades asociativas para resolver las relaciones muchos-a-muchos: **Computadora–Software** (qué software está instalado en qué equipo) y **Computadora–SistemaOperativo** (un equipo puede tener más de un sistema operativo instalado).

La autenticación y autorización de usuarios del sistema se desarrollará de forma obligatoria más adelante en el curso y no se contabiliza dentro de estas 9 entidades.
