# Repaso de Backend - Primer Parcial (DAO y DTO)

## 1. DTO (Data Transfer Object)
- **¿Qué es?** Es un objeto plano que se usa exclusivamente para transportar datos de un lugar a otro (por ejemplo, del backend al frontend).
- **Para programadores de C:** Es el equivalente exacto a crear un `struct`, pero en Java lo hacemos con una `class` donde escondemos los atributos (`private`) y creamos funciones para leerlos o escribirlos (`getters` y `setters`).
- **¿Por qué usarlo?** Para evitar enviar datos sensibles por internet (como contraseñas o CURPs) y para no hacer lenta la red mandando información que la pantalla no necesita.
- **Ejemplo:** Si tu tabla (Entidad) tiene 20 columnas, pero la página web solo necesita un desplegable con el ID y el Nombre, creas un DTO que solo tenga esos dos atributos.

## 2. Diferencia entre Entidad, DAO y DTO
- **Entidad (Modelo):** Representa la tabla exacta de la base de datos (con todas sus columnas).
- **DAO (Data Access Object):** Es el "trabajador". Es la única clase que sabe escribir SQL, sabe de conexiones y bases de datos. Transforma filas de la base en objetos.
- **DTO:** Es el "mensajero". Toma partes de la Entidad y las lleva seguras al cliente web o móvil.

## 3. Notas sobre el PDF de la Clase (CRUD y DAO)
Revisamos las reglas estrictas de la clase para el archivo `CarreraDAO.java`:

- **La "carrera de relevos" (Excepciones):**
  El DAO no debe usar bloques `catch` para atrapar el error e imprimirlo en consola. Su única labor es intentar (`try`) ejecutar el SQL, y cerrar las conexiones pase lo que pase (`finally`). Si hay un error, el DAO le avienta la "estafeta" (el error) a la clase que lo llamó usando `throws SQLException` en la firma de su método.

- **El método readAll():**
  En el DAO, cuando se hace un `SELECT *`, se debe devolver una lista tipada (`List<Carrera>`). El cursor (`ResultSet`) va leyendo fila por fila con un `while(rs.next())`, arma el objeto `Carrera`, y lo guarda en la lista. ¡Y al final siempre se cierran 3 cosas en el `finally`: el ResultSet, el PreparedStatement y la Connection!

- **El método read() (Buscar uno solo):**
  Debe usar `SQL_SELECT` (la consulta con `WHERE idCarrera = ?`). Como busca por ID, la base de datos devuelve máximo 1 fila, por lo que el método debe devolver un objeto `Carrera` único, no una lista.

*Nota del profesor AI: Prometí no tocar tu código fuente. Las correcciones debes aplicarlas tú mismo en el IDE siguiendo la página 26 del PDF.*
