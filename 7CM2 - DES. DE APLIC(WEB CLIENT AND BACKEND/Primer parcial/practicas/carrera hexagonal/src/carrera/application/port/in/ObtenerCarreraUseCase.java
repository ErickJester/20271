package carrera.application.port.in;

import carrera.domain.model.Carrera;

public interface ObtenerCarreraUseCase {
    // lanza CarreraException si no existe
    Carrera obtenerCarrera(int idCarrera);
}
