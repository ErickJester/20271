# Clase del 9 de septiembre — Primer proyecto Spring Boot: API REST conversor de temperaturas

- **Materia**: C704 · Web client and backend development frameworks (grupo 7CM2)
- **Fecha**: 9 de septiembre de 2026 (según la marca del usuario)
- **Número de clase**: **por confirmar** — faltan las transcripciones de las clases 7 y 8, que van antes de esta.
- **Tipo de sesión**: taller en vivo. El profesor crea un proyecto Spring Boot desde cero en IntelliJ
  y escribe un API REST que convierte temperaturas; los alumnos replican y terminan el resto.
- **Hay laboratorio al día siguiente**: dijo "los demás los revisamos mañana en el laboratorio".
- **Origen**: transcripción automática (muy sucia, mastica los términos en inglés). Esta versión está
  **decodificada y reordenada** para que se entienda; las marcas de tiempo se conservan.

> **Aviso**: la transcripción base confundía casi todos los tecnicismos. Abajo va la tabla de lo que
> se corrigió. Donde el audio quedó irrecuperable se dice `[ilegible]` en vez de inventar.

---

## Cómo se decodificó (qué decía la transcripción → qué era en realidad)

| Transcripción | Era |
|---|---|
| "Inteligible" / "visto del estudio" | **IntelliJ IDEA** (el IDE de JetBrains) |
| "prepositorio Git" | repositorio Git |
| "globid" | **groupId** |
| "Artifact id" | **artifactId** |
| "Package base" | **package name** (nombre del paquete raíz) |
| "JDC" | **JDK** |
| "la licencia" / "lo mínimo de soporte" | la **versión LTS mínima** de Java (17 para Spring Boot 3) |
| "YAR / guard / RAR / SIG" | **JAR / WAR / EAR / RAR** (tipos de empaquetado Java) |
| "donker" / "tonko" / "tommy" / "web compiler" | **Tomcat** / *web container* |
| "glasswitch" / "payar" / "Glassish" / "Webfeed" / "Jmos" | **GlassFish / Payara / WebSphere / JBoss** |
| "IJB" | **EJB** (Enterprise JavaBeans) |
| "el dictus y el huevo" | **DevTools y Web** (las dos dependencias de hoy) |
| "las dept" | **DevTools** |
| "Data JPA" / "el security versión 6 / 7.0" | **Spring Data JPA** / **Spring Security 6 vs 7** |
| "H2, el de cuates" / "proyecto HTTP de server" | **httpd**, el nombre coloquial del **Apache HTTP Server** |
| "los CERNs y las JCPs" | **Servlets y JSPs** |
| "string mbc" | **Spring MVC** |
| "jacarta faces" | **Jakarta Faces** (JSF) |
| "TimeLeap" | **Thymeleaf** (motor de plantillas) |
| "monito Lopoc" / "Lombo" | **Lombok** |
| "ResController" / "request mapping" / "get mapping" | **@RestController / @RequestMapping / @GetMapping** |
| "response entity" | **ResponseEntity** |
| "arroba request para" | **@RequestParam** |
| "Conversión response" | el record/clase **ConversionResponse** |
| "jugar con Q" / "un pipe... es JQ" | usar **cURL** / tuberia a **jq** para verlo bonito |
| "hacemos un ron" | un **Run** (ejecutar la app) |
| "la vieja con diálogo" / "Inicializer" | **start.spring.io** / **Spring Initializr** (por web) |
| "STS" / "paraviso al estudio Code" | **Spring Tool Suite** / extensiones **para Visual Studio Code** |
| "el point" | el **endpoint** |

---

## La clase en una línea

Cómo nace un backend con Spring Boot: se genera el proyecto con Spring Initializr (Maven, Java,
dependencias **Web** y **DevTools**), se cambia el puerto a **8082**, y se escribe un `@RestController`
con endpoints `GET` que reciben un parámetro por la URL, hacen una cuenta y devuelven un objeto JSON
(`ConversionResponse`) envuelto en un `ResponseEntity`.

## Tarea que dejó

Terminar el **conversor de temperaturas completo** como API REST. El profesor hizo dos de los seis;
faltan cuatro:

