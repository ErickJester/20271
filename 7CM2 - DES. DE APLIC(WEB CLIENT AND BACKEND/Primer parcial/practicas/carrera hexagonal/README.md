# Carrera con arquitectura hexagonal

Mismo CRUD de Carrera (GUI Swing + stored procedures), reorganizado en puertos y adaptadores.

```
src/carrera/
  domain/          Carrera (reglas), CarreraException, PersistenciaException,
                   port/out/CarreraRepositoryPort        <- puerto de salida (el viejo DAO como interfaz)
  application/     port/in/*UseCase (5 interfaces)       <- puertos de entrada
                   service/*Service (5 clases)            <- implementan los casos de uso
  infrastructure/  persistence/jdbc/CarreraJdbcAdapter    <- adaptador de salida (CallableStatement + MySQL)
                   persistence/memory/CarreraInMemoryAdapter <- 2do adaptador, mismo puerto
                   ui/VentanaCarrera                      <- adaptador de entrada (Swing)
                   Main                                   <- raiz de composicion
test/carrera/PruebaCasosDeUso.java  <- prueba los casos de uso sin UI ni BD
```

Compilar y probar:

    javac -encoding UTF-8 -d out $(find src test -name "*.java")
    java -cp out carrera.PruebaCasosDeUso

Ejecutar la ventana:

    java -cp out carrera.infrastructure.Main          (en memoria, sin BD)
    java -cp "out;mysql-connector-j-26.7.0.jar" carrera.infrastructure.Main jdbc   (MySQL: docker compose up -d)
