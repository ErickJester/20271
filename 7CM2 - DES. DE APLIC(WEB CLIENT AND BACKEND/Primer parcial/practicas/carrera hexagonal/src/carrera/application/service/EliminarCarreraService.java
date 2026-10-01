package carrera.application.service;

import carrera.application.port.in.EliminarCarreraUseCase;
import carrera.domain.exception.CarreraException;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.AlumnoRepositoryPort;
import carrera.domain.port.out.CarreraRepositoryPort;

public class EliminarCarreraService implements EliminarCarreraUseCase {

    private final CarreraRepositoryPort carreraRepositorio;
    private final AlumnoRepositoryPort alumnoRepositorio;

    public EliminarCarreraService(CarreraRepositoryPort carreraRepositorio,
                                  AlumnoRepositoryPort alumnoRepositorio) {
        this.carreraRepositorio = carreraRepositorio;
        this.alumnoRepositorio = alumnoRepositorio;
    }

    @Override
    public void eliminarCarrera(int idCarrera) {
        Carrera carrera = carreraRepositorio.buscarPorId(idCarrera)
            .orElseThrow(() -> new CarreraException("No existe la carrera con id " + idCarrera));

        int inscritos = alumnoRepositorio.contarInscritosEnCarrera(idCarrera); // pregunta por el puerto
        carrera.verificarPuedeEliminarse(inscritos);                           // regla del dominio

        carreraRepositorio.eliminar(idCarrera);
    }
}