| Conversión | Fórmula | ¿Quién lo hizo? |
|---|---|---|
| Celsius → Fahrenheit | `F = C * 9 / 5 + 32` | ✅ el profesor (ejemplo) |
| Celsius → Kelvin | `K = C + 273.15` | ✅ el profesor (ejemplo) |
| Fahrenheit → Celsius | `C = (F - 32) * 5 / 9` | ⬜ alumno |
| Fahrenheit → Kelvin | `K = (F - 32) * 5 / 9 + 273.15` | ⬜ alumno |
| Kelvin → Celsius | `C = K - 273.15` | ⬜ alumno |
| Kelvin → Fahrenheit | `F = (K - 273.15) * 9 / 5 + 32` | ⬜ alumno |

Se dieron **30 minutos en clase** para avanzar; el resto se revisa **al día siguiente en el
laboratorio**. Valores de prueba que dio: `10 °C → 50 °F`, `10 °C → 283.15 K`, `20 °C → 68 °F`,
`20 °C → 293.15 K`.

---

## Transcripción decodificada

### 00:02 – 02:44 · Crear el proyecto en IntelliJ (Spring Initializr)

**Profesor:** Así funciona. Después nos pregunta, como cualquier IDE, dónde se va a guardar el
proyecto: por defecto se va a la carpeta de proyectos de **IntelliJ**.

Si quieren crear el **repositorio Git** de una vez, marcan la casilla; si no, lo hacen a mano desde
la terminal más tarde.

Lenguaje: **Java** (ya viene marcado). Tipo de proyecto: **Maven** (ya viene marcado). El
**groupId**: pónganle el que quieran; yo lo voy a dejar en `com.ipn.mx` por gusto. El
**artifactId** es el nombre de lo que están desarrollando; normalmente toma el nombre del proyecto,
pero se puede cambiar. El **package name** lo voy a recortar y lo dejo en `com.ipn.mx`, y al
artifactId le voy a poner `api7cm2`.

**JDK**: el que tengan instalado. Yo tengo el **26**, lo voy a dejar así; ustedes a lo mejor tienen
el 23 o el 25, o no tienen ninguno y les toca darle a *Download JDK* y elegir cuál. Versión de
Java del proyecto: lo mínimo que necesitamos hoy es una **versión LTS con soporte** (para Spring
Boot 3 eso es Java 17). De ahí para arriba, la que tengan: si traen el 26, pónganle 26.

### 02:44 – 04:18 · Digresión: JAR, WAR, EAR, RAR

**Profesor:** Después, el **empaquetado**. Cuando trabajamos con Java para web tenemos archivos de
tipo **JAR**, **WAR**, **EAR** y **RAR** (este RAR no es el de comprimir, es *Resource Adapter
Archive*, y casi no se usa).

- **JAR** — el típico. Aplicaciones de escritorio o de consola: un ejecutable empaquetado. En Spring
  Boot el JAR trae **incrustado un servidor** (una versión minimalista de Tomcat), por eso puede
  arrancar solo.
- **WAR** — para web. Es lo que metemos en la carpeta correspondiente de un **Tomcat**, un **TomEE**,
  un **GlassFish** o un **Payara** para que lo publiquen.
- **EAR** — cuando jugamos con **módulos empresariales** (EJB y demás). Antes se empaquetaba cada
  parte por separado o todo junto en un EAR.
- **RAR** — literal para adaptadores de recursos que se vayan a reutilizar.

Por definición, aquí solo vamos a usar dos: **JAR o WAR**. Funcionan parecido. Si quieren
desplegar en un servidor externo, se van por WAR; hoy lo dejamos en **JAR**.

### 04:41 – 07:38 · Dependencias

**Profesor:** Archivo de configuración: Spring soporta **`application.properties`** o
**`application.yml`** (YAML). Si vienen de Python, el YML les va a acomodar porque funciona a base de
**tabuladores/sangría**: si hay una propiedad `spring.datasource.url`, empiezas por el elemento
principal y vas bajando con indentación. Nosotros vamos a usar **`application.properties`**.

Dependencias que vamos a ir necesitando:

- **Spring Web** — la primera, indispensable.
- **Validation** — hoy no, pero después.
- **Spring Data JPA** — después.
- **MySQL Driver** — en algún punto.
- **DevTools** — hoy sí.
- **Spring Security** — eventualmente. Ojo: la que trae el Initializr es la **versión 6**; la actual
  es la **7.0**. Cuando toque, vamos a la documentación, copiamos la dependencia y la pegamos para
  traer la última versión.

