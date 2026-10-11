# ApiEventos

API REST de eventos (Spring Boot 4 + JPA + MySQL) reconstruida a partir de las transcripciones de las
clases del proyecto y del zip `GestionEventos.zip` de un compañero (`Primer parcial/GestionEventos.zip`).
Las clases intermedias no se tienen en transcripción; esa parte se tomó del zip, que es código real del
profesor y no un resumen.

## Qué viene de dónde

Los comentarios `29sept` y `zip` en el código marcan el origen de cada ajuste.

| Pieza | Origen |
|---|---|
| Creación del proyecto, base `GestionEventos7CM2`, Lombok, DevTools, validaciones (nombre 4-200, descripción 4-500, fecha obligatoria) | Clase del 29 de septiembre (primer día) |
| Paquetes `com.ipn.mx.evento.{controller,domain,repository,service}`, interfaz e implementación del servicio en el mismo paquete | Zip del compañero |
| `findById` devuelve la entidad o `null`; el `try/catch` de `DataAccessException` está en el controlador; métodos `readAll` y `read` | Zip del compañero |
| `generarReporte` declarado en `EventoService` | Zip del compañero |
| Ruta base `/api/v1/eventos` (el zip del compañero tenía `/api/v1/ApiEventos` + `/eventos`) | Clase del CRUD, parte 2 (transcripción real) |
| Tabla `Evento` y columnas camelCase (`idEvento`, `nombreEvento`...) con `PhysicalNamingStrategyStandardImpl` | Zip del compañero |
| Mensajes de validación, sin el error de copia de la descripción | Zip del compañero |
| Spring Boot 4.1.1, starter `webmvc`, Lombok como procesador de anotaciones | Zip del compañero |
| `create` y `update` con `@Valid` + `BindingResult`; códigos 201 (create), 200 (update), 400, 404, 500 y 204 (delete); mensajes "El evento se creó/actualizó/eliminó satisfactoriamente" | Clase del CRUD del 7 de octubre (transcripción real) |
| `delete` sin comprobar si el evento existe | Clase del CRUD del 7 de octubre; el 404 lo dejó de tarea |
| `@CrossOrigin` | Resumen de IA |
| Swagger: springdoc, propiedades, `paths-to-match=/api/v1/eventos/**` | Clase del CRUD, parte 2 (transcripción real) |
| `EmailService` / `EmailServiceImpl` en `com.ipn.mx.evento.utilerias.service(.impl)` (dentro del paquete `evento`), texto del correo con "duración de 2 días" | Clase del correo y el PDF (transcripción real) |
| Dependencias `mail` e `itextpdf`, endpoint `/eventos/pdf` | Clase del correo y el PDF (resumen y transcripción) |

## Diferencias deliberadas con lo dictado o con el zip

- **Correo**: usuario, contraseña de aplicación y destinatario salen de variables de entorno
  (`MAIL_USER`, `MAIL_PASSWORD`, `MAIL_TO`), no están en el código. Sin `MAIL_TO` no se envía nada y
  el evento se crea igual. Si el envío falla se registra en el log y no tumba el `POST`.
- **PDF**: idéntico al de la clase del 8 de octubre (título Courier 14 azul, encabezados Helvetica 9
  negrita rojo sobre cian, cuerpo Helvetica 10 azul, `printStackTrace` en el `catch`). En el zip
  `generarReporte` está sin escribir; aquí está el de la clase del PDF.
- **Java 21**, no 25 como el zip, porque es el JDK instalado.
- **`nullable = false`** en nombre y descripción (el zip dice `true`) y columna del id `idEvento` (el zip la
  llama `Evento`, parece un descuido).
- **Springdoc 3.1.1**: con Spring Boot 4 hay que usar la línea 3.x; la 2.x es para Spring Boot 3.
- La clase del servicio se llama `EventoServiceImpl`; el zip tiene el typo `EventoSerrviceImpl`.
- `DELETE` responde 204 con cuerpo, como en clase. Un 204 no lleva cuerpo, así que el mensaje de
  "se eliminó" nunca llega al cliente. Como el profesor no comprueba si el evento existe, un id
  inexistente también da 204 (Spring Data 3+ ignora los ids que no encuentra); en clase dio 500.
  La tarea que dejó (404 con mensaje si no existe) no está aplicada.
- Los mensajes de validación son frases completas y el controlador les antepone "El campo 'x'", así que
  se leen duplicados. Se dejó así por fidelidad a las dos fuentes.
- **Puerto**: la API en el 8080, como en clase. Usa el
  MySQL nativo en el 3306 (usuario `root`, contraseña `admin` por defecto, cámbiala con `DB_PASSWORD`).
  El `docker-compose.yml` es una alternativa que publica MySQL en el 3307: arranca la API con `DB_PORT=3307`.

## Lo que sigue sin aparecer

La entidad `Asistente` (la clase 10 mencionó eventos con lista de asistentes), el frontend en Angular,
las pruebas y el despliegue. El paquete `dto` se creó el 29 de septiembre, pero no hay código en él.

## Cómo correrlo

```bash
# Con MySQL nativo (3306) basta crear la base una vez:  CREATE DATABASE GestionEventos7CM2;
docker compose up -d                 # alternativa con Docker: MySQL en el 3307, usar DB_PORT=3307
./mvnw spring-boot:run               # API en http://localhost:8080
```

Swagger: <http://localhost:8080/documentacion/swagger-ui.html>

Para probar el correo, antes de arrancar:

```bash
export MAIL_USER=tu_correo@gmail.com MAIL_PASSWORD=contraseña_de_aplicacion MAIL_TO=destino@gmail.com
```

## Pruebas rápidas

```bash
URL=http://localhost:8080/api/v1/eventos
curl -X POST $URL -H "Content-Type: application/json" -d '{"nombreEvento":"Huawei Day","descripcionEvento":"Charla de Huawei","fechaCreacion":"2026-10-20"}'
curl $URL
curl $URL/1
curl -X PUT $URL/1 -H "Content-Type: application/json" -d '{"nombreEvento":"Huawei Day 2","descripcionEvento":"Cambio","fechaCreacion":"2026-10-21"}'
curl -o reporte.pdf $URL/pdf
curl -X DELETE $URL/1
```

Casos de error que el profesor probó: nombre de menos de 4 caracteres (400), nombre repetido (500), id
inexistente (404).
