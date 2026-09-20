-- Los 5 stored procedures del CRUD de Carrera, uno por cada metodo del DAO.
DELIMITER $$

CREATE PROCEDURE sp_crear_carrera(
    IN p_nombre VARCHAR(150),
    IN p_descripcion VARCHAR(500)
)
BEGIN
    INSERT INTO Carrera (nombreCarrera, descripcionCarrera)
    VALUES (p_nombre, p_descripcion);
END$$

CREATE PROCEDURE sp_leer_carrera(
    IN p_id INT
)
BEGIN
    SELECT idCarrera, nombreCarrera, descripcionCarrera
    FROM Carrera
    WHERE idCarrera = p_id;
END$$

CREATE PROCEDURE sp_listar_carreras()
BEGIN
    SELECT idCarrera, nombreCarrera, descripcionCarrera
    FROM Carrera;
END$$

CREATE PROCEDURE sp_actualizar_carrera(
    IN p_id INT,
    IN p_nombre VARCHAR(150),
    IN p_descripcion VARCHAR(500)
)
BEGIN
    UPDATE Carrera
    SET nombreCarrera = p_nombre,
        descripcionCarrera = p_descripcion
    WHERE idCarrera = p_id;
END$$

CREATE PROCEDURE sp_eliminar_carrera(
    IN p_id INT
)
BEGIN
    DELETE FROM Carrera WHERE idCarrera = p_id;
END$$

DELIMITER ;
