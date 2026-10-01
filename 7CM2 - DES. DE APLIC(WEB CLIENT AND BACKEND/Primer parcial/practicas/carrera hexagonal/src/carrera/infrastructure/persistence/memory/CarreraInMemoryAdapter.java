package carrera.infrastructure.persistence.memory;

import carrera.domain.exception.CarreraException;
import carrera.domain.model.Carrera;
import carrera.domain.port.out.CarreraRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

// SEGUNDO ADAPTADOR de salida para el MISMO puerto: guarda en memoria.
// Prueba de que la arquitectura funciona: se cambia la "base de datos"
// sin tocar dominio, casos de uso ni ventana.
public class CarreraInMemoryAdapter implements CarreraRepositoryPort {

    private final Map<Integer, Carrera> datos = new TreeMap<>();
    private int siguienteId = 1;

    @Override
    public synchronized void crear(Carrera c) {
        int id = siguienteId++;
        datos.put(id, new Carrera(id, c.getNombreCarrera(), c.getDescripcionCarrera()));
    }

    @Override
    public synchronized void actualizar(Carrera c) {
        if (!datos.containsKey(c.getIdCarrera())) {
            throw new CarreraException("No existe la carrera con id " + c.getIdCarrera());
        }
        datos.put(c.getIdCarrera(), c);
    }

    @Override
    public synchronized void eliminar(int idCarrera) {
        datos.remove(idCarrera);
    }

    @Override
    public synchronized Optional<Carrera> buscarPorId(int idCarrera) {
        return Optional.ofNullable(datos.get(idCarrera));
    }

    @Override
    public synchronized List<Carrera> listar() {
        return new ArrayList<>(datos.values());
    }
}
