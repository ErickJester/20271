package carrera.application.service;

import carrera.application.port.in.ActualizarCarreraUseCase;
import carrera.domain.exception.CarreraException;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

public class ActualizarCarreraService implements ActualizarCarreraUseCase {

    private final CarreraRepositoryPort repositorio;

    public ActualizarCarreraService(CarreraRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void actualizarCarrera(Carrera carrera) {
        if (carrera.getIdCarrera() == null) {
            throw new CarreraException("Para actualizar se necesita el id de la carrera");
        }
        repositorio.actualizar(carrera);
    }
}
