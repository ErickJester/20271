# Práctica API — Creación de una API REST con Spring Boot (conversor de temperaturas)

- **Materia**: C704 · Web client and backend development frameworks (grupo 7CM2)
- **Fecha**: miércoles 9 de septiembre de 2026 (la misma sesión que
  [`clases/clase 9 sept/transcripcion.md`](../../clases/clase%209%20sept/transcripcion.md), donde
  esa versión está decodificada desde la transcripción automática). Al día siguiente, jueves 10,
  fue el laboratorio en el Laboratorio 3
- **Duración aproximada**: ~65 minutos (marcas de tiempo del 00:00 al ~64:47), con bloques de trabajo
  en máquina de por medio
- **Tipo de sesión**: teoría-práctica. El profesor construye en vivo el primer endpoint y deja el
  resto del conversor como ejercicio
- **Tema**: proyecto Spring Boot con Maven en IntelliJ, `application.properties`, DTO como `record`,
  `@RestController` + `@RequestMapping` + `@GetMapping`, `ResponseEntity`, `@RequestParam`, pruebas con
  navegador, `curl`/`jq`, Postman e Insomnia
- **Origen**: transcripción de la grabación, con marcas de tiempo aproximadas

> Los pasajes entre paréntesis y en cursiva son acotaciones de lo que pasaba en el aula, no palabras
> del profesor. Los tecnicismos de backend se respetan tal cual.

---

## Resumen rápido

| Qué | Valor |
|---|---|
| Proyecto | `ApiSimple7CM2` (Maven, Java, empaquetado JAR) |
| Group / package base | `com.ipn.mx` |
| Java | mínimo 17; el profesor usa el JDK 26 |
| Dependencias de hoy | Spring Web + Spring Boot DevTools |
| Puerto | `server.port=8082` (por defecto sería 8080) |
| Endpoint base | `http://localhost:8082/api/temperatura` |
| Verbo usado hoy | `GET` → responde `200 OK` |
| Ejemplo | `http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=10` |

