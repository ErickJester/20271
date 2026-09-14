-- ============================================================
-- Avance 1 -- Sistema de Inventario y Gestion de Equipo de Computo (ESCOM)
-- C704 -- Desarrollo de aplicaciones web cliente y backend (7CM2)
-- Motor objetivo: PostgreSQL 16
-- ============================================================

CREATE DATABASE inventario_equipo_escom
    WITH ENCODING = 'UTF8'
    LC_COLLATE = 'es_MX.UTF-8'
    LC_CTYPE = 'es_MX.UTF-8';

-- Conectarse a la base recien creada antes de continuar (psql):
-- \c inventario_equipo_escom

-- ============================================================
-- 1. Tablas catalogo (sin dependencias)
-- ============================================================

CREATE TABLE edificio_campus (
    id_edificio     SERIAL PRIMARY KEY,
    clave           VARCHAR(10) NOT NULL,
    nombre          VARCHAR(80) NOT NULL,
    tipo_espacio    VARCHAR(20) NOT NULL,
    direccion       VARCHAR(150),
    numero_niveles  SMALLINT,
    activo          BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_edificio_clave UNIQUE (clave),
    CONSTRAINT ck_edificio_tipo CHECK (tipo_espacio IN ('Laboratorio','Cubiculo','Aula','Oficina','Almacen')),
    CONSTRAINT ck_edificio_niveles CHECK (numero_niveles IS NULL OR numero_niveles > 0)
);

CREATE TABLE distribuidor (
    id_distribuidor SERIAL PRIMARY KEY,
    razon_social    VARCHAR(120) NOT NULL,
    rfc             VARCHAR(13) NOT NULL,
    nombre_contacto VARCHAR(100),
    telefono        VARCHAR(15),
    correo          VARCHAR(100),
    direccion       VARCHAR(150),
    fecha_alta      DATE NOT NULL DEFAULT CURRENT_DATE,
    CONSTRAINT uq_distribuidor_rfc UNIQUE (rfc),
    CONSTRAINT ck_distribuidor_rfc CHECK (LENGTH(rfc) BETWEEN 12 AND 13),
    CONSTRAINT ck_distribuidor_correo CHECK (correo IS NULL OR correo LIKE '%_@_%._%')
);

CREATE TABLE categoria_software (
    id_categoria SERIAL PRIMARY KEY,
    nombre       VARCHAR(60) NOT NULL,
    descripcion  VARCHAR(200),
    CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
);

CREATE TABLE experto_soporte (
    id_experto        SERIAL PRIMARY KEY,
    num_empleado      VARCHAR(15) NOT NULL,
    nombre            VARCHAR(60) NOT NULL,
    apellido_paterno  VARCHAR(60) NOT NULL,
    apellido_materno  VARCHAR(60),
    correo            VARCHAR(100) NOT NULL,
    telefono          VARCHAR(15),
    especialidad      VARCHAR(80),
    activo            BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uq_experto_num_empleado UNIQUE (num_empleado),
    CONSTRAINT uq_experto_correo UNIQUE (correo),
    CONSTRAINT ck_experto_correo CHECK (correo LIKE '%_@_%._%')
);

CREATE TABLE sistema_operativo (
    id_sistema_operativo SERIAL PRIMARY KEY,
    nombre               VARCHAR(60) NOT NULL,
    version              VARCHAR(30) NOT NULL,
    arquitectura         VARCHAR(10) NOT NULL,
    fabricante           VARCHAR(80) NOT NULL,
    fecha_fin_soporte    DATE,
    CONSTRAINT uq_so_nombre_version_arq UNIQUE (nombre, version, arquitectura),
    CONSTRAINT ck_so_arquitectura CHECK (arquitectura IN ('32 bits','64 bits','ARM64'))
);

-- ============================================================
-- 2. Tabla maestra software (depende de categoria_software y experto_soporte)
-- ============================================================

