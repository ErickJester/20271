package carrera.application.service;

import carrera.application.port.in.ListarCarrerasUseCase;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

import java.util.List;

public class ListarCarrerasService implements ListarCarrerasUseCase {

    private final CarreraRepositoryPort repositorio;

    public ListarCarrerasService(CarreraRepositoryPort repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Carrera> listarCarreras() {
        return repositorio.listar();
    }
}
