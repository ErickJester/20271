package carrera.domain.port.out;

import carrera.domain.model.Carrera;

import java.util.List;
import java.util.Optional;

// PUERTO DE SALIDA: lo que la aplicacion NECESITA del exterior.
// Es el "CarreraDAO" de antes, pero como interfaz que habla solo en terminos
// del dominio. Ni SQLException, ni Connection, ni nada de JDBC.
public interface CarreraRepositoryPort {
    void crear(Carrera carrera);

    void actualizar(Carrera carrera);

    void eliminar(int idCarrera);

    Optional<Carrera> buscarPorId(int idCarrera);

    List<Carrera> listar();
}
