package carrera.application.port.in;

import carrera.domain.model.Carrera;

import java.util.List;

public interface ListarCarrerasUseCase {
    List<Carrera> listarCarreras();
}
