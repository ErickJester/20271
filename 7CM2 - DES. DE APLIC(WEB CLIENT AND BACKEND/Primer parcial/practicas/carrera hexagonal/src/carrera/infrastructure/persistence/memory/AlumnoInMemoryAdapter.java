package carrera.infrastructure.persistence.memory;

import carrera.domain.port.out.AlumnoRepositoryPort;

import java.util.HashMap;
import java.util.Map;

// Adaptador en memoria del puerto de alumnos. inscribir() no es parte del
// puerto: es solo una ayuda para armar datos de prueba.
public class AlumnoInMemoryAdapter implements AlumnoRepositoryPort {

    private final Map<Integer, Integer> inscritosPorCarrera = new HashMap<>();

    public void inscribir(int idCarrera) {
        inscritosPorCarrera.merge(idCarrera, 1, Integer::sum);
    }

    @Override
    public int contarInscritosEnCarrera(int idCarrera) {
        return inscritosPorCarrera.getOrDefault(idCarrera, 0);
    }
}
