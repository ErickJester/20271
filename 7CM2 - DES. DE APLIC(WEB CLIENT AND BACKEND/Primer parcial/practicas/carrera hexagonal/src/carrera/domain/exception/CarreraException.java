package carrera.domain.exception;

// Se rompio una regla de negocio (dato invalido, carrera inexistente...).
public class CarreraException extends RuntimeException {
    public CarreraException(String mensaje) {
        super(mensaje);
    }
}