**Tarea de la clase (30 min en el aula; lo demás en el laboratorio del jueves):** completar el
conversor de temperaturas con los métodos que faltan (ver [Ejercicio](#ejercicio-completar-el-conversor)).

---

## Bloque 1 · Crear el proyecto en IntelliJ (minuto 00:00 a 07:35)

**Profesor:** …puede llamar ApiSimple. Así funciona.

Después nos pregunta, como cualquier IDE de algún modo, nos dice dónde va a estar guardado. Ahí dice
que se va a ir a... a donde está la carpeta de proyectos de IntelliJ, de IDEA.

Si ustedes quieren crear el repositorio Git de una vez, le pican a la casilla de crear el repositorio
Git; si no, lo pueden hacer a manita desde terminal, se meten a la carpeta y le dan un `git init` y
san se acabó.

- **Lenguaje**: vamos a agarrar Java. Entonces Java ya está marcado.
- **Tipo**: en realidad vamos a agarrar Maven, ya está marcado.
- **Group ID**: pónganle el que ustedes quieran. En este caso lo voy a dejar `com.ipn.mx`, nada más
  por puro gusto.
- **Artifact ID**: cómo se llama la cosa esa que están desarrollando. Normalmente toma el nombre del
  proyecto, se lo pueden cambiar si quieren.
- **Package base**: le voy a quitar... no es cierto, regrésate... le voy a quitar este cachito, lo
  vamos a dejar en `com.ipn.mx`. Y este le voy a cambiar el nombre: `ApiSimple7CM2`, ¿vale?
- **JDK instalado**: el que tengan ustedes instalado. Por ejemplo, yo tengo el 26 instalado, vamos a
  dejar eso. Pero pues a lo mejor el 23, pues a lo mejor el 25, o a lo mejor no tienen JDK instalado y
  tienen buen internet, pues a lo mejor le pican a descargar JDK y ustedes deciden cuál, ¿sale?
- **Versión de Java**: pues en realidad lo mínimo que necesitamos para trabajar es la 17, ¿sale? Es lo
  mínimo que se requiere de soporte. De ahí en fuera, ustedes elijan con la que tengan. Por ejemplo,
  tienen un JDK 25, pues ponen 26. Yo creo que a estas alturas el soporte ya será adecuado.

### Empaquetado: JAR, WAR, EAR, RAR

**Profesor:** Después, el empaquetado: cuando nosotros jugamos con Java o proyectos web para Java
—bueno, cuando jugamos con proyectos para Java—, en realidad tenemos archivos de tipo:

| Extensión | Significado |
|---|---|
| `.jar` | Java Archive |
| `.war` | Web Application Archive |
| `.ear` | Enterprise Application Archive |
| `.rar` | Resource Adapter Archive |

- `.rar`: y sí, no es el ZIP tradicional o el equivalente al ZIP tradicional; la naturaleza es la
  misma, pero existe dentro de los empaquetados en el ecosistema empresarial de Java. Rara vez se
  utiliza, ¿vale?, pero sí, ¿sale?
- `.jar`: normalmente cuando hacemos aplicaciones de escritorio o aplicaciones en consola, queremos un
  ejecutable y lo empaquetamos; es como que el típico.
- `.war`: cuando hacemos cosas para web, necesitamos a esta cosa, y es el empaquetado que nosotros
  metemos en un Tomcat, en un TomEE, en un GlassFish o en un Payara en la carpeta correspondiente y se
  carga.
- `.ear`: cuando jugamos específicamente con módulos empresariales, ¿sale?, con EJB (Enterprise
  JavaBeans) y cositas por el estilo, que en principio siempre y cuando se crean proyectos de cada una
  de las partes; si no, todo se empaquetaba o todo se empaqueta en un JAR y tampoco pasa nada, o en un
  WAR.
- `.rar`: es literal para recursos que se vayan a utilizar.

Pero bueno, por definición aquí solo tenemos dos archivos: JAR o WAR. ¿Funcionan muy similar? Sí y no,
¿sale? El JAR lo que trae incrustada la cosa esa es una versión muy minimalista de un Tomcat (Tomcat
embebido), ¿sale? Por eso puede funcionar. Pero si quieren que la extensión sea diferente y lo quieren
cargar a un servidor en sí, ah, bueno, pues a lo mejor podemos agarrar el WAR, ¿sale?

En este caso vamos a dejarlo en **JAR**.

### Archivo de configuración: properties o YAML

**Profesor:** Archivo de configuración: se requiere. Tiene soporte para las dos variantes: un
`application.properties` o un `application.yml` (un YAML), ¿vale? Vamos a agarrar el `properties`.

Si vienen o están acostumbrados de ir jugando con Python, pues el YAML les funciona porque funciona a
base de tabuladores; solo que es un carajito... bueno, me cuesta trabajo a mí, a lo mejor a ustedes se
les hace simple: funciona con tabuladores, ¿vale?

Por ejemplo, si hay una propiedad eh... que se llama `spring.datasource.jpa.x_cosa`: ah, bueno,
primero empiezas con el elemento principal y vas bajando según lo que vayan utilizando, ¿vale?

### Dependencias

**Profesor:** Luego de aquí, ¿con quién vamos a estar trabajando? Bueno, el primero: pues necesitamos
el **Web** (Spring Web). Necesitamos —hoy no, pero necesitamos a lo mejor— este... **Validation**
(Spring Boot Starter Validation). Necesitamos a lo mejor **Data** (Spring Data JPA). Necesitamos
**MySQL**, por ejemplo, en algún punto. Vamos a ocupar las **DevTools** (Spring Boot DevTools), nada
más para que esté jugando. 1, 2, 3, 4, 5... ¿Me hace falta uno?

**Alumno:** ¿Security?

**Profesor:** No, ahorita no. No, eso vamos a dejarlo para después. Pero sí, el Security sí,
eventualmente sí, pero me parece que el que trae este es la versión 6 todavía, ¿vale? La versión
actual es la 7. y algo si mal no recuerdo, entonces pues vamos a la documentación y tomamos la
dependencia y se lo pegamos al `pom.xml` para que se traiga la última versión, porque según yo trae el
6. no sé qué.

Este... ¿no me hace falta algo? El Web, el Data, el Validation, DevTools... Vamos a pensar que solo con
eso: MySQL, JPA... Al menos con eso, ¿va?

Pero hoy no vamos a jugar con esas cosas, entonces hoy nada más nos vamos a quedar con:

- El **DevTools**
- Y el **Web**

Solo esos dos, ¿va? Entonces solo con esos dos por lo pronto. Ya luego recordaré qué más me hace
falta. Y vamos a decirle que lo cree.

---

## Bloque 2 · Estructura del proyecto, `HELP.md` y puertos (minuto 07:35 a 13:20)

*(El IDE genera el proyecto.)*

**Profesor:** Y tienen algo como eso. Y bueno, el Markdown del `HELP.md` deberían de leerlo o
revisarlo en algún momento. ¿Por qué? Pues porque a lo mejor:

1. Los apunta a la documentación oficial de Maven.
2. El de Spring.
3. El de Web.
4. Y trae guías de apoyo, como por ejemplo: «Construye un servicio REST», «Construye una aplicación
   web con MVC (en este caso Spring MVC, el Modelo-Vista-Controlador)», y ya.

Y pues verlo sí vale la pena. Lo cerramos, ¿sale?

Vamos a revisar estos archivitos. Esta es la estructura con la que normalmente trabajamos. Como estamos
creando en teoría algo para web por definición, si bien no agregamos Spring MVC específicamente,
genera la estructura a lo mejor para crear un proyecto de ese estilo. Todo lo que ustedes quisieran
agregar en la carpeta `resources`, por ejemplo en `static` y en `templates`, pues son lo que tiene que
ver con los recursos HTML o con los estilos, o con las imágenes, o con lo que sea que quisieran pintar
si fuera la naturaleza del proyecto.

Por ejemplo, si vamos a hacer algo web, pues en `templates` se van todos los HTML correspondientes,
¿sale? Funciona con páginas HTML puras con una biblioteca, con un framework extra que hay que
incorporar que es **Thymeleaf**, ¿sale?, que es el que hace justo... funciona como motor de plantillas.

Pero nosotros no lo vamos a tocar, solo vamos a modificar algo de aquí: del `application.properties`.
Y lo único que le vamos a poner por ahorita va a ser un:

```properties
server.port=8082
```

¿Por qué? Por puro gusto.

### Web Server vs. Web Container vs. Application Server

**Profesor:** ¿A qué nos referimos? Nos referimos a lo siguiente: que si entonces voy a jugar con un
servidor, voy a hacer algo para web, ¡sí!, entonces necesito un Web Server. ¿Y quién es el Web Server
con el que voy a trabajar? Siendo formales, en realidad necesito un **Web Container**, necesito un
**Tomcat**, ¿sale? Y esta cosa por defecto se apropia del puerto **8080**, ¿va?

Que en la definición, nosotros en el ecosistema de Java tenemos, hablando de servidores:

| Tipo | Ejemplos | Qué entiende |
|---|---|---|
| **Web Server** | Proyecto Apache HTTP Server (el Apache 2 de los cuates) | HTML, CSS, JavaScript y cualquier cosa de JavaScript que se les ocurra, más un montón de recursos de carácter estático: audio, video, imágenes, lo que sea |
| **Web Container** | Tomcat | Cualquier tecnología Java para web, específicamente Servlets y JSP y cualquier framework de Java para web: por ejemplo Spring MVC, Jakarta Faces (JSF), etc. Más todo lo del Web Server |
| **Application Server** | GlassFish, Payara, WebLogic, WebSphere, WildFly (el JBoss) | También por defecto se apropia del puerto 8080 |

**Profesor:** Y entonces, si deciden en algún momento tener su aplicación así con el ecosistema de Java
muy con capas físicas:

- Necesitan a lo mejor para la carcasa web, para la landing que no hace nada, a lo mejor el **Web
  Server** normal.
- Para la parte que ya procesa algo de Java muy web, muy tranquilo: a lo mejor el **Tomcat**.
- Para alguien que tiene que ver con servicios, este... data source, pool de conexiones y demás: pues
  a lo mejor agarramos a este cuate (**Application Server**), ¿sale?

Y bueno, uno... esos dos deben tener un puerto diferente. Entonces nosotros vamos a jugar con una
versión minimalista del Tomcat, lo vamos a meter en el **8082**, ¿va?

Vamos a empezar a escribir cachito de código, ¿sale?

---

## Bloque 3 · El DTO como `record` (minuto 13:20 a ~20:00)

*(Escribe código en el IDE.)*

**Profesor:** Bueno, vamos a empezar a escribir un cachito de código. Aquí en el `src/main/java`, en el
source package, vamos a crear un nuevo paquete. Nuevo package, yo le voy a llamar... lo voy a poner
directo: `dto`.

Vamos a crear un DTO. Ahora, ¿cuál es la estructura de un DTO? Bueno, nos dicen que un DTO es una
clase Java simple que probablemente implemente la interfaz de serialización (`Serializable`), que tenga
atributos privados a la clase, un constructor con argumento cero... ¡Ya me acordé qué otra cosa me
hacía falta! *(Risas)*

Pero bueno, sí. ¿Ya se acordaron? ¿Ustedes no se acordaron? Se me olvidó poner **Lombok** para no
escribir cosas, pero bueno, en fin. Ahí está.

En fin, justo. Entonces eso es lo que debe tener una clase Java, ¿no? En este caso vamos a crearlo así:
en lugar de una clase de... simple, común, normal, vamos a poner un **`record`**, porque esta cosa no
va a cambiar. Entonces le vamos a llamar `ConversionResponse`. Lo quiero para respuesta:
`ConversionResponse`.

Y ahí está, ¿vale?

Entonces, ¿qué va a tener esta cosa? Vamos a definir como atributo, por ejemplo de tipo `Double`,
vamos a poner `valorOriginal`, coma, vamos a poner un `String unidadOriginal`, vamos a poner un
`Double` a lo mejor `valorRespuesta`, `String unidadRespuesta`:

```java
package com.ipn.mx.dto;

public record ConversionResponse(
    Double valorOriginal,
    String unidadOriginal,
    Double valorRespuesta,
    String unidadRespuesta
) {}
```

Y ya, no necesitamos más, ya tenemos un DTO en particular, ¿sale?

Insisto: ¿pudiera quedar como una clase? Sí. Necesitaríamos getters y setters para cada cosa. Este en
particular pues en realidad es un... literal, es un objeto que funciona simplemente pasándole valores a
los atributos y recuperando los valores de los atributos. Es **inmutable** y por lo tanto puede ser muy
específico para los DTOs.

---

## Bloque 4 · El controller: `@RestController` y `@RequestMapping` (minuto ~20:00 a ~26:00)

**Profesor:** Luego vamos a crear un nuevo paquete, nuevo package, le vamos a llamar `controller`. Y
aquí, ¿qué vamos a poner? Pues justo el controller que necesitamos: nueva Java Class —ahora sí es una
clase simple— y vamos a crear el controlador. ¿Cómo le vamos a llamar?

¿Qué vamos a hacer para empezar? Ah, bueno, vamos a hacer esto: ¿qué es este? Convertir de Celsius a
Fahrenheit. Yo no, lo van a hacer ustedes, por cierto. Pero es: vamos a replicar esta cosa que está
aquí. Si yo le pongo un 10 de Celsius a Fahrenheit me dice que es 50; si yo le pongo un 10 y lo quiero
en Kelvin debería de darme 283, ¿sale? Si le paso un Kelvin a Celsius debería de darme esto; si le paso
un Fahrenheit a Celsius debería de darme esto, ¿sale? Vamos a hacer un conversor de temperaturas. Bueno,
ustedes lo van a hacer, yo no.

Entonces vamos a llamarle `TemperaturaController`:

```java
package com.ipn.mx.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/temperatura")
public class TemperaturaController {

}
```

¿Qué debe de llevar esta cosa? Bueno, pues vamos a decorarlo. Lo primero que debe de llevar es un
**`@RestController`**, pues porque vamos a crear un API. ¿Vale? Y como es de tipo REST, pues en teoría
debería de permitirnos utilizar todos los verbos del protocolo.

Después vamos con un **`@RequestMapping`**. ¿Por qué? Porque tengo que encontrar la forma para llegar
a este cuate, la forma en que voy a utilizar el API. Entonces por lo pronto le vamos a poner signo de
comillas, diagonal, api, diagonal, temperatura, justo: `"/api/temperatura"`. Ahí está, ¿sale?

Y con eso simplemente le vamos a decir a la cosa esa que podemos llegar a él con:

```
http://localhost:8082/api/temperatura
```

Que fue el puerto que se definió en el `application.properties`, creo. Sí.

Cualquier cosa que nosotros vayamos a solicitar a la URL tenemos que pegar esto de arranque, hasta ahí.
¿De dónde sale? De este archivito, ¿sale? Necesitamos el puerto, que es esta cosa y que básicamente es
mi `server.port`, ¿sale? Y como es un server, pues está instalado en mi máquina: voy a apuntar a mi
`localhost`, a mi `127.0.0.1`, a mi `192.168.x.x` —la IP que tenga— o si está casado con DNS, pues al
nombre del server, ¿no? Y el resto lo obtenemos justo de la definición del `RequestMapping` en el
controller, ¿vale?

Bueno, prosigamos. Continuemos.

---

## Bloque 5 · El primer verbo: `@GetMapping` y `ResponseEntity` (minuto ~26:00 a 32:10)

**Profesor:** Ahora, ¿qué sigue? Pues vienen los verbos. ¿Cuál vamos a usar? El primer verbo, el único
verbo a lo mejor a mapear va a ser un **`@GetMapping`**. ¿Por qué? Porque pues en realidad no vamos a
insertar, no vamos a actualizar, no vamos a eliminar, no vamos a hacer actualizaciones parciales;
simplemente es: «Mira, método fulanito, encuéntrate, te paso un argumento, opera con él y devuélveme un
resultado». Al menos eso es lo que pretendemos.

Y aquí viene una pregunta para ustedes: por lo pronto lo vamos a dejar así. ¿Qué vamos a poner? Pues
`public`, por ejemplo, porque el método debería de ser público, y vamos a poner un **`ResponseEntity`**
como respuesta. ¿A quién queremos que nos devuelva como respuesta? Pues al `ConversionResponse`, que de
alguna manera fue el que creamos. Y vamos a crear el método, le vamos a poner el nombre al método:
`celsiusToFahrenheit`:

```java
@GetMapping("/convertir-a-fahrenheit")
public ResponseEntity<ConversionResponse> celsiusToFahrenheit(
    @RequestParam(name = "valor") Double celsius
) {
    Double fahrenheit = (celsius * 9 / 5) + 32;
    ConversionResponse response = new ConversionResponse(celsius, "°C", fahrenheit, "°F");
    return ResponseEntity.ok(response);
}
```

Y este método pues va a recibir argumentos. ¿Qué argumentos va a recibir este método? Bueno, pues va a
recibir o le vamos a pasar un valor, y para eso vamos a decorarlo: **`@RequestParam`**, por ejemplo,
¿sale? ¿Qué nombre va a tener el parámetro que va a recibir? Por ejemplo, `valor`, ¿vale?

¿De qué tipo de dato va a ser ese parámetro? `Double`. ¿Cómo se llama la variable que va a responder a
ese parámetro? Pues a lo mejor le voy a llamar `celsius`, ¿sale?

### ¿Ponerle nombre al mapeo o no?

**Profesor:** Pero antes de llegar a esta cosa, aquí: tomen ustedes una decisión, ¿sale? Tomen una
decisión: ¿le van a poner nombre o simplemente se van a referir a esto? ¿A qué me refiero? Cada método
que ustedes mapeen puede llevar un nombre en particular: ¿cuál?, el que ustedes quieran. Por ejemplo,
`"/convertir-a-fahrenheit"`, por ejemplo, si ustedes así lo consideran.

Ahora, ¿eso qué significa? Significa que aparte de esto, cuando manden a llamar a ese método le vamos a
pegar la diagonal `convertir-a-fahrenheit`, o como sea que se diga. Si ustedes deciden que es un mapeo
de un método por ejemplo de este estilo, sin nombre, pues en realidad hasta aquí es suficiente. Pero
como son puros GET, sí hay que diferenciarlos: eventualmente o reciben un argumento o tienen un nombre.
En este caso lo más sano es ponerle un nombre.

### Cuerpo del método

**Profesor:** Luego vamos a jugar con la respuesta, más bien ¿qué vamos a hacer aquí? Quedamos que este
es Celsius y este es Fahrenheit. Este cachito que está aquí me lo voy a llevar, lo vamos a dejar aquí y
es con el que vamos a jugar.

Entonces vamos a convertir: ¿qué necesito? Un `Double`, por ejemplo, `fahrenheit`, me funciona, ¿vale?
Y este va a ser igual a la cantidad que estamos recibiendo. ¿Quién es el argumento? Pues el argumento es
`celsius`.

Y ahí dice que lo vamos a multiplicar por 9 y lo vamos a dividir entre 5 y le vamos a sumar 32. O al
menos eso dice. ¿De dónde obtenemos esto? Pues lo vamos a obtener de aquí, del argumento que recibimos
como método, ¿va?

Luego pues necesito jugar con mi respuesta y para eso vamos a jugar con mi `ConversionResponse`. Le
vamos a llamar `response` y este va a ser igual a por ejemplo `new ConversionResponse(...)` que recibe
algunos valores, ¿vale?

Acuérdense que lo primero que recibe es el valor original, por lo tanto el valor original va a estar
dado por `celsius`, el parámetro que recibimos. Después dice que es la unidad original: nada más por
puro gusto la unidad original esta cosa dice que viene en grados centígrados, por ejemplo: `"°C"`. Coma,
después me dice que viene el resultado de la conversión o el valor convertido, el valor de la respuesta
en este caso, y el valor de la respuesta va a estar dado por `fahrenheit`.

Y por último es el otro valor que es la unidad de respuesta, es decir a qué cosa lo convirtió, y en este
caso fue a grados Fahrenheit: `"°F"`, ¿vale?

¡Listos! Ese es nuestro objeto respuesta. Ahora pues tenemos que decirle que nos lo regrese y vamos con
un `return` de tipo `ResponseEntity.ok(response)`, y ya, ¿sale? Sin más ni más.

Guardamos. Ya tenemos el primer método de alguna forma.

### Documentar el uso

**Profesor:** Ahora, ¿cómo usaríamos a lo mejor ese primer método? ¿Dónde lo pongo? Aquí arriba. Ahí
está, ¿sale?

Recibe un parámetro, el `celsius`; ¿qué cosa me devuelve?, pues ahí le ponen lo que sea que devuelva,
que en este caso es un `ConversionResponse`. Y le vamos a poner uso: nada más para tenerlo a la mano va
a ser:

```
http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=10
```

Así lo vamos a llamar, ¿sale? Así vamos a construir la URL para que esta cosa nos responda en algún
momento. ¿Desde dónde? Desde donde ustedes quieran. Si todo va bien —y espero que todo vaya bien, que
todo esté tranquilo, si es que esta cosa va bien—, nada más hacemos un run a eso y pues yo esperaría que
ya esté respondiendo, ¿sale?

Y si ya está respondiendo, bueno, pues a lo mejor pudiéramos apuntar a... ¿Dónde quedó? Esta cosa que
está aquí, así como está, y vamos a pegarlo aquí, ¿vale?

---

## Bloque 6 · Prueba en el navegador (minuto 32:10 a ~33:00)

*(Prueba el endpoint en el navegador.)*

**Profesor:** Y nos dice que el resultado es eso. No sé si sea correcto, pues al parecer sí:

- Dice que por un 10 me da un 50.
- Por un 20 me da un 68. Vamos a ver si es cierto... Ahí está la respuesta:

```json
{
  "valorOriginal": 20.0,
  "unidadOriginal": "°C",
  "valorRespuesta": 68.0,
  "unidadRespuesta": "°F"
}
```

¿Sale? ¿Estamos de acuerdo, jóvenes? Sencillito, ¿no?

### Ejercicio: completar el conversor

**Profesor:** Bueno, tienen 30 minutos para acabar:

- De Celsius a Kelvin
- De Fahrenheit a Kelvin
- De Fahrenheit a Celsius
- De Kelvin a Celsius
- Y de Kelvin a Fahrenheit

Literal, todo el conversor. Eso es un API.

---

## Bloque 7 · Celsius → Kelvin en vivo (minuto ~33:00 a 36:45)

**Profesor:** Les ayudo con el otro, vamos a terminar Celsius para que no digan. Hoy toca
Celsius-Kelvin. Entonces, como toca Celsius-Kelvin, vamos a regresar a esta cosa. Y lo bonito es que
así como está, en realidad así como está me es funcional:

```java
@GetMapping("/convertir-a-kelvin")
public ResponseEntity<ConversionResponse> celsiusToKelvin(
    @RequestParam(name = "valor") Double celsius
) {
    Double kelvin = celsius + 273.15;
    ConversionResponse response = new ConversionResponse(celsius, "°C", kelvin, "K");
    return ResponseEntity.ok(response);
}
```

*(El bloque de arriba es el que aparece en la transcripción. Enseguida el profesor renombra el mapeo a
`convertir-celsius-a-kelvin`, como se ve en la URL de prueba.)*

**Profesor:** Ya rezongó, y va a rezongar. Ahora aquí, ¿qué vamos a decirle? Convertir a... le vamos a
poner de un nombre: `convertir-celsius-a-kelvin`. Y por lo tanto el método: `celsiusToKelvin`. Lo
demás hasta aquí se queda igualito y ya nada más nos peleamos con este: aquí dice que debería de ser
así, ¿vale?

Vamos a ver si es cierto. Y aquí nos dice —y para este vamos a cambiarle de nombre, aquí le vamos a
poner `kelvin`, por ejemplo—, ya nos rezongó allá abajo, pues de una vez cambiamos el... ajá, para que
no rezongue.

Aquí nos dice que es el valor recibido, que en este caso pues es `celsius` (o sea que le vamos a pasar
el valor de 20) más 273.15, y ya, ¿sale? Y como no hay operación extra, pues hasta le podemos quitar
ese paréntesis. ¡Listos!

Entonces recibió 20, le suma 273 y eso te debe de dar algo como eso. Y listo. Entonces de nuevo: la
respuesta se construye igual:

- Valor original
- La unidad de temperatura original
- El valor respuesta, es este... el resultado de la conversión
- Y la unidad a la que se está convirtiendo, solo que aquí pues es un Kelvin (`"K"`), ¿vale?

¡Listo! Guardamos bien bonito, ¿sale?, y este... echamos a andar para evitarnos cualquier situación.

Y lo mismo: nada más mandamos a llamar al método en cuestión, que se supone que aquí debería de ser algo
como esto:

```
http://localhost:8082/api/temperatura/convertir-celsius-a-kelvin?valor=20
```

Y dice que ahí está: **293.15**. Pues creo que sí hace su chamba, ¿no? Si le ponemos cualquier otra
cosa pues simplemente cambia y vamos verificando. Si da esos resultados, pues ya la... ya lo hicimos, ya
lo hicimos.

Les toca a ustedes terminar los otros.

---

## Bloque 8 · Clientes: `curl` y `jq` (minuto 36:45 a 39:20)

*(Explicación del uso de herramientas CLI como cURL, jq, e interfaces gráficas.)*

**Profesor:** Bueno, su compañero dijo hace ratito que lo mejor es a lo mejor jugar con... con... con
`curl`, que es lo ideal, que está bien, no pasa nada. De todos modos en términos de clientes para...
para esta cosa, pues pudiéramos decirle:

```bash
curl -s "http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=10"
```

Y como no quiero escribir nada, vamos a poner esto tal cual está entre comillas, cerramos la comilla y
ahí está la respuesta: hace lo mismo.

Pero pues solo aparece en su versión original; yo lo quiero ver así de bonito, bien estilizado. Pues lo
mismo me funciona, solo que al final le puedo agregar un pipe (`|`), ¿vale?, para ver si esta cosa lo
puede imprimir en bonito, y es `jq`:

```bash
curl -s "http://localhost:8082/api/temperatura/convertir-a-fahrenheit?valor=10" | jq
```

Nada más para que lo muestre en forma de encabezados, bonito, con formato y ya, ¿sale? Nos dice qué
cosa envía, y bueno, ay, ustedes disculparán los tonos de la cosa esa, por eso no se ve, pero pues aquí
dice:

- `valorOriginal`: 50 *(sic: con `valor=10` el original es 10 y la respuesta es 50; así lo dice el
  profesor en la grabación)*
- `unidadOriginal`: `"°C"`
- `respuesta`: ...
- `unidadRespuesta`: ...

¿Vale? Y ya.

**Profesor:** Señores, les quedan 26 minutos, tiempo suficiente para terminarlo. Y según yo, con eso
podemos dar inicio a esto...

---

## Bloque 9 · Diapositivas: qué se está cubriendo (minuto 39:20 a 40:20)

*(Muestra diapositivas sobre los conceptos que se están cubriendo en la práctica.)*

**Profesor:** Eventualmente, jóvenes, aquí estamos ya revisando el primero: el **verbo GET**. Segunda
cosa que estamos revisando: **códigos de respuesta**, porque a lo mejor no lo visualizamos, pero esto
nos está devolviendo un **200** (`HttpStatus.OK`), ¿vale? A lo mejor no lo conceptualizamos, pero nos
está devolviendo un 200, ya va el primero. **Endpoints**, porque construimos el endpoint con el que
estamos trabajando, ¿sale?

Señores, lo demás lo revisamos mañana en el laboratorio, pero terminen este ejercicio, ¿va?
Entreténganse en lo que yo arreglo unos asuntos por ahí.

---

## Bloque 10 · Postman / Insomnia (minuto 40:20 a 43:30)

*(Muestra cómo configurar Postman o Insomnia.)*

**Profesor:** Es más: vamos a jugar con este. Ya ustedes elijan con qué trabajar. Vamos a crear una
colección —funciona así, se crea una colección solo para organizar— y se llama **Conversor
Temperaturas**. Vamos por un Create, me crea el espacio, y pues a lo mejor vamos con el primer request
que necesitamos, que es un GET. Y según yo, pues de hecho nada más es pegarle la liga que tenemos, ¿va?

Siendo este... decentes utilizando la herramienta, estamos pasando un parámetro. Ese parámetro pues en
los... en este caso se lo pasamos en la URL. Si no lo quieren pasar en la URL o más bien quieren
utilizar la herramienta, pues le dan agregar —bueno, ya había uno, así se agrega—:

- **Name**: `valor`
- **Value**: `50`

Y ya, le pasamos el argumento o el parámetro, ¿sale?

Eso quiere decir —vamos a ver si es cierto— que si se lo quito a la URL, funciona. Y en el sentido
estricto, la URL que se le pega aquí no lleva el signo de interrogación para indicar el parámetro, pero
está agregado como parámetro en el apartado de los **Query Params**, y el preview de la URL vuelve a
quedar así, ¿sale? Si a esta cosa le cambian el valor, ahí se ve el preview del parámetro, le dan al
Send y ahí está la respuesta:

- Código de respuesta: **200 OK**
- Tiempo
- Y demás cosas

Y con eso tienen chamba. Termínenlo, ¿vale?

---

## Bloque 11 · `application.properties` y el puerto (minuto 43:30 a 45:10)

**Profesor:** El `application.properties` en realidad lo único que tiene en este momento es: por
defecto le aparece el `spring.application.name`, que es el nombre del proyecto, el valor que se le
asocia al proyecto. Lo único que le agregamos para ir empezando fue `server.port`. Si no se lo ponemos,
funciona de todos modos; la diferencia es que va a buscar el puerto 8080.

Es decir, ya que lo estamos viendo gráficamente: esta cosa dice 8082, le damos un Send y pues me dice:
«Error: no se puede conectar al servidor», porque se apropia por defecto del 8080, a menos que le
cambiemos el puerto. Es lo único que hace esa propiedad. Vamos a dejarlo como estaba:
`server.port=8082`, ¿vale?

Entonces, señores, eso es un DTO.

---

## Bloque 12 · Teoría: el DTO como clase estándar con Lombok (minuto 45:10 a 48:15)

*(Explicación teórica: cómo se vería el DTO si fuera una clase estándar con Lombok en lugar de un
`record`.)*

**Profesor:** Si dicen: «Es que quiero crear una clase para un DTO», por supuesto que se puede. Y la
clase a lo mejor quedaría como:

```java
package com.ipn.mx.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversionResponse implements Serializable {
    private Double valorOriginal;
    private String unidadOriginal;
    private Double valorRespuesta;
    private String unidadRespuesta;
}
```

El típico `implements Serializable` y aquí sí, con ayuda del bonito Lombok: `@Data`,
`@NoArgsConstructor`, `@AllArgsConstructor`, y ¿por qué no?, `@Builder`. Y simplemente tendría
`private Double valorOriginal`, `private String unidadOriginal`, `private Double valorRespuesta`,
`private String unidadRespuesta`, y ya. Si es que lo van a hacer en términos de clases, ¿vale?

Y claro, si lo van a hacer con clases, esto cambia... o cambia un tantito; bueno, no tanto, pero sí. De
hecho ni siquiera cambiaría, así quedaría y debería de funcionar igual.

---

## Bloque 13 · Avisos y trabajo en máquina (minuto 48:15 a 55:40)

*(Revisión de alumnos y avisos sobre laboratorios y fechas. Los alumnos programan en sus máquinas y
hacen consultas individuales entre el 49:50 y el 53:35.)*

**Profesor:** Está bien, está bien, yo no he dicho nada.

*(Pasa lista mental / cuenta alumnos)*: 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 23... Bueno, aguantaron
hasta la fecha del 17 de septiembre.

Y supongo que a los que dieron de baja por no haber tomado la que estaba antes, ¿no les dieron chance
de inscribir? ¿Sí te dieron chance?

**Alumno:** Sí, pero es que yo vengo de movilidad y me dieron de baja porque no pasaron la lista, y
ahorita ya me la van a inscribir.

**Profesor:** Ah, bueno. Qué bueno que estás en movilidad.

Sé que al menos hay tres que no han cursado la deserción...

*(Pausa mientras los alumnos programan en sus máquinas.)*

**Profesor:** Oigan, por cierto: **mañana nos toca laboratorio, pero nos toca en el Laboratorio 3.** Ya
nos van a cambiar. Los jueves es en el 3, ¿vale?

---

## Bloque 14 · Spring Initializr y documentación de Spring (minuto 55:40 a 59:00)

**Profesor:** Si van a... si quieren desvelarse... llamarse en sus noches de insomnio que sea su
documentación, que sea su biblia en esas noches de insomnio cuando no tengan nada de... ganas de hacer
nada o en lugar de ponerse a maratonear este series, se ponen a leer, ¿sale?

Por ejemplo, y aquí están todos los proyectos que tienen soporte en esta cosa: Spring Boot, Cloud,
Data, Integration, Batch, Security... Digo, eventualmente vamos a jugar con Data, con Security, este...
Security definitivamente, y tienen herramientas de desarrollo, por ejemplo tienen **Spring Tools**: y
ahí está el plugin para Visual Studio Code, el STS lo pueden descargar en standalone, este... bueno, lo
que se les ocurra: para Eclipse o para todas estas cosas raras.

Y si no, pues también siempre pueden acudir a la vieja confiable: es decir, al **Spring Initializr**
(`start.spring.io`), y ahí está el Initializr:

*(Muestra en el navegador start.spring.io, minuto ~57:00.)*

**Profesor:** Y pueden crear el proyecto desde acá. Lo que les va a generar es un empaquetado, un
`.zip`, lo descargan, lo desempaquetan y ya lo pueden utilizar. Funciona igual: por ejemplo, proyecto
Maven, lenguaje Java, agarramos el generalmente aceptado (no snapshot), y empiezan a llenar los datos.

Por ejemplo: Java, el package, 26, y de este lado agregan todas las dependencias que necesitan:

- DevTools
- Lombok
- Web
- Por ahí debe decir Data (Spring Data JPA)
- PostgreSQL / MySQL
- Validation
- Security

Y le dan «Generate» y ya. Esa cosa les debe de generar un empaquetado, lo descargan, lo abren y lo
pueden correr. No debería de tardarse.

Por ejemplo: ahí está el ZIP, se desempaqueta y ya lo usan.

---

## Bloque 15 · Dudas finales (minuto 59:00 a 64:47)

*(Resolución de dudas con alumnos en sus pantallas. Hacia el final el profesor se acerca a revisar que
las respuestas JSON y los códigos de estado HTTP 200 se muestren correctamente en Insomnia y Postman,
minutos 61:30 a 64:47.)*

**Alumno:** Profe, ¿cómo era la fórmula de Fahrenheit a Celsius?

**Profesor:** `(fahrenheit - 32) * 5 / 9`.

**Alumno:** ¿Y de Celsius a Kelvin?

**Profesor:** `celsius + 273.15`.

**Alumno:** ¿Y de Kelvin a Fahrenheit?

**Profesor:** `(kelvin - 273.15) * 9 / 5 + 32`.

**Profesor:** Ya aprovechando que tienen que hacer los cursos... Está bien. Con calma. Listo, señores.
**Mañana continuamos en el Laboratorio 3.**

---

## Apéndice · Estado del ejercicio de conversión

Referencia rápida de las seis conversiones y de dónde salió cada fórmula. Las marcadas como
*derivada* no las dijo el profesor en la grabación; se despejan de las otras.

| Conversión | Fórmula | Fuente |
|---|---|---|
| Celsius → Fahrenheit | `(c * 9 / 5) + 32` | Hecha en vivo (bloque 5) |
| Celsius → Kelvin | `c + 273.15` | Hecha en vivo (bloque 7) |
| Fahrenheit → Celsius | `(f - 32) * 5 / 9` | Dictada por el profesor (bloque 15) |
| Kelvin → Fahrenheit | `(k - 273.15) * 9 / 5 + 32` | Dictada por el profesor (bloque 15) |
| Kelvin → Celsius | `k - 273.15` | *Derivada* de C → K |
| Fahrenheit → Kelvin | `(f - 32) * 5 / 9 + 273.15` | *Derivada* de F → C y C → K |

**Pendientes que salieron en la clase**

- Terminar los 4 métodos que faltan (F→C, F→K, K→C, K→F). El profesor dio 30 minutos en el aula y
  dijo que lo demás se ve "mañana en el laboratorio".
- Probarlos con navegador, `curl | jq` y Postman/Insomnia (colección "Conversor Temperaturas").
- Laboratorio del jueves: **Laboratorio 3** (cambio de salón).