**Hoy nada más nos quedamos con dos: `DevTools` y `Web`.** Le damos a *Create*.

### 07:38 – 08:29 · El `HELP.md` y las guías de Spring

**Profesor:** El `HELP.md` que se genera con el proyecto deberían leerlo: apunta a la documentación
oficial de Maven, de Spring y de Spring Web, y trae guías de apoyo como *"Building a RESTful Web
Service"* o *"Serving Web Content with Spring MVC"* (esta última explica **MVC**: modelo, vista,
controlador). Lo cerramos.

### 08:29 – 09:57 · Estructura del proyecto y `application.properties`

**Profesor:** Esta es la estructura con la que normalmente trabajamos. Como estamos creando algo
para web, Spring MVC genera la estructura de ese estilo. Todo lo que quieran poner en
`src/main/resources` —en **`static/`** y en **`templates/`**— es lo que tiene que ver con recursos:
HTML, estilos, imágenes, lo que el proyecto necesite pintar.

Si fuera un proyecto web con vistas, ahí van los HTML. Funciona con HTML puro, o con un framework
extra que hay que incorporar: **Thymeleaf**, que actúa como motor de plantillas. Nosotros no lo
vamos a tocar hoy.

Lo único que vamos a modificar es el **`application.properties`**, y por ahora solo le ponemos:

```properties
server.port=8082
```

¿Por qué 8082? Por puro gusto.

### 09:57 – 13:04 · Digresión: web server vs web container vs application server

**Profesor:** Si voy a hacer algo para web, necesito un **web server**. Y siendo formales, para Java
en realidad necesito un **web container**: necesito un **Tomcat**. Esta cosa, por defecto, se
apropia del **puerto 8080**.

En el ecosistema de Java, hablando de servidores, hay tres niveles:

| Nivel | Ejemplos | Entiende | Puerto por defecto |
|---|---|---|---|
| **Web server** | Apache **httpd** ("el de los cuates") | HTML, CSS, JavaScript y recursos estáticos (audio, video, imágenes) | 80 |
| **Web container** | **Tomcat** | Todo lo anterior + tecnología Java para web: **Servlets y JSPs**, y frameworks como **Spring MVC** o **Jakarta Faces** | 8080 |
| **Application server** | **GlassFish, Payara, WebLogic, WebSphere, JBoss** | Todo lo anterior + Jakarta EE completo (EJB, etc.) | 8080 |

Si en algún momento montan la aplicación con capas físicas separadas: el **web server** normal para
la carcasa/landing que no hace nada, el **Tomcat** para la parte que ya procesa Java, y el
**application server** para lo que tiene que ver con servicios, *data source*, conexiones y demás.

### 13:04 – 16:36 · El DTO: un `record ConversionResponse`

**Profesor:** Vamos a escribir un cachito de código. En el paquete principal creamos un **nuevo
paquete** para el **DTO**.

¿Cuál es la estructura de un DTO? Una clase Java simple, que probablemente implemente
`Serializable`, con atributos privados y un constructor sin argumentos... *(se acuerda de que le
faltó agregar **Lombok** al crear el proyecto)*.

En vez de una clase simple, como esto **no va a cambiar**, vamos a usar un **`record`**. Le
llamamos `ConversionResponse` (lo quiero para la respuesta). ¿Qué lleva?

```java
public record ConversionResponse(
    double valorOriginal,
    String unidadOriginal,
    double valorRespuesta,
    String unidadRespuesta
) {}
```

Y ya. No necesitamos más. Podría quedar como clase con *getters* y *setters*, pero como es un objeto
que solo pasa valores a los atributos y los recupera, un `record` es muy adecuado para un DTO.

### 16:36 – 24:19 · El controller: `TemperaturaController` y el primer endpoint

**Profesor:** Creamos otro **paquete nuevo**: `controller`. Adentro, una **clase simple**, el
controlador. Vamos a empezar con esto: convertir de **Celsius a Fahrenheit**.

Referencia de lo que debe hacer (un conversor que ya existe): `10 °C → 50 °F`; `10 °C → 283.15 K`;
Kelvin → Celsius y Fahrenheit → Celsius también. Vamos a hacer el conversor de temperaturas
completo — bueno, ustedes; yo hago uno.

```java
@RestController
@RequestMapping("/api/temperatura")
public class TemperaturaController {

}
```

