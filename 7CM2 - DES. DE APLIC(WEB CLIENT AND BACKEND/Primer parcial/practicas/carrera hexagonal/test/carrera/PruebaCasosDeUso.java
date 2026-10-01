package carrera;

import carrera.application.service.*;
import carrera.domain.exception.CarreraException;
import carrera.domain.model.Carrera;
import carrera.infrastructure.persistence.memory.CarreraInMemoryAdapter;

// Prueba sin UI y sin base de datos: justo la promesa de Cockburn.
// Ejecutar con: java -cp out carrera.PruebaCasosDeUso
public class PruebaCasosDeUso {

    static int fallos = 0;

    static void check(String nombre, boolean ok) {
        System.out.println((ok ? "OK    " : "FALLO ") + nombre);
        if (!ok) fallos++;
    }

    public static void main(String[] args) {
        var repo = new CarreraInMemoryAdapter();
        var crear = new CrearCarreraService(repo);
        var actualizar = new ActualizarCarreraService(repo);
        var alumnos = new carrera.infrastructure.persistence.memory.AlumnoInMemoryAdapter();
        var eliminar = new EliminarCarreraService(repo, alumnos);
        var obtener = new ObtenerCarreraService(repo);
        var listar = new ListarCarrerasService(repo);

        crear.crearCarrera(new Carrera(null, "  ISC ", "Ingenieria en Sistemas"));
        crear.crearCarrera(new Carrera(null, "LCD", "Ciencia de Datos"));
        check("crear y listar 2", listar.listarCarreras().size() == 2);
        check("id asignado y nombre recortado", obtener.obtenerCarrera(1).getNombreCarrera().equals("ISC"));

        actualizar.actualizarCarrera(new Carrera(1, "ISC", "Sistemas Computacionales"));
        check("actualizar", obtener.obtenerCarrera(1).getDescripcionCarrera().equals("Sistemas Computacionales"));

        eliminar.eliminarCarrera(2);
        check("eliminar", listar.listarCarreras().size() == 1);

        alumnos.inscribir(1);
        check("no elimina carrera con alumnos", lanza(() -> eliminar.eliminarCarrera(1)));
        check("la carrera con alumnos sigue ahi", listar.listarCarreras().size() == 1);
        check("eliminar inexistente lanza excepcion", lanza(() -> eliminar.eliminarCarrera(99)));

        check("nombre vacio rechazado por el dominio", lanza(() -> new Carrera(null, " ", "x")));
        check("descripcion nula rechazada", lanza(() -> new Carrera(null, "x", null)));
        check("nombre > 150 rechazado", lanza(() -> new Carrera(null, "a".repeat(151), "x")));
        check("obtener inexistente lanza excepcion", lanza(() -> obtener.obtenerCarrera(99)));
        check("actualizar sin id lanza excepcion", lanza(() -> actualizar.actualizarCarrera(new Carrera(null, "a", "b"))));

        System.out.println(fallos == 0 ? "\nTODO BIEN" : "\nFALLOS: " + fallos);
        System.exit(fallos == 0 ? 0 : 1);
    }

    static boolean lanza(Runnable r) {
        try {
            r.run();
            return false;
        } catch (CarreraException e) {
            return true;
        }
    }
}
