# Diccionario de Datos — Sistema de Inventario y Gestión de Equipo de Cómputo (ESCOM)

Documento correspondiente al **Avance 1** del proyecto de C704. El profesor autorizó tomar el
proyecto directamente de un libro de la bibliografía del curso; este diseño adapta el caso continuo
de la CPU (*Central Pacific University*) del capítulo 13 de Kendall & Kendall, sustituyendo la
institución por la ESCOM y resolviendo, además del caso base, las extensiones que el propio capítulo
deja como ejercicio (ver [`planteamiento.md`](planteamiento.md)). El resultado son **9 entidades de
negocio** más **2 entidades asociativas** que resuelven las relaciones muchos-a-muchos, con un total
de **11 tablas**.

Este diccionario es la fuente de verdad del avance: el modelo E-R, la representación relacional y el
script SQL deben coincidir atributo por atributo con lo aquí definido.

## Convenciones adoptadas

| Aspecto | Decisión |
|---|---|
| **SGBD objetivo** | PostgreSQL 16 (tipos `SERIAL`, `VARCHAR`, `NUMERIC`, `BOOLEAN`, `DATE`) |
| **Nomenclatura de tablas** | `snake_case`, en singular (`computadora`, no `computadoras`) |
| **Nomenclatura de atributos** | `snake_case`, sin acentos ni caracteres especiales en los identificadores |
| **Claves primarias** | Sustitutas (surrogate), enteras autoincrementales, con prefijo `id_` (equivalente a los códigos/números de inventario que el libro usa como identificador de cada entidad) |
| **Claves primarias compuestas** | Solo en las entidades asociativas, formadas por sus dos claves foráneas |
| **Codificación** | `UTF8`, intercalación `es_MX.UTF-8` |
| **Fechas** | Tipo `DATE` (sin hora); el formato de captura es `AAAA-MM-DD` |
| **Montos** | `NUMERIC(10,2)` en pesos mexicanos (MXN) |

> **Nota sobre PostgreSQL**: el tipo `SERIAL` es azúcar sintáctica de PostgreSQL que crea un
> `INTEGER NOT NULL` asociado a una secuencia. En el diccionario se documenta como `SERIAL` y en el
> script DDL se declara como tal.

---

## Resumen de tablas

| # | Tabla | Tipo | Clave primaria | Descripción | Origen en el libro |
|---|---|---|---|---|---|
| 1 | `edificio_campus` | Catálogo | `id_edificio` | Espacios físicos de la ESCOM donde se ubican los equipos | Caso base (`Campus Location`) |
| 2 | `distribuidor` | Catálogo | `id_distribuidor` | Proveedores a quienes se adquiere el equipo de cómputo | Ejercicios E-3 / E-7 (`Vendor`) |
| 3 | `categoria_software` | Catálogo | `id_categoria` | Clasificación temática de los paquetes de software | Caso base (`Software Category`) |
| 4 | `departamento` | Catálogo | `id_departamento` | Departamento institucional al que pertenece cada experto | Caso base (`Department Codes`) |
| 5 | `experto_soporte` | Catálogo | `id_experto` | Personal de soporte técnico de la institución | Caso base (`Employee`) |
| 6 | `sistema_operativo` | Catálogo | `id_sistema_operativo` | Sistemas operativos instalables en los equipos | Caso base (`Operating System`) |
| 7 | `software` | Maestra | `id_software` | Paquetes de software del inventario institucional | Caso base (`Software Master`) |
| 8 | `computadora` | Maestra | `id_computadora` | Equipos de cómputo que conforman el inventario | Caso base (`Hardware Master`) |
| 9 | `mantenimiento` | Transaccional | `id_mantenimiento` | Historial de servicios realizados a cada equipo | Ejercicios E-4 / E-6 (`Maintenance`) |
| 10 | `instalacion_software` | Asociativa | `id_computadora` + `id_software` | Qué software está instalado en qué equipo y con qué licencia | Caso base (`Hardware-Software`) |
| 11 | `instalacion_so` | Asociativa | `id_computadora` + `id_sistema_operativo` | Qué sistemas operativos están instalados en qué equipo | Ejercicio E-8 |

---

## 1. `edificio_campus`

**Descripción**: catálogo de los edificios, laboratorios y espacios físicos de la ESCOM donde se
localiza el equipo de cómputo. Permite responder consultas de distribución del parque informático por
ubicación. En el libro es la entidad `Campus Location`, que el caso base deja como catálogo mínimo
(código + descripción); aquí se conserva ampliada porque la problemática pide distinguir laboratorios,
cubículos y aulas.

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

