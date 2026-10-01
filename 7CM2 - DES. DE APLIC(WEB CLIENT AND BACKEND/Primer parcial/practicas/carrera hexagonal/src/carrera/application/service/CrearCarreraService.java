package carrera.application.service;

import carrera.application.port.in.CrearCarreraUseCase;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

// Implementa el puerto de entrada y USA el puerto de salida.
// Depende de interfaces, jamas de JDBC ni de Swing (inversion de dependencias).
public class CrearCarreraService implements CrearCarreraUseCase {

    private final CarreraRepositoryPort repositorio;

    public CrearCarreraService(CarreraRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void crearCarrera(Carrera carrera) {
        repositorio.crear(carrera);
    }
}
