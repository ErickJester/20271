package carrera.application.port.in;

import carrera.domain.model.Carrera;

// PUERTO DE ENTRADA: lo que el mundo exterior (GUI, REST...) puede pedirle a la app.
// Un caso de uso = una interfaz (principio de responsabilidad unica).
public interface CrearCarreraUseCase {
    void crearCarrera(Carrera carrera);
}