**Descripción**: proveedores externos a quienes la institución adquiere el equipo de cómputo. Es la
entidad `Vendor` que los ejercicios E-3 y E-7 del capítulo piden agregar al caso base; los atributos
siguen tal cual la lista que da el ejercicio E-7. `monto_total_compra` y `numero_total_pedidos` son,
a propósito, una **redundancia controlada**: el libro los guarda como columnas ya calculadas en vez de
obligar a recalcular `SUM()`/`COUNT()` sobre `computadora` cada vez que se consultan.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_distribuidor` | SERIAL | — | No | **PK** | Identificador único del distribuidor (`Numero distribuidor`) |
| 2 | `nombre` | VARCHAR | 120 | No | — | Nombre del distribuidor (`Nombre distribuidor`) |
| 3 | `calle` | VARCHAR | 100 | Sí | — | Calle del domicilio (`Calle`) |
| 4 | `ciudad` | VARCHAR | 60 | No | — | Ciudad del domicilio (`Ciudad`) |
| 5 | `estado` | VARCHAR | 60 | No | — | Estado/entidad federativa del domicilio (`Estado`; no confundir con `computadora.estado`, que es la situación operativa de un equipo) |
| 6 | `codigo_postal` | VARCHAR | 10 | Sí | — | Código postal (`Codigo postal`) |
| 7 | `telefono` | VARCHAR | 15 | No | — | Número telefónico (`Numero telefonico`) |
| 8 | `fecha_ultimo_pedido` | DATE | — | Sí | — | Fecha de envío del último pedido (`Fecha envio ultimo pedido`) |
| 9 | `monto_total_compra` | NUMERIC | 12,2 | No | — | Monto total comprado a este distribuidor, en MXN (`Monto total compra distribuidor`) |
| 10 | `numero_total_pedidos` | INTEGER | — | No | — | Número total de pedidos enviados al distribuidor (`Numero total de pedidos enviados al distribuidor`) |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_distribuidor`: `PRIMARY KEY (id_distribuidor)`.
- `CK_distribuidor_monto`: `CHECK (monto_total_compra >= 0)`.
- `CK_distribuidor_pedidos`: `CHECK (numero_total_pedidos >= 0)`.
- `DEFAULT` de `monto_total_compra` = `0` y de `numero_total_pedidos` = `0`.

---

## 3. `categoria_software`

**Descripción**: clasificación temática de los paquetes de software (ofimática, desarrollo, diseño,
etc.). Es la entidad `Software Category` del caso base: Chip la separa en su propio catálogo
"para ahorrar espacio en los archivos maestros" y para poder cambiar la categoría de un paquete sin
tocar todos sus registros.

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

## 4. `departamento`

**Descripción**: catálogo de los departamentos institucionales a los que pertenece cada experto de
soporte. Es la entidad `Department Codes` que aparece en el diagrama final del caso base (figura
E13.3) y que las primeras versiones de este diseño habían omitido, dejando el departamento como texto
libre dentro de `experto_soporte`.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_departamento` | SERIAL | — | No | **PK** | Identificador único del departamento |
| 2 | `nombre` | VARCHAR | 80 | No | UQ | Nombre del departamento (ej. Soporte Técnico) |

**Claves foráneas**: ninguna.

**Restricciones relevantes**

- `PK_departamento`: `PRIMARY KEY (id_departamento)`.
- `UQ_departamento_nombre`: `UNIQUE (nombre)`.

---

## 5. `experto_soporte`

**Descripción**: personal de soporte técnico de la institución (`Employee` en el libro). Cumple dos
funciones en el modelo: es responsable del seguimiento de un paquete de software y es quien ejecuta
los servicios de mantenimiento sobre los equipos. Los atributos siguen la lista del caso base, con
`id_departamento` como clave foránea hacia `departamento` en vez del texto libre que tenía una
versión anterior de este diseño.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_experto` | SERIAL | — | No | **PK** | Identificador único del experto de soporte (`Numero empleado`) |
| 2 | `num_empleado` | VARCHAR | 15 | No | UQ | Número de empleado institucional (`Numero empleado`) |
| 3 | `primer_nombre` | VARCHAR | 60 | No | — | Primer nombre del experto (`Primer nombre experto`) |
| 4 | `apellido_paterno` | VARCHAR | 60 | No | — | Apellido paterno (`Apellido paterno experto`) |
| 5 | `telefono_oficina` | VARCHAR | 15 | Sí | — | Teléfono de oficina (`Telefono oficina`) |
| 6 | `direccion_email` | VARCHAR | 100 | No | UQ | Correo institucional del experto (`Direccion email`) |
| 7 | `id_departamento` | INTEGER | — | No | **FK** | Departamento al que pertenece (`Codigo departamento`) |

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_departamento` | `departamento` | `id_departamento` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_experto_soporte`: `PRIMARY KEY (id_experto)`.
- `UQ_experto_num_empleado`: `UNIQUE (num_empleado)`.
- `UQ_experto_email`: `UNIQUE (direccion_email)`.
- `CK_experto_email`: `CHECK (direccion_email LIKE '%_@_%._%')`.
- `RESTRICT` en la FK: no se puede eliminar un departamento mientras tenga expertos asignados.

