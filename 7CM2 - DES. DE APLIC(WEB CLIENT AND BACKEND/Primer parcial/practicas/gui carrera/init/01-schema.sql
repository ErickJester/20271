CREATE TABLE IF NOT EXISTS Carrera (
    idCarrera          INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombreCarrera      VARCHAR(150) NOT NULL UNIQUE,
    descripcionCarrera VARCHAR(500) NOT NULL
);