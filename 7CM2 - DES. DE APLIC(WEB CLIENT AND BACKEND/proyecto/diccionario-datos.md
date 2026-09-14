# Diccionario de Datos — Sistema de Inventario y Gestión de Equipo de Cómputo (ESCOM)

Documento correspondiente al **Avance 1** del proyecto de C704. Define las 8 entidades de negocio
identificadas en [`planteamiento.md`](planteamiento.md) más las 2 entidades asociativas que resuelven
las relaciones muchos-a-muchos, con un total de **10 tablas**.

Este diccionario es la fuente de verdad del avance: el modelo E-R, la representación relacional y el
script SQL deben coincidir atributo por atributo con lo aquí definido.

## Convenciones adoptadas

| Aspecto | Decisión |
|---|---|
| **SGBD objetivo** | PostgreSQL 16 (tipos `SERIAL`, `VARCHAR`, `NUMERIC`, `BOOLEAN`, `DATE`) |
| **Nomenclatura de tablas** | `snake_case`, en singular (`computadora`, no `computadoras`) |
| **Nomenclatura de atributos** | `snake_case`, sin acentos ni caracteres especiales en los identificadores |
| **Claves primarias** | Sustitutas (surrogate), enteras autoincrementales, con prefijo `id_` |
| **Claves primarias compuestas** | Solo en las entidades asociativas, formadas por sus dos claves foráneas |
| **Codificación** | `UTF8`, intercalación `es_MX.UTF-8` |
| **Fechas** | Tipo `DATE` (sin hora); el formato de captura es `AAAA-MM-DD` |
| **Montos** | `NUMERIC(10,2)` en pesos mexicanos (MXN) |

> **Nota sobre PostgreSQL**: el tipo `SERIAL` es azúcar sintáctica de PostgreSQL que crea un
> `INTEGER NOT NULL` asociado a una secuencia. En el diccionario se documenta como `SERIAL` y en el
> script DDL se declara como tal.

---

## Resumen de tablas

| # | Tabla | Tipo | Clave primaria | Descripción |
|---|---|---|---|---|
| 1 | `edificio_campus` | Catálogo | `id_edificio` | Espacios físicos de la ESCOM donde se ubican los equipos |
| 2 | `distribuidor` | Catálogo | `id_distribuidor` | Proveedores a quienes se adquiere el equipo de cómputo |
| 3 | `categoria_software` | Catálogo | `id_categoria` | Clasificación temática de los paquetes de software |
| 4 | `experto_soporte` | Catálogo | `id_experto` | Personal de soporte técnico de la institución |
| 5 | `sistema_operativo` | Catálogo | `id_sistema_operativo` | Sistemas operativos instalables en los equipos |
| 6 | `software` | Maestra | `id_software` | Paquetes de software del inventario institucional |
| 7 | `computadora` | Maestra | `id_computadora` | Equipos de cómputo que conforman el inventario |
| 8 | `mantenimiento` | Transaccional | `id_mantenimiento` | Historial de servicios realizados a cada equipo |
| 9 | `instalacion_software` | Asociativa | `id_computadora` + `id_software` | Qué software está instalado en qué equipo y con qué licencia |
| 10 | `instalacion_so` | Asociativa | `id_computadora` + `id_sistema_operativo` | Qué sistemas operativos están instalados en qué equipo |

---

## 1. `edificio_campus`

**Descripción**: catálogo de los edificios, laboratorios y espacios físicos de la ESCOM donde se
localiza el equipo de cómputo. Permite responder consultas de distribución del parque informático por
ubicación.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_edificio` | SERIAL | — | No | **PK** | Identificador único del edificio o espacio |
| 2 | `clave` | VARCHAR | 10 | No | UQ | Clave corta institucional del espacio (ej. `LAB-C1`) |
| 3 | `nombre` | VARCHAR | 80 | No | — | Nombre completo del edificio o laboratorio |
| 4 | `tipo_espacio` | VARCHAR | 20 | No | — | Naturaleza del espacio: laboratorio, cubículo, aula, oficina |
| 5 | `direccion` | VARCHAR | 150 | Sí | — | Referencia de ubicación dentro del campus |
| 6 | `numero_niveles` | SMALLINT | — | Sí | — | Cantidad de niveles o pisos del edificio |
| 7 | `activo` | BOOLEAN | — | No | — | Indica si el espacio sigue en uso (`TRUE` por omisión) |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_edificio_campus`: `PRIMARY KEY (id_edificio)`.
- `UQ_edificio_clave`: `UNIQUE (clave)` — no pueden existir dos espacios con la misma clave.
- `CK_edificio_tipo`: `CHECK (tipo_espacio IN ('Laboratorio','Cubiculo','Aula','Oficina','Almacen'))`.
- `CK_edificio_niveles`: `CHECK (numero_niveles IS NULL OR numero_niveles > 0)`.
- `DEFAULT` de `activo` = `TRUE`.

---

## 2. `distribuidor`

**Descripción**: proveedores externos a quienes la institución adquiere el equipo de cómputo. Permite
calcular el volumen de compra acumulado por proveedor y dar seguimiento a garantías con el vendedor.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_distribuidor` | SERIAL | — | No | **PK** | Identificador único del distribuidor |
| 2 | `razon_social` | VARCHAR | 120 | No | — | Nombre legal de la empresa proveedora |
| 3 | `rfc` | VARCHAR | 13 | No | UQ | Registro Federal de Contribuyentes del proveedor |
| 4 | `nombre_contacto` | VARCHAR | 100 | Sí | — | Persona de contacto asignada a la institución |
| 5 | `telefono` | VARCHAR | 15 | Sí | — | Teléfono de contacto a 10 dígitos |
| 6 | `correo` | VARCHAR | 100 | Sí | — | Correo electrónico de contacto comercial |
| 7 | `direccion` | VARCHAR | 150 | Sí | — | Domicilio fiscal del proveedor |
| 8 | `fecha_alta` | DATE | — | No | — | Fecha de registro del proveedor en el sistema |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_distribuidor`: `PRIMARY KEY (id_distribuidor)`.
- `UQ_distribuidor_rfc`: `UNIQUE (rfc)` — el RFC identifica de forma única a cada contribuyente.
- `CK_distribuidor_rfc`: `CHECK (LENGTH(rfc) BETWEEN 12 AND 13)` — 12 posiciones para persona moral,
  13 para persona física.
- `CK_distribuidor_correo`: `CHECK (correo IS NULL OR correo LIKE '%_@_%._%')`.
- `DEFAULT` de `fecha_alta` = `CURRENT_DATE`.

---

## 3. `categoria_software`

**Descripción**: clasificación temática de los paquetes de software (ofimática, desarrollo, diseño,
etc.). Se modela como catálogo aparte para evitar la redundancia y las inconsistencias de capturar la
categoría como texto libre en cada registro de software.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_categoria` | SERIAL | — | No | **PK** | Identificador único de la categoría |
| 2 | `nombre` | VARCHAR | 60 | No | UQ | Nombre de la categoría (ej. Ofimática, Desarrollo) |
| 3 | `descripcion` | VARCHAR | 200 | Sí | — | Explicación del tipo de software que agrupa |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_categoria_software`: `PRIMARY KEY (id_categoria)`.
- `UQ_categoria_nombre`: `UNIQUE (nombre)` — no se admiten categorías duplicadas.

---

## 4. `experto_soporte`

**Descripción**: personal de soporte técnico de la institución. Cumple dos funciones en el modelo: es
responsable del seguimiento de un paquete de software y es quien ejecuta los servicios de
mantenimiento sobre los equipos.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_experto` | SERIAL | — | No | **PK** | Identificador único del experto de soporte |
| 2 | `num_empleado` | VARCHAR | 15 | No | UQ | Número de empleado institucional |
| 3 | `nombre` | VARCHAR | 60 | No | — | Nombre(s) de pila del experto |
| 4 | `apellido_paterno` | VARCHAR | 60 | No | — | Apellido paterno |
| 5 | `apellido_materno` | VARCHAR | 60 | Sí | — | Apellido materno (opcional) |
| 6 | `correo` | VARCHAR | 100 | No | UQ | Correo institucional del experto |
| 7 | `telefono` | VARCHAR | 15 | Sí | — | Teléfono o extensión de contacto |
| 8 | `especialidad` | VARCHAR | 80 | Sí | — | Área de especialidad técnica (redes, hardware, etc.) |
| 9 | `activo` | BOOLEAN | — | No | — | Indica si el experto sigue adscrito al área de soporte |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_experto_soporte`: `PRIMARY KEY (id_experto)`.
- `UQ_experto_num_empleado`: `UNIQUE (num_empleado)`.
- `UQ_experto_correo`: `UNIQUE (correo)`.
- `CK_experto_correo`: `CHECK (correo LIKE '%_@_%._%')`.
- `DEFAULT` de `activo` = `TRUE`.

---

## 5. `sistema_operativo`

**Descripción**: catálogo de sistemas operativos que pueden instalarse en los equipos. Se separa de
`computadora` porque un mismo equipo puede alojar más de un sistema operativo (arranque dual) y
porque el mismo sistema operativo se repite en cientos de equipos.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_sistema_operativo` | SERIAL | — | No | **PK** | Identificador único del sistema operativo |
| 2 | `nombre` | VARCHAR | 60 | No | UQ¹ | Nombre del sistema operativo (ej. Windows, Ubuntu) |
| 3 | `version` | VARCHAR | 30 | No | UQ¹ | Versión o edición (ej. `11 Pro 23H2`, `22.04 LTS`) |
| 4 | `arquitectura` | VARCHAR | 10 | No | UQ¹ | Arquitectura soportada del binario instalado |
| 5 | `fabricante` | VARCHAR | 80 | No | — | Empresa u organización que lo desarrolla |
| 6 | `fecha_fin_soporte` | DATE | — | Sí | — | Fecha en que el fabricante deja de dar soporte |

¹ Los tres atributos forman una clave única compuesta, no tres claves únicas independientes.

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_sistema_operativo`: `PRIMARY KEY (id_sistema_operativo)`.
- `UQ_so_nombre_version_arq`: `UNIQUE (nombre, version, arquitectura)` — evita registrar dos veces la
  misma combinación de producto, versión y arquitectura.
- `CK_so_arquitectura`: `CHECK (arquitectura IN ('32 bits','64 bits','ARM64'))`.

---

## 6. `software`

**Descripción**: catálogo de paquetes de software institucional. Cada paquete pertenece a una
categoría y tiene asignado un experto de soporte responsable de su seguimiento. Los datos propios de
cada **instalación concreta** (clave y vigencia de licencia) no viven aquí, sino en
`instalacion_software`.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_software` | SERIAL | — | No | **PK** | Identificador único del paquete de software |
| 2 | `titulo` | VARCHAR | 100 | No | UQ¹ | Nombre comercial del paquete (ej. Visual Studio Code) |
| 3 | `version` | VARCHAR | 30 | No | UQ¹ | Versión del paquete (ej. `1.89`, `2024`) |
| 4 | `editorial` | VARCHAR | 80 | No | — | Empresa desarrolladora o editora del paquete |
| 5 | `tipo_licencia` | VARCHAR | 20 | No | — | Naturaleza de la licencia del producto |
| 6 | `id_categoria` | INTEGER | — | No | **FK** | Categoría a la que pertenece el paquete |
| 7 | `id_experto` | INTEGER | — | No | **FK** | Experto de soporte responsable del paquete |

¹ `titulo` y `version` forman una clave única compuesta.

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_categoria` | `categoria_software` | `id_categoria` | RESTRICT | CASCADE |
| `id_experto` | `experto_soporte` | `id_experto` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_software`: `PRIMARY KEY (id_software)`.
- `UQ_software_titulo_version`: `UNIQUE (titulo, version)`.
- `CK_software_tipo_licencia`: `CHECK (tipo_licencia IN ('Libre','Propietaria','Suscripcion','Educativa','Prueba'))`.
- `RESTRICT` en ambas claves foráneas: no se puede eliminar una categoría ni un experto mientras
  existan paquetes de software que dependan de ellos.

---

## 7. `computadora`

**Descripción**: entidad central del inventario. Registra cada equipo de cómputo con sus
características técnicas, su ubicación física, el proveedor que lo vendió y la vigencia de su
garantía.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_computadora` | SERIAL | — | No | **PK** | Identificador único del equipo |
| 2 | `num_inventario` | VARCHAR | 20 | No | UQ | Número de inventario de la etiqueta institucional |
| 3 | `numero_serie` | VARCHAR | 50 | No | UQ | Número de serie asignado por el fabricante |
| 4 | `marca` | VARCHAR | 50 | No | — | Marca del equipo (ej. Dell, HP, Lenovo) |
| 5 | `modelo` | VARCHAR | 60 | No | — | Modelo específico del equipo |
| 6 | `tipo_equipo` | VARCHAR | 20 | No | — | Forma física del equipo |
| 7 | `procesador` | VARCHAR | 80 | Sí | — | Descripción del procesador instalado |
| 8 | `memoria_ram_gb` | SMALLINT | — | Sí | — | Memoria RAM instalada, en gigabytes |
| 9 | `almacenamiento_gb` | INTEGER | — | Sí | — | Capacidad total de almacenamiento, en gigabytes |
| 10 | `fecha_compra` | DATE | — | No | — | Fecha de adquisición del equipo |
| 11 | `costo_adquisicion` | NUMERIC | 10,2 | No | — | Costo de compra del equipo en MXN |
| 12 | `fecha_fin_garantia` | DATE | — | Sí | — | Fecha de término de la garantía del fabricante |
| 13 | `estado` | VARCHAR | 20 | No | — | Situación operativa actual del equipo |
| 14 | `ubicacion_especifica` | VARCHAR | 60 | Sí | — | Posición dentro del espacio (ej. `Mesa 12, lugar 4`) |
| 15 | `id_edificio` | INTEGER | — | No | **FK** | Espacio físico donde se localiza el equipo |
| 16 | `id_distribuidor` | INTEGER | — | No | **FK** | Proveedor al que se le compró el equipo |

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_edificio` | `edificio_campus` | `id_edificio` | RESTRICT | CASCADE |
| `id_distribuidor` | `distribuidor` | `id_distribuidor` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_computadora`: `PRIMARY KEY (id_computadora)`.
- `UQ_computadora_num_inventario`: `UNIQUE (num_inventario)`.
- `UQ_computadora_numero_serie`: `UNIQUE (numero_serie)`.
- `CK_computadora_tipo`: `CHECK (tipo_equipo IN ('Escritorio','Portatil','All-in-One','Servidor'))`.
- `CK_computadora_estado`: `CHECK (estado IN ('Activo','En reparacion','Resguardo','Baja'))`.
- `CK_computadora_costo`: `CHECK (costo_adquisicion >= 0)`.
- `CK_computadora_ram`: `CHECK (memoria_ram_gb IS NULL OR memoria_ram_gb > 0)`.
- `CK_computadora_almacenamiento`: `CHECK (almacenamiento_gb IS NULL OR almacenamiento_gb > 0)`.
- `CK_computadora_garantia`: `CHECK (fecha_fin_garantia IS NULL OR fecha_fin_garantia >= fecha_compra)`
  — la garantía nunca puede terminar antes de que el equipo se haya comprado.
- `DEFAULT` de `estado` = `'Activo'`.

---

## 8. `mantenimiento`

**Descripción**: bitácora de los servicios de mantenimiento aplicados a cada equipo. Es la entidad
transaccional del modelo: crece con el tiempo y permite calcular el gasto histórico por equipo y
distinguir lo cubierto por garantía de lo pagado por la institución.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_mantenimiento` | SERIAL | — | No | **PK** | Identificador único del servicio |
| 2 | `id_computadora` | INTEGER | — | No | **FK** | Equipo al que se le aplicó el mantenimiento |
| 3 | `id_experto` | INTEGER | — | Sí | **FK** | Experto que realizó el servicio (nulo si fue externo) |
| 4 | `fecha_mantenimiento` | DATE | — | No | — | Fecha en que se realizó el servicio |
| 5 | `tipo` | VARCHAR | 20 | No | — | Naturaleza del servicio realizado |
| 6 | `descripcion` | VARCHAR | 250 | No | — | Detalle de las acciones ejecutadas |
| 7 | `costo` | NUMERIC | 10,2 | No | — | Costo del servicio en MXN |
| 8 | `cubierto_garantia` | BOOLEAN | — | No | — | Indica si el servicio lo cubrió la garantía |

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_computadora` | `computadora` | `id_computadora` | CASCADE | CASCADE |
| `id_experto` | `experto_soporte` | `id_experto` | SET NULL | CASCADE |

**Restricciones relevantes**

- `PK_mantenimiento`: `PRIMARY KEY (id_mantenimiento)`.
- `CK_mantenimiento_tipo`: `CHECK (tipo IN ('Preventivo','Correctivo','Actualizacion'))`.
- `CK_mantenimiento_costo`: `CHECK (costo >= 0)`.
- `CK_mantenimiento_garantia_costo`: `CHECK (cubierto_garantia = FALSE OR costo = 0)` — si el
  servicio quedó cubierto por la garantía, el costo para la institución debe ser cero.
- `DEFAULT` de `costo` = `0` y de `cubierto_garantia` = `FALSE`.
- `CASCADE` al eliminar la computadora: dar de baja un equipo del inventario elimina su bitácora, que
  carece de sentido sin el equipo.
- `SET NULL` al eliminar el experto: el historial del servicio se conserva aunque el técnico ya no
  esté registrado.

---

## 9. `instalacion_software` *(entidad asociativa)*

**Descripción**: resuelve la relación muchos-a-muchos entre `computadora` y `software`. Un equipo
tiene instalados varios paquetes y un paquete está instalado en varios equipos. Los atributos de
licencia viven aquí porque la licencia se adquiere **por instalación**, no por producto: es lo que
permite responder *"¿qué computadoras tienen software con licencia vencida?"*.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_computadora` | INTEGER | — | No | **PK, FK** | Equipo donde se realizó la instalación |
| 2 | `id_software` | INTEGER | — | No | **PK, FK** | Paquete de software instalado |
| 3 | `fecha_instalacion` | DATE | — | No | — | Fecha en que se instaló el paquete |
| 4 | `clave_licencia` | VARCHAR | 50 | Sí | — | Clave de activación (nula en software libre) |
| 5 | `fecha_vencimiento_licencia` | DATE | — | Sí | — | Vigencia de la licencia (nula si es perpetua) |
| 6 | `activa` | BOOLEAN | — | No | — | Indica si el paquete sigue instalado en el equipo |

**Clave primaria**: compuesta, `PRIMARY KEY (id_computadora, id_software)`.

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_computadora` | `computadora` | `id_computadora` | CASCADE | CASCADE |
| `id_software` | `software` | `id_software` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_instalacion_software`: la clave compuesta impide registrar dos veces el mismo paquete en el
  mismo equipo.
- `CK_instalacion_vigencia`: `CHECK (fecha_vencimiento_licencia IS NULL OR fecha_vencimiento_licencia >= fecha_instalacion)`.
- `DEFAULT` de `activa` = `TRUE`.
- `CASCADE` desde `computadora` y `RESTRICT` desde `software`: se puede dar de baja un equipo con sus
  instalaciones, pero no se puede borrar un paquete del catálogo mientras siga instalado en algún
  equipo.

---

## 10. `instalacion_so` *(entidad asociativa)*

**Descripción**: resuelve la relación muchos-a-muchos entre `computadora` y `sistema_operativo`,
necesaria porque un equipo puede tener configurado arranque dual. Registra en qué partición vive cada
sistema y cuál es el que arranca por omisión.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_computadora` | INTEGER | — | No | **PK, FK** | Equipo donde está instalado el sistema operativo |
| 2 | `id_sistema_operativo` | INTEGER | — | No | **PK, FK** | Sistema operativo instalado |
| 3 | `fecha_instalacion` | DATE | — | No | — | Fecha de instalación del sistema operativo |
| 4 | `particion` | VARCHAR | 30 | Sí | — | Partición o volumen que ocupa (ej. `C:`, `/dev/sda2`) |
| 5 | `es_principal` | BOOLEAN | — | No | — | Indica si es el sistema que arranca por omisión |

**Clave primaria**: compuesta, `PRIMARY KEY (id_computadora, id_sistema_operativo)`.

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_computadora` | `computadora` | `id_computadora` | CASCADE | CASCADE |
| `id_sistema_operativo` | `sistema_operativo` | `id_sistema_operativo` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_instalacion_so`: la clave compuesta impide registrar dos veces el mismo sistema operativo en el
  mismo equipo.
- `UQ_instalacion_so_principal`: índice único parcial
  `CREATE UNIQUE INDEX uq_instalacion_so_principal ON instalacion_so (id_computadora) WHERE es_principal;`
  — garantiza que cada equipo tenga como máximo **un** sistema operativo marcado como principal.
- `DEFAULT` de `es_principal` = `TRUE`.

---

## Relaciones y cardinalidades

Resumen que debe reflejarse tal cual en el modelo E-R y en la representación relacional.

| # | Relación | Entidades | Cardinalidad | Participación |
|---|---|---|---|---|
| R1 | *se ubica en* | `computadora` → `edificio_campus` | N:1 | Total del lado computadora |
| R2 | *se compra a* | `computadora` → `distribuidor` | N:1 | Total del lado computadora |
| R3 | *pertenece a* | `software` → `categoria_software` | N:1 | Total del lado software |
| R4 | *es soportado por* | `software` → `experto_soporte` | N:1 | Total del lado software |
| R5 | *recibe* | `computadora` → `mantenimiento` | 1:N | Parcial (un equipo nuevo puede no tener servicios) |
| R6 | *realiza* | `experto_soporte` → `mantenimiento` | 1:N | Parcial (opcional, admite nulo) |
| R7 | *tiene instalado* | `computadora` ↔ `software` | M:N vía `instalacion_software` | Parcial en ambos lados |
| R8 | *ejecuta* | `computadora` ↔ `sistema_operativo` | M:N vía `instalacion_so` | Parcial en ambos lados |

**Orden de creación de tablas** (respeta las dependencias de claves foráneas):

1. `edificio_campus`, `distribuidor`, `categoria_software`, `experto_soporte`, `sistema_operativo`
2. `software` *(depende de 3 y 4)*
3. `computadora` *(depende de 1 y 2)*
4. `mantenimiento`, `instalacion_software`, `instalacion_so`

## Consultas que el modelo debe poder responder

Justifican el diseño frente a la problemática planteada y sirven de lista de verificación al validar
el script SQL:

1. Equipos con software cuya licencia ya venció →
   `instalacion_software.fecha_vencimiento_licencia < CURRENT_DATE AND activa = TRUE`.
2. Equipos fuera de garantía → `computadora.fecha_fin_garantia < CURRENT_DATE`.
3. Gasto en mantenimiento de equipos fuera de garantía en el año en curso → unión de
   `mantenimiento` y `computadora` con `cubierto_garantia = FALSE`.
4. Historial completo de servicios de un equipo → `mantenimiento` filtrado por `id_computadora`.
5. Volumen de compra acumulado por proveedor → suma de `computadora.costo_adquisicion` agrupada por
   `id_distribuidor`.
6. Responsable de soporte de cada paquete instalado en un equipo → `instalacion_software` unida con
   `software` y `experto_soporte`.
7. Equipos con arranque dual → `instalacion_so` agrupada por `id_computadora` con `COUNT(*) > 1`.
8. Distribución del parque informático por edificio → conteo de `computadora` agrupado por
   `id_edificio`.

## Pendiente para avances posteriores

El módulo de **autenticación y autorización** (usuarios, roles y permisos del sistema) es obligatorio
en el curso pero se desarrollará más adelante y, conforme a la rúbrica, no se contabiliza dentro de
las entidades de negocio de este avance. Sus tablas se integrarán a este diccionario cuando se
definan.
