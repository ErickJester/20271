package carrera.application.service;

import carrera.application.port.in.ObtenerCarreraUseCase;
import carrera.domain.exception.CarreraException;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

public class ObtenerCarreraService implements ObtenerCarreraUseCase {

    private final CarreraRepositoryPort repositorio;

    public ObtenerCarreraService(CarreraRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Carrera obtenerCarrera(int idCarrera) {
        return repositorio.buscarPorId(idCarrera)
            .orElseThrow(() -> new CarreraException("No existe la carrera con id " + idCarrera));
    }
}
