package carrera.domain.exception;

// El dominio no conoce SQLException: los adaptadores de salida convierten
// sus errores tecnicos en esta excepcion, que si es del dominio.
public class PersistenciaException extends RuntimeException {
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
