-- Tabla de alumnos y el procedimiento que usa AlumnoJdbcAdapter.
-- Sin FOREIGN KEY a proposito: la regla "no eliminar carrera con alumnos"
-- la hace cumplir el DOMINIO, no la base de datos.
CREATE TABLE IF NOT EXISTS Alumno (
    idAlumno  INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(150) NOT NULL,
    idCarrera INT NOT NULL
);

DELIMITER $$

CREATE PROCEDURE sp_contar_inscritos_carrera(
    IN p_id INT
)
BEGIN
    SELECT COUNT(*) AS total FROM Alumno WHERE idCarrera = p_id;
END$$

DELIMITER ;