- **`@RestController`** — porque vamos a crear un API REST; en teoría nos deja usar todos los verbos
  del protocolo.
- **`@RequestMapping("/api/temperatura")`** — es la forma de llegar a este controlador, la ruta base
  del API.

Con eso, se llega a él por `http://localhost:8082/api/temperatura`. El `8082` sale del
`application.properties` (`server.port`). Como el servidor corre en mi máquina, apunto a mi
`localhost` / `127.0.0.1`.

**Los verbos.** ¿Cuál usamos? El único, un **`@GetMapping`**. ¿Por qué? Porque no vamos a insertar,
ni actualizar, ni eliminar, ni hacer actualizaciones parciales. Es: "método fulanito, recibe un
argumento, opera con él y devuelve un resultado".

**Pregunta para ustedes** *(la deja abierta)*: el método debe ser `public`, y devuelve un
`ResponseEntity`. ¿De qué? Del `ConversionResponse` que creamos.

```java
    @GetMapping("/convertir-a-fahrenheit")
    public ResponseEntity<ConversionResponse> celsiusToFahrenheit(
            @RequestParam("valor") double celsius) {
        // ...
    }
```

- **`@RequestParam("valor")`** — el parámetro que llega por la URL se llama `valor`; la variable
  Java que lo recibe la llamo `celsius`.

**Decisión que tienen que tomar:** cada método mapeado puede llevar **nombre propio** en el
`@GetMapping` (ej. `"/convertir-a-fahrenheit"`), o no llevarlo. Como **todos van a ser `GET`**, hay
que diferenciarlos de algún modo: o por el nombre de la ruta, o por los parámetros que reciben. Lo
más sano aquí es **ponerles nombre**.

### 24:19 – 29:48 · El cuerpo del método y la respuesta

**Profesor:** La cuenta: por 9, entre 5, más 32.

```java
        double fahrenheit = celsius * 9 / 5 + 32;
```

Ahora la respuesta, con el `ConversionResponse`. Lo primero que recibe es el **valor original**
(el `celsius` que nos pasaron); luego la **unidad original** (por gusto: `"grados centígrados"`);
luego el **valor de la respuesta** (`fahrenheit`); y por último la **unidad de la respuesta**
(`"grados Fahrenheit"`).

```java
        ConversionResponse response = new ConversionResponse(
            celsius,
            "grados centígrados",
            fahrenheit,
            "grados Fahrenheit"
        );
        return ResponseEntity.ok(response);
```

Guardamos. Ya tenemos el primer método.

### 29:48 – 33:14 · Probar el endpoint y dejar la tarea

**Profesor:** ¿Cómo lo usamos? Construyendo la URL:

```
http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=10
```

Le hacemos un **Run** a la aplicación. Si todo va bien, ya debería estar respondiendo. Pegamos la
URL desde donde quieran (el navegador, por ejemplo).

Resultado con `valor=20`:

```json
{
  "valorOriginal": 20,
  "unidadOriginal": "grados centígrados",
  "valorRespuesta": 68,
  "unidadRespuesta": "grados Fahrenheit"
}
```

Por un 10 da 50, por un 20 da 68. Sencillito, ¿no?

**Tienen 30 minutos para acabar** el conversor completo: de Celsius a Kelvin, de Fahrenheit a
Kelvin, de Fahrenheit a Celsius, de Kelvin a Celsius y de Kelvin a Fahrenheit. Literal, todo el
conversor. **Eso es un API.**

### 33:14 – 36:36 · Segundo endpoint: Celsius → Kelvin (lo hace el profesor)

**Profesor:** Les ayudo con el otro para que no digan. Hoy toca Celsius → Kelvin. Copiamos el
método y le cambiamos el nombre del `@GetMapping` a algo como `"/convertir-celsius-a-kelvin"`, y el
método pasa de `celsiusToFahrenheit` a `celsiusToKelvin`. Lo demás se queda igual.

La cuenta: el valor recibido (`celsius`) **más 273.15**.

```java
    @GetMapping("/convertir-celsius-a-kelvin")
    public ResponseEntity<ConversionResponse> celsiusToKelvin(
            @RequestParam("valor") double celsius) {

        double kelvin = celsius + 273.15;

        ConversionResponse response = new ConversionResponse(
            celsius,
            "grados centígrados",
            kelvin,
            "grados Kelvin"
        );
        return ResponseEntity.ok(response);
    }
```