---

## 6. `sistema_operativo`

**Descripción**: catálogo de sistemas operativos que pueden instalarse en los equipos (`Operating
System` en el libro). Se separa de `computadora` porque un mismo equipo puede alojar más de un
sistema operativo (arranque dual, ejercicio E-8) y porque el mismo sistema operativo se repite en
cientos de equipos y de paquetes de software.

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

## 7. `software`

**Descripción**: catálogo de paquetes de software institucional (`Software Master` en el libro). Los
atributos siguen la versión final del caso (figura E13.4): además de los datos propios del paquete,
`software` referencia el sistema operativo que requiere y describe el tipo de computadora, la memoria
necesaria, si la licencia es de sitio, el número de copias y el costo del paquete. Los datos de **una
instalación concreta** (clave y vigencia de licencia) no viven aquí, sino en `instalacion_software`,
porque la licencia se adquiere por equipo y no por producto.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_software` | SERIAL | — | No | **PK** | Identificador único del paquete (`Numero inventario software`) |
| 2 | `titulo` | VARCHAR | 100 | No | UQ¹ | Nombre comercial del paquete (`Titulo`) |
| 3 | `version` | VARCHAR | 30 | No | UQ¹ | Versión del paquete (`Numero version`) |
| 4 | `editorial` | VARCHAR | 80 | No | — | Empresa desarrolladora o editora (`Publisher`) |
| 5 | `id_categoria` | INTEGER | — | No | **FK** | Categoría a la que pertenece (`Software Category Code`) |
| 6 | `id_sistema_operativo` | INTEGER | — | No | **FK** | Sistema operativo que requiere (`Operating System Code`) |
| 7 | `tipo_computadora_requerida` | VARCHAR | 30 | Sí | — | Tipo de equipo compatible (`Computer Type`) |
| 8 | `memoria_requerida_gb` | SMALLINT | — | Sí | — | Memoria mínima requerida, en GB (`Memory Required`) |
| 9 | `licencia_sitio` | BOOLEAN | — | No | — | Si la licencia cubre todo el sitio (`Site License`) |
| 10 | `numero_copias` | SMALLINT | — | Sí | — | Copias con licencia (nulo si `licencia_sitio = TRUE`) (`Number Of Copies`) |
| 11 | `costo` | NUMERIC | 10,2 | No | — | Costo del paquete, en MXN (`Software Cost`) |
| 12 | `id_experto` | INTEGER | — | No | **FK** | Experto responsable del paquete (`Employee Number`) |

¹ `titulo` y `version` forman una clave única compuesta.

**Claves foráneas**

| Atributo | Tabla referenciada | Atributo referenciado | ON DELETE | ON UPDATE |
|---|---|---|---|---|
| `id_categoria` | `categoria_software` | `id_categoria` | RESTRICT | CASCADE |
| `id_sistema_operativo` | `sistema_operativo` | `id_sistema_operativo` | RESTRICT | CASCADE |
| `id_experto` | `experto_soporte` | `id_experto` | RESTRICT | CASCADE |

**Restricciones relevantes**

- `PK_software`: `PRIMARY KEY (id_software)`.
- `UQ_software_titulo_version`: `UNIQUE (titulo, version)`.
- `CK_software_memoria`: `CHECK (memoria_requerida_gb IS NULL OR memoria_requerida_gb > 0)`.
- `CK_software_copias`: `CHECK (numero_copias IS NULL OR numero_copias > 0)`.
- `CK_software_costo`: `CHECK (costo >= 0)`.
- `RESTRICT` en las tres FK: no se puede eliminar una categoría, un sistema operativo ni un experto
  mientras existan paquetes de software que dependan de ellos.

---

## 8. `computadora`

**Descripción**: entidad central del inventario, equivalente a `Hardware Master` en el caso de la
CPU. Registra cada equipo con sus características técnicas, su ubicación física, el proveedor que lo
vendió y la vigencia de su garantía. Los atributos marcados con¹ vienen directamente de la figura
E13.1 del libro.

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
| 9 | `capacidad_disco_duro_gb` | INTEGER | — | Sí | — | Capacidad del disco duro principal, en gigabytes |
| 10 | `capacidad_segundo_disco_duro_gb`¹ | INTEGER | — | Sí | — | Capacidad de un segundo disco duro, si lo tiene |
| 11 | `unidad_optica`¹ | VARCHAR | 30 | Sí | — | Unidad óptica instalada (ej. `DVD-RW`; nulo si no tiene) |
| 12 | `fecha_compra` | DATE | — | No | — | Fecha de adquisición del equipo |
| 13 | `costo_adquisicion` | NUMERIC | 10,2 | No | — | Costo de compra del equipo en MXN |
| 14 | `costo_reemplazo`¹ | NUMERIC | 10,2 | Sí | — | Costo estimado de reemplazar el equipo hoy, en MXN |
| 15 | `intervalo_actualizacion_meses`¹ | SMALLINT | — | Sí | — | Cada cuántos meses se planea renovar el equipo |
| 16 | `fecha_fin_garantia` | DATE | — | Sí | — | Fecha de término de la garantía del fabricante |
| 17 | `estado` | VARCHAR | 20 | No | — | Situación operativa actual del equipo |
| 18 | `ubicacion_especifica` | VARCHAR | 60 | Sí | — | Posición dentro del espacio (ej. `Mesa 12, lugar 4`) |
| 19 | `id_edificio` | INTEGER | — | No | **FK** | Espacio físico donde se localiza el equipo |
| 20 | `id_distribuidor` | INTEGER | — | No | **FK** | Proveedor al que se le compró el equipo |

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
- `CK_computadora_costo_reemplazo`: `CHECK (costo_reemplazo IS NULL OR costo_reemplazo >= 0)`.
- `CK_computadora_ram`: `CHECK (memoria_ram_gb IS NULL OR memoria_ram_gb > 0)`.
- `CK_computadora_disco`: `CHECK (capacidad_disco_duro_gb IS NULL OR capacidad_disco_duro_gb > 0)`.
- `CK_computadora_disco2`: `CHECK (capacidad_segundo_disco_duro_gb IS NULL OR capacidad_segundo_disco_duro_gb > 0)`.
- `CK_computadora_intervalo`: `CHECK (intervalo_actualizacion_meses IS NULL OR intervalo_actualizacion_meses > 0)`.
- `CK_computadora_garantia`: `CHECK (fecha_fin_garantia IS NULL OR fecha_fin_garantia >= fecha_compra)`
  — la garantía nunca puede terminar antes de que el equipo se haya comprado.
- `DEFAULT` de `estado` = `'Activo'`.

---

## 9. `mantenimiento`

**Descripción**: bitácora de los servicios de mantenimiento aplicados a cada equipo (`Maintenance` en
el libro, ejercicios E-4 y E-6). Es la única entidad transaccional del modelo: crece con el tiempo y
permite calcular el gasto histórico por equipo y distinguir lo cubierto por garantía de lo pagado por
la institución. Conserva dos atributos que el ejercicio E-6 no pide (`id_experto`, `descripcion`)
porque la problemática exige saber quién dio el servicio, no solo que se dio.

| # | Atributo | Tipo de dato | Long. | Nulo | Clave | Descripción |
|---|---|---|---|---|---|---|
| 1 | `id_mantenimiento` | SERIAL | — | No | **PK** | Identificador único del servicio (`Numero orden mantenimiento`) |
| 2 | `id_computadora` | INTEGER | — | No | **FK** | Equipo al que se le aplicó el mantenimiento (`Numero inventario hardware`) |
| 3 | `id_experto` | INTEGER | — | Sí | **FK** | Experto que realizó el servicio (nulo si fue externo) |
| 4 | `fecha_mantenimiento` | DATE | — | No | — | Fecha en que se realizó el servicio (`Fecha mantenimiento`) |
| 5 | `tipo` | VARCHAR | 20 | No | — | Naturaleza del servicio realizado (`Tipo de mantenimiento`) |
| 6 | `descripcion` | VARCHAR | 250 | No | — | Detalle de las acciones ejecutadas |
| 7 | `costo` | NUMERIC | 10,2 | No | — | Costo del servicio en MXN (`Costo de mantenimiento`) |
| 8 | `cubierto_garantia` | BOOLEAN | — | No | — | Si el servicio lo cubrió la garantía (`Mantenimiento cubierto por garantia`) |

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

## 10. `instalacion_software` *(entidad asociativa)*

**Descripción**: resuelve la relación muchos-a-muchos entre `computadora` y `software` (`Hardware-
Software` en el libro). Un equipo tiene instalados varios paquetes y un paquete está instalado en
varios equipos. Los atributos de licencia por instalación (clave y vigencia) viven aquí, no en
`software`, y son un añadido de este diseño sobre el caso base: son lo que permite responder
*"¿qué computadoras tienen software con licencia vencida?"*.

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

## 11. `instalacion_so` *(entidad asociativa)*

**Descripción**: resuelve la relación muchos-a-muchos entre `computadora` y `sistema_operativo`, la
entidad `Computer Operating System` que pide agregar el ejercicio E-8, necesaria porque un equipo
puede tener configurado arranque dual. Registra en qué partición vive cada sistema y cuál es el que
arranca por omisión.

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
| R4 | *requiere* | `software` → `sistema_operativo` | N:1 | Total del lado software |
| R5 | *es soportado por* | `software` → `experto_soporte` | N:1 | Total del lado software |
| R6 | *recibe* | `computadora` → `mantenimiento` | 1:N | Parcial (un equipo nuevo puede no tener servicios) |
| R7 | *realiza* | `experto_soporte` → `mantenimiento` | 1:N | Parcial (opcional, admite nulo) |
| R8 | *tiene instalado* | `computadora` ↔ `software` | M:N vía `instalacion_software` | Parcial en ambos lados |
| R9 | *ejecuta* | `computadora` ↔ `sistema_operativo` | M:N vía `instalacion_so` | Parcial en ambos lados |
| R10 | *pertenece a* | `experto_soporte` → `departamento` | N:1 | Total del lado experto |

**Orden de creación de tablas** (respeta las dependencias de claves foráneas):

1. `edificio_campus`, `distribuidor`, `categoria_software`, `departamento`, `sistema_operativo`
2. `experto_soporte` *(depende de 4)*
3. `software` *(depende de 3, 5 y 6)*
4. `computadora` *(depende de 1 y 2)*
5. `mantenimiento`, `instalacion_software`, `instalacion_so`

## Consultas que el modelo debe poder responder

Justifican el diseño frente a la problemática planteada y sirven de lista de verificación al validar
el script SQL:

1. Equipos con software cuya licencia ya venció →
   `instalacion_software.fecha_vencimiento_licencia < CURRENT_DATE AND activa = TRUE`.
2. Equipos fuera de garantía → `computadora.fecha_fin_garantia < CURRENT_DATE`.
3. Gasto en mantenimiento de equipos fuera de garantía en el año en curso → unión de
   `mantenimiento` y `computadora` con `cubierto_garantia = FALSE`.
4. Historial completo de servicios de un equipo → `mantenimiento` filtrado por `id_computadora`.
5. Volumen de compra acumulado por proveedor → columnas `monto_total_compra` y
   `numero_total_pedidos` de `distribuidor`, ya calculadas (ver nota de redundancia controlada).
6. Responsable de soporte de cada paquete instalado en un equipo → `instalacion_software` unida con
   `software` y `experto_soporte`.
7. Equipos con arranque dual → `instalacion_so` agrupada por `id_computadora` con `COUNT(*) > 1`.
8. Distribución del parque informático por edificio → conteo de `computadora` agrupado por
   `id_edificio`.
9. Experto y departamento responsables de cada paquete de software → `software` unida con
   `experto_soporte` y `departamento`.

## Pendiente para avances posteriores

El módulo de **autenticación y autorización** (usuarios, roles y permisos del sistema) es obligatorio
en el curso pero se desarrollará más adelante y, conforme a la rúbrica, no se contabiliza dentro de
las entidades de negocio de este avance. Sus tablas se integrarán a este diccionario cuando se
definan.
