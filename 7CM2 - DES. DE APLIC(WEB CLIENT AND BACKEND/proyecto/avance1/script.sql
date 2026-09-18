-- ============================================================
-- Avance 1 -- Sistema de Inventario y Gestion de Equipo de Computo (ESCOM)
-- Web client and backend development frameworks (7CM2)
-- Motor objetivo: MySQL 8
-- ============================================================

CREATE DATABASE inventario_equipo_escom
    CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_spanish_ci;

USE inventario_equipo_escom;

-- ============================================================
-- 1. Tablas catalogo (experto_soporte depende de departamento, creada justo antes)
-- ============================================================

CREATE TABLE edificio_campus (
    id_edificio     INT AUTO_INCREMENT PRIMARY KEY,
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

-- monto_total_compra y numero_total_pedidos son, a proposito, una redundancia controlada:
-- se guardan como columnas ya calculadas en vez de obligarlos a calcularse con SUM()/COUNT()
-- sobre computadora cada vez que se consultan.
CREATE TABLE distribuidor (
    id_distribuidor       INT AUTO_INCREMENT PRIMARY KEY,
    nombre                VARCHAR(120) NOT NULL,
    calle                 VARCHAR(100),
    ciudad                VARCHAR(60) NOT NULL,
    estado                VARCHAR(60) NOT NULL,
    codigo_postal         VARCHAR(10),
    telefono              VARCHAR(15) NOT NULL,
    fecha_ultimo_pedido   DATE,
    monto_total_compra    NUMERIC(12,2) NOT NULL DEFAULT 0,
    numero_total_pedidos  INTEGER NOT NULL DEFAULT 0,
    CONSTRAINT ck_distribuidor_monto CHECK (monto_total_compra >= 0),
    CONSTRAINT ck_distribuidor_pedidos CHECK (numero_total_pedidos >= 0)
);

CREATE TABLE categoria_software (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(60) NOT NULL,
    descripcion  VARCHAR(200),
    CONSTRAINT uq_categoria_nombre UNIQUE (nombre)
);

-- Departamento al que pertenece cada experto de soporte.
CREATE TABLE departamento (
    id_departamento INT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(80) NOT NULL,
    CONSTRAINT uq_departamento_nombre UNIQUE (nombre)
);

-- id_departamento es clave foranea hacia departamento, en vez de texto libre.
CREATE TABLE experto_soporte (
    id_experto        INT AUTO_INCREMENT PRIMARY KEY,
    num_empleado      VARCHAR(15) NOT NULL,
    primer_nombre     VARCHAR(60) NOT NULL,
    apellido_paterno  VARCHAR(60) NOT NULL,
    telefono_oficina  VARCHAR(15),
    direccion_email   VARCHAR(100) NOT NULL,
    id_departamento   INTEGER NOT NULL,
    CONSTRAINT uq_experto_num_empleado UNIQUE (num_empleado),
    CONSTRAINT uq_experto_email UNIQUE (direccion_email),
    CONSTRAINT ck_experto_email CHECK (direccion_email LIKE '%_@_%._%'),
    CONSTRAINT fk_experto_departamento FOREIGN KEY (id_departamento)
        REFERENCES departamento (id_departamento) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE sistema_operativo (
    id_sistema_operativo INT AUTO_INCREMENT PRIMARY KEY,
    nombre               VARCHAR(60) NOT NULL,
    version              VARCHAR(30) NOT NULL,
    arquitectura         VARCHAR(10) NOT NULL,
    fabricante           VARCHAR(80) NOT NULL,
    fecha_fin_soporte    DATE,
    CONSTRAINT uq_so_nombre_version_arq UNIQUE (nombre, version, arquitectura),
    CONSTRAINT ck_so_arquitectura CHECK (arquitectura IN ('32 bits','64 bits','ARM64'))
);

-- ============================================================
-- 2. Tabla maestra software (depende de categoria_software, experto_soporte y sistema_operativo)
-- ============================================================

-- Referencia el sistema operativo requerido y describe el tipo de computadora, la memoria
-- necesaria, si la licencia es de sitio, el numero de copias y el costo del paquete.
CREATE TABLE software (
    id_software                  INT AUTO_INCREMENT PRIMARY KEY,
    titulo                       VARCHAR(100) NOT NULL,
    version                      VARCHAR(30) NOT NULL,
    editorial                    VARCHAR(80) NOT NULL,
    id_categoria                 INTEGER NOT NULL,
    id_sistema_operativo         INTEGER NOT NULL,
    tipo_computadora_requerida   VARCHAR(30),
    memoria_requerida_gb         SMALLINT,
    licencia_sitio               BOOLEAN NOT NULL DEFAULT FALSE,
    numero_copias                SMALLINT,
    costo                        NUMERIC(10,2) NOT NULL DEFAULT 0,
    id_experto                   INTEGER NOT NULL,
    CONSTRAINT uq_software_titulo_version UNIQUE (titulo, version),
    CONSTRAINT ck_software_memoria CHECK (memoria_requerida_gb IS NULL OR memoria_requerida_gb > 0),
    CONSTRAINT ck_software_copias CHECK (numero_copias IS NULL OR numero_copias > 0),
    CONSTRAINT ck_software_costo CHECK (costo >= 0),
    CONSTRAINT fk_software_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria_software (id_categoria) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_software_so FOREIGN KEY (id_sistema_operativo)
        REFERENCES sistema_operativo (id_sistema_operativo) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_software_experto FOREIGN KEY (id_experto)
        REFERENCES experto_soporte (id_experto) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ============================================================
-- 3. Tabla maestra computadora (depende de edificio_campus y distribuidor)
-- ============================================================

CREATE TABLE computadora (
    id_computadora                    INT AUTO_INCREMENT PRIMARY KEY,
    num_inventario                    VARCHAR(20) NOT NULL,
    numero_serie                      VARCHAR(50) NOT NULL,
    marca                             VARCHAR(50) NOT NULL,
    modelo                            VARCHAR(60) NOT NULL,
    tipo_equipo                       VARCHAR(20) NOT NULL,
    procesador                        VARCHAR(80),
    memoria_ram_gb                    SMALLINT,
    capacidad_disco_duro_gb           INTEGER,
    capacidad_segundo_disco_duro_gb   INTEGER,
    unidad_optica                     VARCHAR(30),
    fecha_compra                      DATE NOT NULL,
    costo_adquisicion                 NUMERIC(10,2) NOT NULL,
    costo_reemplazo                   NUMERIC(10,2),
    intervalo_actualizacion_meses     SMALLINT,
    fecha_fin_garantia                DATE,
    estado                            VARCHAR(20) NOT NULL DEFAULT 'Activo',
    ubicacion_especifica              VARCHAR(60),
    id_edificio                       INTEGER NOT NULL,
    id_distribuidor                   INTEGER NOT NULL,
    CONSTRAINT uq_computadora_num_inventario UNIQUE (num_inventario),
    CONSTRAINT uq_computadora_numero_serie UNIQUE (numero_serie),
    CONSTRAINT ck_computadora_tipo CHECK (tipo_equipo IN ('Escritorio','Portatil','All-in-One','Servidor')),
    CONSTRAINT ck_computadora_estado CHECK (estado IN ('Activo','En reparacion','Resguardo','Baja')),
    CONSTRAINT ck_computadora_costo CHECK (costo_adquisicion >= 0),
    CONSTRAINT ck_computadora_costo_reemplazo CHECK (costo_reemplazo IS NULL OR costo_reemplazo >= 0),
    CONSTRAINT ck_computadora_ram CHECK (memoria_ram_gb IS NULL OR memoria_ram_gb > 0),
    CONSTRAINT ck_computadora_disco CHECK (capacidad_disco_duro_gb IS NULL OR capacidad_disco_duro_gb > 0),
    CONSTRAINT ck_computadora_disco2 CHECK (capacidad_segundo_disco_duro_gb IS NULL OR capacidad_segundo_disco_duro_gb > 0),
    CONSTRAINT ck_computadora_intervalo CHECK (intervalo_actualizacion_meses IS NULL OR intervalo_actualizacion_meses > 0),
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
    id_mantenimiento     INT AUTO_INCREMENT PRIMARY KEY,
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

-- principal_uk es una columna generada que solo vale 1 cuando es_principal = TRUE
-- (y NULL en caso contrario); junto con la restriccion UNIQUE de abajo, emula un
-- indice unico parcial: MySQL trata cada NULL como distinto, asi que la unicidad
-- solo se evalua entre las filas donde es_principal es verdadero.
CREATE TABLE instalacion_so (
    id_computadora         INTEGER NOT NULL,
    id_sistema_operativo   INTEGER NOT NULL,
    fecha_instalacion      DATE NOT NULL,
    particion              VARCHAR(30),
    es_principal           BOOLEAN NOT NULL DEFAULT TRUE,
    principal_uk           TINYINT GENERATED ALWAYS AS (CASE WHEN es_principal THEN 1 ELSE NULL END) VIRTUAL,
    PRIMARY KEY (id_computadora, id_sistema_operativo),
    CONSTRAINT uq_instalacion_so_principal UNIQUE (id_computadora, principal_uk),
    CONSTRAINT fk_instalacion_so_computadora FOREIGN KEY (id_computadora)
        REFERENCES computadora (id_computadora) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_instalacion_so_sistema FOREIGN KEY (id_sistema_operativo)
        REFERENCES sistema_operativo (id_sistema_operativo) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- ============================================================
-- 5. Poblado (DML) -- minimo 3 registros por tabla
-- ============================================================

INSERT INTO edificio_campus (clave, nombre, tipo_espacio, direccion, numero_niveles, activo) VALUES
('LAB-C1', 'Laboratorio de Computo 1', 'Laboratorio', 'Edificio C, planta baja', 1, TRUE),
('LAB-C2', 'Laboratorio de Computo 2', 'Laboratorio', 'Edificio C, primer piso', 1, TRUE),
('CUB-D3', 'Cubiculos Docentes Edificio D', 'Cubiculo', 'Edificio D, segundo piso', 1, TRUE);

INSERT INTO distribuidor (nombre, calle, ciudad, estado, codigo_postal, telefono, fecha_ultimo_pedido, monto_total_compra, numero_total_pedidos) VALUES
('Compuequipos del Centro SA de CV', 'Av. Instituto Politecnico Nacional 2508', 'Ciudad de Mexico', 'Ciudad de Mexico', '07738', '5551234567', '2024-02-10', 37300.00, 2),
('Distribuidora TecnoMex SA de CV', 'Blvd. Miguel de Cervantes Saavedra 301', 'Ciudad de Mexico', 'Ciudad de Mexico', '07300', '5559876543', '2023-06-01', 24900.00, 1),
('Importadora de Sistemas del Norte SA', 'Eje Central Lazaro Cardenas 100', 'Ciudad de Mexico', 'Ciudad de Mexico', '11000', '5555551234', NULL, 0.00, 0);

INSERT INTO categoria_software (nombre, descripcion) VALUES
('Ofimatica', 'Procesadores de texto, hojas de calculo y presentaciones'),
('Desarrollo', 'IDEs, compiladores y herramientas de programacion'),
('Diseno', 'Software de edicion grafica, CAD y multimedia'),
('Seguridad', 'Antivirus y herramientas de proteccion del equipo');

INSERT INTO departamento (nombre) VALUES
('Soporte Tecnico'),
('Redes y Telecomunicaciones'),
('Sistemas y Licenciamiento');

INSERT INTO experto_soporte (num_empleado, primer_nombre, apellido_paterno, telefono_oficina, direccion_email, id_departamento) VALUES
('EMP-0001', 'Ricardo', 'Alvarez', '4921110001', 'ricardo.alvarez@escom.ipn.mx', 2),
('EMP-0002', 'Paola', 'Martinez', '4921110002', 'paola.martinez@escom.ipn.mx', 3),
('EMP-0003', 'Ivan', 'Delgado', '4921110003', 'ivan.delgado@escom.ipn.mx', 1);

INSERT INTO sistema_operativo (nombre, version, arquitectura, fabricante, fecha_fin_soporte) VALUES
('Windows', '11 Pro 23H2', '64 bits', 'Microsoft Corporation', '2028-10-10'),
('Ubuntu', '22.04 LTS', '64 bits', 'Canonical Ltd.', '2027-04-01'),
('Windows', '10 Pro 22H2', '64 bits', 'Microsoft Corporation', '2026-10-14');

INSERT INTO software (titulo, version, editorial, id_categoria, id_sistema_operativo, tipo_computadora_requerida, memoria_requerida_gb, licencia_sitio, numero_copias, costo, id_experto) VALUES
('Microsoft Office', '2021', 'Microsoft Corporation', 1, 1, 'Escritorio o Portatil', 4, FALSE, 25, 3200.00, 2),
('Visual Studio Code', '1.89', 'Microsoft Corporation', 2, 2, 'Cualquiera', 2, TRUE, NULL, 0.00, 2),
('AutoCAD', '2024', 'Autodesk Inc.', 3, 1, 'Escritorio', 16, FALSE, 5, 45000.00, 2),
('Windows Defender', '4.18', 'Microsoft Corporation', 4, 1, 'Cualquiera', 1, TRUE, NULL, 0.00, 3);

INSERT INTO computadora (num_inventario, numero_serie, marca, modelo, tipo_equipo, procesador, memoria_ram_gb, capacidad_disco_duro_gb, capacidad_segundo_disco_duro_gb, unidad_optica, fecha_compra, costo_adquisicion, costo_reemplazo, intervalo_actualizacion_meses, fecha_fin_garantia, estado, ubicacion_especifica, id_edificio, id_distribuidor) VALUES
('ESCOM-00123', 'SN-DL-7420-001', 'Dell', 'OptiPlex 7420', 'Escritorio', 'Intel Core i5-13500', 16, 512, NULL, 'DVD-RW', '2024-02-10', 15800.00, 16500.00, 48, '2027-02-10', 'Activo', 'Mesa 1, lugar 3', 1, 1),
('ESCOM-00124', 'SN-HP-EL800-002', 'HP', 'EliteDesk 800 G9', 'Escritorio', 'Intel Core i7-13700', 32, 512, 1024, NULL, '2024-02-10', 21500.00, 22500.00, 48, '2027-02-10', 'Activo', 'Mesa 2, lugar 1', 1, 1),
('ESCOM-00087', 'SN-LN-T14-003', 'Lenovo', 'ThinkPad T14', 'Portatil', 'AMD Ryzen 7 7840U', 16, 512, NULL, NULL, '2023-06-01', 24900.00, 26000.00, 36, '2025-06-01', 'Activo', 'Resguardo docente', 3, 2);

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