CREATE TABLE software (
    id_software    SERIAL PRIMARY KEY,
    titulo         VARCHAR(100) NOT NULL,
    version        VARCHAR(30) NOT NULL,
    editorial      VARCHAR(80) NOT NULL,
    tipo_licencia  VARCHAR(20) NOT NULL,
    id_categoria   INTEGER NOT NULL,
    id_experto     INTEGER NOT NULL,
    CONSTRAINT uq_software_titulo_version UNIQUE (titulo, version),
    CONSTRAINT ck_software_tipo_licencia CHECK (tipo_licencia IN ('Libre','Propietaria','Suscripcion','Educativa','Prueba')),
    CONSTRAINT fk_software_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria_software (id_categoria) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_software_experto FOREIGN KEY (id_experto)
        REFERENCES experto_soporte (id_experto) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ============================================================
-- 3. Tabla maestra computadora (depende de edificio_campus y distribuidor)
-- ============================================================

CREATE TABLE computadora (
    id_computadora       SERIAL PRIMARY KEY,
    num_inventario       VARCHAR(20) NOT NULL,
    numero_serie         VARCHAR(50) NOT NULL,
    marca                VARCHAR(50) NOT NULL,
    modelo               VARCHAR(60) NOT NULL,
    tipo_equipo          VARCHAR(20) NOT NULL,
    procesador           VARCHAR(80),
    memoria_ram_gb       SMALLINT,
    almacenamiento_gb    INTEGER,
    fecha_compra         DATE NOT NULL,
    costo_adquisicion    NUMERIC(10,2) NOT NULL,
    fecha_fin_garantia   DATE,
    estado               VARCHAR(20) NOT NULL DEFAULT 'Activo',
    ubicacion_especifica VARCHAR(60),
    id_edificio          INTEGER NOT NULL,
    id_distribuidor      INTEGER NOT NULL,
    CONSTRAINT uq_computadora_num_inventario UNIQUE (num_inventario),
    CONSTRAINT uq_computadora_numero_serie UNIQUE (numero_serie),
    CONSTRAINT ck_computadora_tipo CHECK (tipo_equipo IN ('Escritorio','Portatil','All-in-One','Servidor')),
    CONSTRAINT ck_computadora_estado CHECK (estado IN ('Activo','En reparacion','Resguardo','Baja')),
    CONSTRAINT ck_computadora_costo CHECK (costo_adquisicion >= 0),
    CONSTRAINT ck_computadora_ram CHECK (memoria_ram_gb IS NULL OR memoria_ram_gb > 0),
    CONSTRAINT ck_computadora_almacenamiento CHECK (almacenamiento_gb IS NULL OR almacenamiento_gb > 0),
    CONSTRAINT ck_computadora_garantia CHECK (fecha_fin_garantia IS NULL OR fecha_fin_garantia >= fecha_compra),
    CONSTRAINT fk_computadora_edificio FOREIGN KEY (id_edificio)
        REFERENCES edificio_campus (id_edificio) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_computadora_distribuidor FOREIGN KEY (id_distribuidor)
        REFERENCES distribuidor (id_distribuidor) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ============================================================
-- 4. Tabla transaccional y entidades asociativas
-- ============================================================

CREATE TABLE mantenimiento (
    id_mantenimiento     SERIAL PRIMARY KEY,
    id_computadora       INTEGER NOT NULL,
    id_experto           INTEGER,
    fecha_mantenimiento  DATE NOT NULL,
    tipo                 VARCHAR(20) NOT NULL,
    descripcion          VARCHAR(250) NOT NULL,
    costo                NUMERIC(10,2) NOT NULL DEFAULT 0,
    cubierto_garantia    BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT ck_mantenimiento_tipo CHECK (tipo IN ('Preventivo','Correctivo','Actualizacion')),
    CONSTRAINT ck_mantenimiento_costo CHECK (costo >= 0),
    CONSTRAINT ck_mantenimiento_garantia_costo CHECK (cubierto_garantia = FALSE OR costo = 0),
    CONSTRAINT fk_mantenimiento_computadora FOREIGN KEY (id_computadora)
        REFERENCES computadora (id_computadora) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_mantenimiento_experto FOREIGN KEY (id_experto)
        REFERENCES experto_soporte (id_experto) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE instalacion_software (
    id_computadora              INTEGER NOT NULL,
    id_software                 INTEGER NOT NULL,
    fecha_instalacion           DATE NOT NULL,
    clave_licencia              VARCHAR(50),
    fecha_vencimiento_licencia  DATE,
    activa                      BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_computadora, id_software),
    CONSTRAINT ck_instalacion_vigencia CHECK (fecha_vencimiento_licencia IS NULL OR fecha_vencimiento_licencia >= fecha_instalacion),
    CONSTRAINT fk_instalacion_sw_computadora FOREIGN KEY (id_computadora)
        REFERENCES computadora (id_computadora) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_instalacion_sw_software FOREIGN KEY (id_software)
        REFERENCES software (id_software) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE instalacion_so (
    id_computadora         INTEGER NOT NULL,
    id_sistema_operativo   INTEGER NOT NULL,
    fecha_instalacion      DATE NOT NULL,
    particion              VARCHAR(30),
    es_principal           BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_computadora, id_sistema_operativo),
    CONSTRAINT fk_instalacion_so_computadora FOREIGN KEY (id_computadora)
        REFERENCES computadora (id_computadora) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_instalacion_so_sistema FOREIGN KEY (id_sistema_operativo)
        REFERENCES sistema_operativo (id_sistema_operativo) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Garantiza que cada computadora tenga a lo sumo un sistema operativo "principal"
-- (indice unico parcial: solo evalua unicidad entre las filas donde es_principal = TRUE)
CREATE UNIQUE INDEX uq_instalacion_so_principal
    ON instalacion_so (id_computadora)
    WHERE es_principal;

-- ============================================================
-- 5. Poblado (DML) -- minimo 3 registros por tabla
-- ============================================================

INSERT INTO edificio_campus (clave, nombre, tipo_espacio, direccion, numero_niveles, activo) VALUES
('LAB-C1', 'Laboratorio de Computo 1', 'Laboratorio', 'Edificio C, planta baja', 1, TRUE),
('LAB-C2', 'Laboratorio de Computo 2', 'Laboratorio', 'Edificio C, primer piso', 1, TRUE),
('CUB-D3', 'Cubiculos Docentes Edificio D', 'Cubiculo', 'Edificio D, segundo piso', 1, TRUE);

INSERT INTO distribuidor (razon_social, rfc, nombre_contacto, telefono, correo, direccion, fecha_alta) VALUES
('Compuequipos del Centro SA de CV', 'CCE010203AB1', 'Laura Jimenez', '4921234567', 'ventas@compuequipos.mx', 'Av. Tecnologico 100, Zacatecas', '2022-01-15'),
('Distribuidora TecnoZac SA de CV', 'DTZ150607XY2', 'Marco Reyes', '4929876543', 'contacto@tecnozac.mx', 'Blvd. Adolfo Lopez Mateos 45, Zacatecas', '2021-08-03'),
('Importadora de Sistemas del Norte SA', 'ISN980512QW3', 'Diana Soto', '4925551234', 'diana.soto@isn.com.mx', 'Carretera Panamericana Km 12, Zacatecas', '2023-03-20');

INSERT INTO categoria_software (nombre, descripcion) VALUES
('Ofimatica', 'Procesadores de texto, hojas de calculo y presentaciones'),
('Desarrollo', 'IDEs, compiladores y herramientas de programacion'),
('Diseno', 'Software de edicion grafica, CAD y multimedia'),
('Seguridad', 'Antivirus y herramientas de proteccion del equipo');

INSERT INTO experto_soporte (num_empleado, nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, activo) VALUES
('EMP-0001', 'Ricardo', 'Alvarez', 'Gomez', 'ricardo.alvarez@escom.ipn.mx', '4921110001', 'Redes y hardware', TRUE),
('EMP-0002', 'Paola', 'Martinez', 'Luna', 'paola.martinez@escom.ipn.mx', '4921110002', 'Software y licenciamiento', TRUE),
('EMP-0003', 'Ivan', 'Delgado', 'Rios', 'ivan.delgado@escom.ipn.mx', '4921110003', 'Sistemas operativos', TRUE);

INSERT INTO sistema_operativo (nombre, version, arquitectura, fabricante, fecha_fin_soporte) VALUES
('Windows', '11 Pro 23H2', '64 bits', 'Microsoft Corporation', '2028-10-10'),
('Ubuntu', '22.04 LTS', '64 bits', 'Canonical Ltd.', '2027-04-01'),
('Windows', '10 Pro 22H2', '64 bits', 'Microsoft Corporation', '2026-10-14');

INSERT INTO software (titulo, version, editorial, tipo_licencia, id_categoria, id_experto) VALUES
('Microsoft Office', '2021', 'Microsoft Corporation', 'Propietaria', 1, 2),
('Visual Studio Code', '1.89', 'Microsoft Corporation', 'Libre', 2, 2),
('AutoCAD', '2024', 'Autodesk Inc.', 'Suscripcion', 3, 2),
('Windows Defender', '4.18', 'Microsoft Corporation', 'Libre', 4, 3);

INSERT INTO computadora (num_inventario, numero_serie, marca, modelo, tipo_equipo, procesador, memoria_ram_gb, almacenamiento_gb, fecha_compra, costo_adquisicion, fecha_fin_garantia, estado, ubicacion_especifica, id_edificio, id_distribuidor) VALUES
('ESCOM-00123', 'SN-DL-7420-001', 'Dell', 'OptiPlex 7420', 'Escritorio', 'Intel Core i5-13500', 16, 512, '2024-02-10', 15800.00, '2027-02-10', 'Activo', 'Mesa 1, lugar 3', 1, 1),
('ESCOM-00124', 'SN-HP-EL800-002', 'HP', 'EliteDesk 800 G9', 'Escritorio', 'Intel Core i7-13700', 32, 1024, '2024-02-10', 21500.00, '2027-02-10', 'Activo', 'Mesa 2, lugar 1', 1, 1),
('ESCOM-00087', 'SN-LN-T14-003', 'Lenovo', 'ThinkPad T14', 'Portatil', 'AMD Ryzen 7 7840U', 16, 512, '2023-06-01', 24900.00, '2025-06-01', 'Activo', 'Resguardo docente', 3, 2);

INSERT INTO mantenimiento (id_computadora, id_experto, fecha_mantenimiento, tipo, descripcion, costo, cubierto_garantia) VALUES
(1, 1, '2025-03-14', 'Preventivo', 'Limpieza interna y verificacion de disipadores', 0.00, TRUE),
(3, 1, '2025-07-02', 'Correctivo', 'Reemplazo de bateria, equipo fuera de garantia', 1450.00, FALSE),
(2, 3, '2025-09-01', 'Actualizacion', 'Actualizacion de BIOS y reinstalacion de sistema operativo', 0.00, TRUE);

INSERT INTO instalacion_software (id_computadora, id_software, fecha_instalacion, clave_licencia, fecha_vencimiento_licencia, activa) VALUES
(1, 1, '2024-02-12', 'MSO-2021-XXXX-0001', '2027-02-12', TRUE),
(1, 2, '2024-02-12', NULL, NULL, TRUE),
(2, 3, '2024-02-15', 'ACAD-2024-YYYY-0002', '2025-02-15', TRUE),
(3, 4, '2023-06-03', NULL, NULL, TRUE);

INSERT INTO instalacion_so (id_computadora, id_sistema_operativo, fecha_instalacion, particion, es_principal) VALUES
(1, 1, '2024-02-10', 'C:', TRUE),
(2, 1, '2024-02-10', 'C:', TRUE),
(3, 3, '2023-06-01', 'C:', TRUE),
(3, 2, '2023-06-01', '/dev/sda2', FALSE);

-- ============================================================
-- 6. Consultas de validacion (evidencia sugerida para el reporte)
-- ============================================================

-- Equipos con software cuya licencia ya vencio
-- SELECT c.num_inventario, s.titulo, i.fecha_vencimiento_licencia
-- FROM instalacion_software i
-- JOIN computadora c ON c.id_computadora = i.id_computadora
-- JOIN software s ON s.id_software = i.id_software
-- WHERE i.activa = TRUE AND i.fecha_vencimiento_licencia < CURRENT_DATE;

-- Gasto en mantenimiento de equipos fuera de garantia
-- SELECT c.num_inventario, SUM(m.costo) AS gasto_total
-- FROM mantenimiento m
-- JOIN computadora c ON c.id_computadora = m.id_computadora
-- WHERE m.cubierto_garantia = FALSE
-- GROUP BY c.num_inventario;
