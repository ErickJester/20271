package carrera.infrastructure;

import carrera.application.service.ActualizarCarreraService;
import carrera.application.service.CrearCarreraService;
import carrera.application.service.EliminarCarreraService;
import carrera.application.service.ListarCarrerasService;
import carrera.domain.port.out.AlumnoRepositoryPort;
import carrera.domain.port.out.CarreraRepositoryPort;
import carrera.infrastructure.persistence.jdbc.AlumnoJdbcAdapter;
import carrera.infrastructure.persistence.jdbc.CarreraJdbcAdapter;
import carrera.infrastructure.persistence.memory.AlumnoInMemoryAdapter;
import carrera.infrastructure.persistence.memory.CarreraInMemoryAdapter;
import carrera.infrastructure.ui.VentanaCarrera;

import javax.swing.SwingUtilities;

// RAIZ DE COMPOSICION: el unico lugar que conoce TODAS las piezas concretas
// y las conecta. Aqui se elige el adaptador; el resto del codigo no se entera.
//
//   java ... carrera.infrastructure.Main            -> en memoria (sin BD)
//   java ... carrera.infrastructure.Main jdbc       -> MySQL con stored procedures
public class Main {

    public static void main(String[] args) {
        boolean jdbc = args.length > 0 && args[0].equalsIgnoreCase("jdbc");
        String url = "jdbc:mysql://localhost:3307/MisCursos";

        CarreraRepositoryPort repositorio = jdbc
            ? new CarreraJdbcAdapter(url, "root", "tu_password")
            : new CarreraInMemoryAdapter();
        AlumnoRepositoryPort alumnos = jdbc
            ? new AlumnoJdbcAdapter(url, "root", "tu_password")
            : new AlumnoInMemoryAdapter();

        SwingUtilities.invokeLater(() -> new VentanaCarrera(
            new CrearCarreraService(repositorio),
            new ActualizarCarreraService(repositorio),
            new EliminarCarreraService(repositorio, alumnos),
            new ListarCarrerasService(repositorio)
        ).setVisible(true));
    }
}