*(En el pizarrón le salió `273.5`; el valor correcto es `273.15`.)* La respuesta se arma igual:
valor original, unidad original, valor convertido, unidad a la que se convierte.

Guardamos, arrancamos, y probamos con `valor=20` → **293.15**. Sí hace su chamba. Les toca a
ustedes terminar los otros cuatro.

### 36:36 – 38:19 · Clientes para probar: cURL y jq

**Profesor:** Su compañero decía que lo ideal es probar con **cURL**. En términos de cliente,
podemos pasarle la URL tal cual, entre comillas:

```bash
curl "http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=50"
```

Hace lo mismo, pero la respuesta sale "en crudo". Si la quiero **bien estilizada**, al final le
agrego una tubería a **`jq`**, que la imprime bonita:

```bash
curl "http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=50" | jq
```

### 38:19 – 43:04 · Postman / Insomnia: colección y query params

**Profesor:** Ustedes eligen con qué trabajar. Vamos a crear una **colección** para organizar, le
llamamos `conversor temperaturas`, *Create*. Ahora el primer **request**: un `GET`. Básicamente es
pegarle la URL que ya tenemos.

Siendo decentes con la herramienta: el parámetro `valor` **no lo escribes en la URL**, lo agregas en
la sección de **query params** (`valor` = `50`). Si lo quitas de la URL cruda, el *preview* de la
herramienta lo vuelve a agregar solo con el `?valor=...`. Le das *Send* y ahí está la respuesta,
con su **código de respuesta**, su tiempo, etc.

**Sobre el código de respuesta:** a lo mejor no lo conceptualizamos, pero eso está devolviendo un
**200**. Los demás endpoints los revisamos mañana en el laboratorio.

### 43:04 – 44:52 · Recap del `application.properties`

**Profesor:** El `application.properties`, en este momento, por defecto solo trae
`spring.application.name` (el nombre del proyecto). Lo primero que agregamos fue `server.port`. Si
**no** lo pones, funciona igual, solo que busca el **8080**. Gráficamente: si dejamos `8082` y el
servidor está en 8080, un *Send* da error de conexión. Es lo único que hace esa propiedad. Lo
dejamos como está.

Entonces, señores, **eso es un DTO**.

### 44:52 – 48:21 · Alternativa: el DTO como clase con Lombok

**Profesor:** Si quieren crear una **clase** para el DTO en vez de un `record`, por supuesto que se
puede. Quedaría algo así, con ayuda de **Lombok**:

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponse implements Serializable {
    private double valorOriginal;
    private String unidadOriginal;
    private double valorRespuesta;
    private String unidadRespuesta;
}
```

Si lo hacen con clases, el resto del código **casi no cambia** — de hecho, ni siquiera cambiaría:
`new ConversionResponse(...)` sigue funcionando igual, y debería responder lo mismo.

### 48:21 – 01:01:31 · Trabajo en laboratorio + digresiones (comprimido)

La última parte es sobre todo tiempo de trabajo de los alumnos y ruido de la transcripción
automática (muchos "Gracias" sueltos que son artefactos, no diálogo). Lo rescatable:

- **Conteo de asistencia / grupo**: contó hasta ~30. Charla lateral con un alumno de **movilidad**
  al que dieron de baja "por no haber tomado la materia previa". Sin relación con el contenido.
- **spring.io**: recorrió la página de proyectos (**Spring Data**, **Spring Boot**, **Spring
  Security**...). Mencionó las **free tools**: **STS (Spring Tool Suite)** y las extensiones **para
  Visual Studio Code**.
- **start.spring.io (Spring Initializr por web)**: la vía alterna a IntelliJ. Se elige proyecto
  **Maven**, lenguaje **Java**, versión de Spring Boot **estable** (no SNAPSHOT), Java **26**, y del
  lado derecho se agregan las dependencias: **Lombok**, **Web**, **DevTools**, **PostgreSQL
  Driver**, **Validation**, y **Security** cuando toque. *Generate* descarga un ZIP; lo
  desempaquetas y lo abres en el IDE. Funciona igual que crearlo desde IntelliJ.
- Broma de cierre: en las **noches de insomnio**, "que su documentación sea su biblia" en lugar de
  ponerse a maratonear series.
