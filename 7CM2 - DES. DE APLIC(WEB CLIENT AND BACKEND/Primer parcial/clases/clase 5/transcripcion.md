# Clase 5 — Arquitectura Hexagonal y Microservicios

- **Materia**: C704 · Web client and backend development frameworks (grupo 7CM2)
- **Fecha**: lunes 7 de septiembre de 2026
- **Duración aproximada**: 1 hora (marcas de tiempo del 00:00 al ~58:00)
- **Cierre**: con esta sesión termina la Unidad I
- **Origen**: transcripción detallada de la grabación, ya limpia (no requirió el pipeline de
  `pdftotext` + decodificación de jerga que sí necesitaron las clases 1 a 4)
- **Artifact reconstruido**: [`clase5-hexagonal-microservicios.html`](clase5-hexagonal-microservicios.html)

> Los tecnicismos de backend y arquitectura de software se respetan tal cual. Los pasajes entre
> paréntesis y en cursiva son acotaciones de lo que pasaba en el aula, no palabras del profesor.

---

## Arranque y revisión de la tarea

**Profesor:** Esperemos que... que sí nos tengan... pues ya... todo... bonito. Eh... A ver.

*(Pausa mientras acomoda equipo y busca conexiones / 00:10 - 00:22)*

**Profesor:** Vamos a terminar lo que tenemos pendiente de teoría. Vamos a continuar. Ahora sí, ¿se
acuerdan que había una tarea y era que me trajeran la réplica fiel del minisitio de nuevo ingreso?
¿Ya quedó?

**Alumno:** Ya.

**Profesor:** ¿Cuánto se tardaron?

**Alumno:** Dos minutos.

**Profesor:** Realmente no es tanto. ¿30 segundos? Con esa vamos a trabajar.

*(El profesor intenta conectar el proyector / 00:58 - 02:45)*

**Profesor:** Fuente HDMI sin señal. O voy por otro... Bueno, a ver. Está bien. Vamos a recapitular
algo en lo que ahorita me doy una escapadita y voy por otro adaptador.

---

## Recapitulación en el pizarrón: de la tabla a la clase

**Profesor:** ¿Qué hicimos? Hasta ahorita, ¿qué hicieron más bien? A partir de una tabla de base de
datos, vamos a suponer algo como esto:

*(Escribe en el pizarrón)*

Se supone que lo que hicimos fue... bueno, lo que ya hicieron, fue construir una clase que era
equivalente a la entidad. Con atributos privados a la clase —solo voy a poner uno porque no quiero
poner los demás—, con un constructor al menos con argumento cero o con un constructor con
parámetros, ¿sale?, como ustedes consideren. Más métodos getters, más métodos setters, más a lo
mejor un `toString()`. Al menos el `toString()`, ¿sale? Y el resto pues es el `equals`, `hashCode` y
demás. Y ya está, ya tenemos una clase, ¿sale?

Bueno, cuando nosotros escribimos código o hacer esto, la verdad a veces como que da hueva porque es
muy repetitivo, si es cierto. ¿Pues qué creen? Cuando haciendo uso de alguna que otra herramienta,
pues pudiéramos decirle que la clase tal vez pudiera quedar así: `Category`, simplemente `private`,
por ejemplo, por ponerle un valor, `idCategory`, `private String name`, y `description`. Y la clase
pudiera quedar así, sin más ni más.

Pero tendríamos que auxiliarnos a lo mejor de alguna dependencia de terceros para decirle algo como
esto:

*(Escribe anotaciones de Lombok en el pizarrón: `@Data`, `@Getter`, `@Setter`,
`@AllArgsConstructor`, `@NoArgsConstructor`, `@Builder`)*

E inclusive algo como esto. ¿Sale? Y la clase quedaría así, solo que quedaría muy decorada. ¿Quién
hace esta chamba de aquí? Pues a lo mejor una dependencia de terceros que se llama **Lombok**,
¿sale? Y ayuda bastante según sea el caso, según lo que quieran... si quieran o no quieran escribir.

---

## Arquitectura Hexagonal en el pizarrón

**Profesor:** Pero bueno, ¿qué toca ahorita en la lámina? **Arquitectura Hexagonal**, es la que
sigue, ¿sale? ¿Y esa cosa en qué divide? Pues por definición divide en **Dominio**, en **Aplicación**
y en **Infraestructura**, ¿sale? A final de cuentas siguen siendo tres capas, ¿sale? El detalle es
que cada capa tiene su chiste. En cada una de las capas se agregan justo los **puertos** y los
**adaptadores** según sea el caso.

Pero este cuate de aquí particularmente dice que lo que se modela es un **objeto de dominio**, ¿sale?
Y el objeto de dominio es lo que en su definición, cuando creamos la tabla, es aquella persona, idea,
cosa con características propias que pueden ser descritas que existen en el mundo real y que pueden
ser identificables unas de otras. ¡Completita!

Entonces, este de aquí que tiene esta variante tendríamos que escribir esa clase tal cual, ¿sale?
Esa clase tiene atributos privados a la clase, métodos get, métodos set, este... constructores y
reglas de validación, ¿sale? Todas las reglas de negocio deberían de ir dentro de la clase, y es una
clase Java completita, **sin decoradores** y sin más, ¿sale? Porque en sí lo que pretende representar
esa entidad es una categoría.

¿Qué cosa puedo validar en una categoría? Bueno, si ya tengo una categoría llamada «Deportes», pues
no puedo agregar otra que se llame «Deportes», ¿vale? En principio sí se puede porque esta cosa es
autonumérica, entonces da lo mismo lo que le ponga porque puedo tener como cinco «Deportes» del 1 al
5, ¿vale? Pero bueno, lo que tengo que validar, por ejemplo, es que el nombre pues a lo mejor no...
sea único, ¿no? Sea ese atributo que a lo mejor discrimine si puede o no repetirse.

Por ejemplo, cuando nosotros nos hemos metido a internet a registrarnos en algún momento para hacer
cualquier cosa y luego dice: «El correo ya existe. Ya me registré una vez, ya no lo puedo volver a
usar». Ah, bueno, ¿por qué? Porque a nivel base de datos a lo mejor esa cosa tiene por ahí habilitado
el atributo `UNIQUE`, ¿no? ¿Sale? Y tampoco nos... si bien aquí validamos, eso no quiere decir que a
lo mejor a nivel base de datos pues pongamos cláusulas `CHECK` para determinar que realmente sea una
cadena, que sea de una longitud, que tenga un valor... Por ejemplo, si es un precio, el precio de un
producto, pues que no sea negativo, este... lo que se les ocurra, ¿sale? Todas esas cuestiones.

Entonces, de allá de arranque esa cosa debería de tener para empezar:

1. El **objeto de dominio** que estamos modelando.
2. El manejo específico de las **excepciones**, es decir, que la cosa esa no existe, este a lo mejor
   no se encontró, no existe, lo que sea.
3. Y alguien que me va a poner qué cosa voy a poder hacer con él en algún momento, ¿vale? Simplemente
   las reglas o la interfaz donde viene... que va a servir como **puerto** para poder pues conectar lo
   que se nos ocurra, ¿vale?

### La capa de Aplicación: casos de uso

Ahora, en términos de Aplicación, pues a lo mejor en términos de aplicación son todos los **casos de
uso** que nosotros querramos agregar. Entonces, vamos a tener los servicios, sí, ahí vamos a juntar
los servicios. Pero eh... de alguna forma pues vamos a separar en términos de casos de uso.

Por ejemplo, si tengo a este mono por acá, ¿sale?, si a lo mejor tengo como casos de uso:

- Crear
- Actualizar
- Buscar
- Eliminar

Pues si lo vemos en la figurita nos damos cuenta que son casos de uso por separado, ¿vale? Bueno,
pues esto de aquí implica que cada uno sea una clase, curiosamente. Específicamente una **interfaz**,
¿vale? Que exista uno por cada una de las acciones, porque en realidad se requiere. ¿Por qué? Porque
bueno, por ahí hay uno de los principios de lo que ya leyeron que dice que cada cosa debería de tener
solo una única responsabilidad (**Single Responsibility Principle**). Entonces la única
responsabilidad de esto pues es crear, eliminar y demás. Bueno, eso normalmente definimos los
contratos en términos de aplicación.

### La capa de Infraestructura: los adaptadores

¿Y cómo sabemos? En algún momento, pues sí, tengo una clase Java pero pues necesito que esta cosa se
vaya a mi base de datos, que pueda hacer este match, ¿sale? Pues yo necesito que esa cosa se almacene
en la base de datos, en algún lugar. Bueno, la intención es esta: estos dos de alguna manera se van a
tener que conocer mutuamente, ¿sale? Pero este que me determina los bordes, los lados, los laterales,
es donde a lo mejor ya nos peleamos con la estructura específica.

Voy a jugar con algo que me permita insertar a base de datos: JDBC, JPA, lo que se les ocurra. Voy a
jugar con algo que me permita hacer consultas: necesito un API REST, por ejemplo, ¿vale? O no quiero
jugar con REST, quiero jugar con GraphQL, por ejemplo. Ah, bueno, pues ahí va el adaptador. Entonces
empezamos a definir los adaptadores.

Para este ejemplo pues aquí es donde tenemos que indicarle: «Ah, mira, a ver, espérate. Vamos a
utilizar, por ejemplo, JPA». Por lo tanto debe de haber una clase de tipo entidad ya con los
decoradores específicos de JPA que a lo mejor me permiten mapear entre la clase... esta cosa, la
tabla y la clase de tipo entidad. Pero curiosamente para poder acceder a él lo voy a hacer a través
de algún objeto que me permita hacer match entre mi clase entidad y mi clase de dominio, ¿vale?

Entonces, ¿qué tiene de ventajas a lo mejor agarrar esto? Pues las ventajas que tiene es de que si
hoy juego con MySQL, pues mañana juego con Postgres, mañana juego con cualquier otra cosa. Si hoy
juego con REST, mañana juego con SOAP, pasado juego con este... GraphQL, lo que se les ocurra.

Entonces, en algunos casos sí hay que hacer cambios drásticos porque no todo es tan transparente. Por
ejemplo, eh... si quiero cambiar la base de datos, pues tengo que cambiar el conector, el driver, se
requiere, porque pues el driver que cargué para MySQL no es genérico. Entonces me funciona para MySQL
pero voy a conectarme con un Postgres.

Y detallillos: la forma en que trabaja GraphQL es diferente a como trabaja este SOAP, es diferente a
como trabaja REST. Pues nada más creo el adaptador y le vamos pegando cosas. Más o menos así
funciona.

Pues yo no sé si ya pasó el prefecto. Jefe de grupo, le encargo mis cosas, voy por mi... por otro
adaptador porque no me tardo.

*(El profesor sale del aula a buscar un adaptador y regresa / 15:30 - 18:50)*

---

## Con proyector: la presentación

*(Se proyecta la presentación en pantalla / 19:00)*

**Profesor:** En teoría vamos a pensar algo como esto. Eso son como que sus objetivos por poner algo.
¿Con qué vamos a jugar? Con Java, con Spring, pues lo vamos a poner en términos de Spring, ¿vale? San
se acabó, ¿vale?

Bueno, ¿qué es? Todo lo que tenga que ver con cuestiones de arquitectura es chamba de ustedes, ¿eh?
Es decir, agarran, se echan un volado y deciden cuál es la mejor arquitectura para su propuesta de
desarrollo. Punto. Entonces simplemente es eso, son decisiones, ¿vale?

¿En qué van a tener? Pues ¿se acuerdan que les decía?: vayan con el arquitecto, pídanle que construya
su casa y les diga: «Ah, pues sí, mira, quiero unos ventanales, este... déjame el espacio para poner
una fuente, este... quiero una torre porque pues no sé, jugaba Calabozos y Dragones», y lo que a
ustedes se les ocurra, ¿no? Lo que sea. Entonces son decisiones de la estructura global de los
componentes, las asociaciones que va a tener su sistema.

Ahora bien, ¿por qué dicen que el software es vivo? Porque esa cosa cambia a cada rato, ¿vale? Y
bueno, ahí también ustedes tienen que justo poder determinar a lo mejor los límites porque pues
aparece una nueva característica en algún momento que quieren incorporar. «Ahora quiero que...» —el
típico—: «cámbiale el color, agrégale esta funcionalidad», y demás. Entonces esto va a ir
evolucionando.

O es el típico: vamos a agarrar este stack para desarrollarlo. Mañana apareció un nuevo... «Oye,
acaba de aparecer esto, vamos a cambiarlo». O sea, sí, a lo mejor amerita que a veces tenemos que
estar al día con las evoluciones en cuestión de tecnología. Puede ser que sí, ¿sale? Pero en realidad
el análisis y la decisión depende de justo de que ustedes pues vean exactamente qué tanto les va a
pegar, qué tanto les va a afectar.

Entonces, propósito: pues controlar de alguna manera la manera en que vamos a construir o que van a
construir el software.

*(Pasa a la siguiente diapositiva)*

¡Ah! Esta que está aquí: el «Tío Bob» (Uncle Bob) hizo ese comentario cuando estaba hablando de justo
temas de arquitecturas, ¿vale?

> «La arquitectura trata sobre las decisiones que son difíciles de cambiar más adelante. Una buena
> arquitectura no impide el cambio, simplemente lo permite.»

Debería de. Bueno, ahora, ¿qué onda con las arquitecturas tradicionales en capas y demás? Pues justo
cuando jugamos con algo de capas, en realidad lo que hacemos es... hay un gran... el **acoplamiento**
entre los componentes realmente es muy alto, ¿vale? ¿Qué nos genera? Pues el tema de probar, de
modificar, ¿sale? El que esté casado con alguna tecnología, que esté bloqueado y que esté cerrado,
pues realmente el riesgo es muy alto.

Imagínense aquella cosa de allá. Ah, bueno, también aclaremos, ¿eh? Hoy les estoy vendiendo
Arquitectura Hexagonal, ¿sale? Obviamente para la tarea que ustedes hicieron, donde me están
vendiendo arquitecturas limpias o arquitectura en cebolla, pues a lo mejor es arquitectura en cebolla.
El día que tocó hablar de arquitectura en tres capas, pues obviamente lo mejor fue la arquitectura en
tres capas. ¿Estamos de acuerdo? En ese sentido, sí, claro.

Entonces, el problema: algo como eso, ¿vale? Entonces pues ahí el acoplamiento es muy fuerte en el
sentido de: «Ah, pues si vamos a cambiar el framework, pues a lo mejor probablemente, si no seguimos
buenas prácticas, es que tengamos que modificar prácticamente todo», ¿vale?

Ahora bien, la solución: pues dice: «Ah, pues agarren Arquitectura Hexagonal», ¿no?, ¿vale? ¿Por qué?
Porque a lo mejor lo único que vamos a crear...

*(Pasa diapositivas explicando los hexágonos y capas / 24:30 - 27:00)*

### Digresión: el cable de la Plaza de la Computación

Bueno, entonces tenemos justo todas estas partes, todas estas fronteras. Entonces la intención es
justo aislar el núcleo del negocio y todo lo que tenga que ver con tecnologías no debiera de afectar
tanto.

Imagínense... ¿qué pudiera ser? Ah, sí. Fui a la Plaza de la Computación el fin de semana a comprar
un cable, ¿sale?, porque necesitaba un cargador. Pero no encontré literalmente el que quería. O
bueno, ahí al primero... yo ya no me la paso buscando, llego al primer puesto y: «Dame un cargador
para esto, un cable para esto». Y me ofrecieron un cablecito que tenía un adaptador, que era
literalmente USB normal a USB normal, pero tenía una pieza intercambiable, una piececita que la
alzabas y se volvía USB a USB-C, y si la quitabas del otro extremo era de C a C, curiosamente.

Entonces, ese es un adaptador. ¿Qué necesito? «Ah, pues es que necesito conectar MySQL». Está bien,
ponle el adaptador y san se acabó. «Es que necesito esta tecnología». Ponle el adaptador y san se
acabó, ¿sale?

O el ejemplo típico: bueno, ese tiene tres... Hay contactos que tienen solo dos piezas, pero el cable
de su computadora tiene tres patitas. ¿Qué es lo que hacen? Pues van y compran una piececita naranja,
un adaptador que pues tiene tres piezas y lo pasas, ¿no? Y ya lo conectan. Eso es un adaptador,
¿vale?

Entonces lo único que hacemos es justo empezar a cambiar adaptadores, ¿vale?

### Principios y origen del patrón

*(Muestra lámina con principios / 27:00)*

Ahí está. Regla: el dominio de negocio **jamás** debe depender de bases de datos, de interfaces de
usuario, de nada, ¿vale?

*(Lee citas históricas de Alistair Cockburn sobre Puertos y Adaptadores)*

¿Cuándo surgió? Bueno, aquí está. Propuso el patrón original denominado «Puertos y Adaptadores» para
superar las diferencias de la arquitectura tradicional en tres capas, la problemática que suponía,
¿sale?

Similares: les tocó averiguar eso: **Clean Architecture**, está el **Onion Architecture** y demás. La
regla es que todos se centran en el mismo principio: el dominio debe de estar en el centro.

Y este personaje en particular que propuso la arquitectura de puertos y adaptadores dijo:

> «Crea tu aplicación para que funcione sin interfaz de usuario, sin bases de datos, de tal forma que
> puedas ejecutar pruebas de lo que se les ocurra contra tu aplicación.»

¿Vale? Entonces el elemento central bueno pues se vuelve... o los objetos de negocio.

¿Qué principios?

- Bajo acoplamiento
- Alta cohesión
- Independencia del dominio
- Separación de responsabilidades
- Inversión de dependencias, ¿sale? ¿Por qué? Porque en realidad jugamos contra interfaces, nada más.
- Y facilidad de pruebas. En teoría podemos ir probando cada cosa y para eso utilizamos, no sé,
  **JUnit, Mockito, ArchUnit**, lo que se les ocurra, ¿vale?

Ahora bien, ¿cuál es la estructura? Esa es la estructura:

- **Infraestructura**: mundo exterior y adaptadores.
- **Aplicación**: quien se encarga de hacer la chamba, el orquestador, el puerto.
- **Dominio**: pues el objeto que estamos modelando, ¿vale?

### La capa de Dominio, en concreto

Entonces podemos empezar a jugar con ellos de tal forma que para la capa de Dominio, a lo mejor
pudiéramos tener algo como eso: son responsabilidades internas, objetos con identidad, entidad de
negocio como la clase que estaba allá, ¿sale? Es decir, como ustedes consideren. Es una clase Java
pura con atributos privados a la clase, mutators, accessors, un `toString()`, un `equals()` y con sus
respectivas reglas de negocio, ¿vale?

Por lo tanto pues no me interesa saber si ese objeto que estamos modelando, si ese objeto de dominio
en realidad representa una tabla en MySQL, en Postgres, si estamos jugando con Spring, si estamos
jugando con Hibernate puro, si estamos jugando con HTTP, si estamos jugando con cualquier controlador
de tipo MVC. En realidad no tiene por qué importarnos.

¿Entonces cómo quedaría? Más menos así: allá está mi tabla. Pero a final de cuentas, ¿necesito mi
tabla? Sí, sí necesito mi tabla porque a final de cuentas es la que estoy modelando, ¿vale? ¿Y qué
estamos modelando? Bueno, según eso, cuando aparezca: una categoría: `id_categoria`, `descripcion` y
`nombre`. Simple.

Okay, vamos a crear la clase. ¿La clase qué atributos tiene? Pues a lo mejor nombre, descripcion
—para que cupiera me comí el id, si quieren—, y ahí está el método que a lo mejor hace alguna
validación. Valida en términos de si el nombre es igual a nulo, si el nombre se encuentra vacío,
este... si lo que se les ocurra. Pero está dentro de la misma clase, ¿vale?

Y ya, terminamos bien felices, bien contentos.

### La capa de Aplicación, en concreto

Bueno, pasamos a la siguiente. Y aquí empezamos a jugar con casos de uso, definimos los casos de uso.
Imaginemos que tenemos algo como esto: crear categoría, consultar categoría, listar categoría,
actualizar categoría, eliminar categoría, lo que se les ocurra: modificar el estado de una categoría
(si está activo o no), lo que sea, ¿vale?

Entonces, ¿quién se va a encargar de esa magia? Pues la capa de Aplicación. Por lo tanto va a definir
los contratos, las interfaces con las que va a poder jugar de algún modo, ¿sale?

A modo de ejemplo, pues algo como eso, ¿vale? Si estoy con el caso de uso «Crear categoría» para el
create, por ejemplo: «Ah, bueno, pues creamos la interfaz `CreateCategoryUseCase`,
`Category createCategory(Category category)` y recibe algo». ¿Qué? Quién sabe, pero recibe algo,
¿sale?

Y ya. Entonces tengo una interfaz para el caso de uso crear, el que estaba por allá en el dibujito que
está detrás de la lona. Entonces necesito otra interfaz para el caso de uso listar, otra interfaz para
el caso de uso eliminar, otra interfaz para lo que sea.

Entonces, ¿cuántas clases llevamos? Pues en realidad hasta ahorita... así a modo de ejemplo, la de la
lámina pasada y esta, dos. Sí, pero **una por cada caso de uso**. Entonces de arranque un CRUD tiene
cinco, ¿vale? Aquí deberían de haber cinco de estas cosas, más las otras que están ocultas, ¿vale?

Bueno, entonces justo el dominio y la aplicación conocen esta interfaz, pero no nos interesa saber si
vamos a agarrar JDBC, JPA, este... lo que sea, cualquier tecnología, ¿vale?

### La capa de Infraestructura, en concreto

La Infraestructura: pues simplemente son los conectores con quién vamos a jugar. Por ejemplo:
persistencias, llamadas a APIs externas, bases de datos, JDBC, JPA, controladores REST, interfaces de
usuario por línea de comandos, este... lo que sea que se les ocurra para que pueda interactuar con
él, ¿sale?

A modo de ejemplo, si vamos a hablar de adaptadores, pues entonces a lo mejor necesito crear un
adaptador que se conecte a MySQL, ¿vale? Y aquí es donde a lo mejor ya viene la funcionalidad
específica según con lo que estemos jugando. Si aquí estamos jugando a lo mejor con JPA y vamos a
hacer el mapeo de la entidad: «Ah, bueno, pues a lo mejor ahí pudieran venir los métodos `save()`,
`update()`, este `delete()` o JPA puro: `persist()`, bueno en Hibernate: `persist()`, este
`merge()`, lo que sea».

---

## Qué cambia y qué no cuando cambias de tecnología

Más o menos así. Ahora, ¿qué pasa si hay cambios o cuáles son sus ventajas? Cambios: sí hay cambios,
en realidad no es transparente. Si cambiamos MySQL por cualquier otro manejador de datos, si estamos
jugando con un `pom.xml`, ¿vale?, por ejemplo, pues hay que indicarle que vamos a jugar con este en
lugar de este y por lo tanto necesito que se traiga el driver, ¿sale?

También pues necesito cambiar la cadena de conexión en el `application.properties`. Bueno, y no solo
la cadena de conexión: si el usuario es diferente y la contraseña es diferente, pues también hay que
modificar esas líneas. Pero si a lo mejor existe una base de datos que ya se pusieron de acuerdo que
va a tener a lo mejor el mismo nombre, mismo usuario, misma contraseña aunque sea este diferente
manejador, pues nada más cambiamos la cadena de conexión.

¿Hay que cambiar el dialecto? Sí y no. No es necesario. O sea, sí, existe, se puede, se debe,
¿debería? Sí, ¿sale? Pero en versiones recientes en realidad ya no es necesario; cuando obtiene la
conexión, en automático jala este... el dialecto con el cual está trabajando, ¿sale? Si es un Postgres
pues va por un `PostgreSQLDialect`, o si es un MySQL, si es un Oracle, si es un lo que sea. En
realidad lo determina cuando obtiene la conexión.

**¿Qué cosa no va a cambiar nunca?**

Bueno, ninguna clase del dominio va a cambiar. Ninguna clase que se encargue de la chamba de
determinar qué casos de uso va a implementar va a cambiar, ¿sale? El adaptador, pues para este caso
JPA, pues tampoco, porque no es específico del SQL; en realidad trabaja a nivel objeto: lo que espera
recibir es un objeto y san se acabó.

Bueno, claro: le agregaron más atributos a la tabla... van a tener que cambiar, ahí sí, las clases del
dominio, ahí sí las clases de tipo entidad, porque le agregaron más atributos, ¿sale?

¿Cambiamos de REST a GraphQL, por ejemplo? ¿Qué cambios? Pues hay que crear un nuevo adaptador. Se
echan un volado y deciden si borran lo que ya tenían o si lo mantienen; daño no les va a hacer,
simplemente le están dando soporte a otra nueva tecnología, ¿vale? Entonces lo demás no cambia, los
casos de uso siempre son reutilizables.

---

## Desventajas: el costo en clases

**Desventajas:** ¡Híjole! ¿Qué tan difícil es? Sí y no, ¿sale?

Sí, por la forma en que se estructura nada más. Son un montón de clases para hacer algo que queda
en... que se puede solucionar en tres clases simples, por ejemplo. Es decir, yo necesito conectarme,
crear un API REST —para el ejemplo del API REST—, necesito crear un API REST que se conecta a MySQL
que me traiga todas las categorías de los productos existentes. ¿Qué necesito? Pues un controlador,
ya está, una clase. Un servicio, ya está el servicio, ¿sale?

Pero vamos a ser decentes, ¿vale?, y al servicio le vamos a crear la interfaz del servicio y la
implementación del servicio: van tres clases, ¿vale? Vamos para atrás: ¿qué necesito? Un repositorio.
Ahí está el repositorio. Si quieren ser muy puros, vamos a crear la interfaz del repositorio y la
implementación del repositorio: ah, pues ya van cinco clases; si no, llevamos cuatro. Después
necesito la clase de tipo entidad, la que representa la tabla, pues el Entity class, solo es una
clase: van seis o van cinco según sea el caso, ¿vale?

Pero siguiendo esta cosa vamos a tener como... más de 15 clases, ¿vale?, para hacer algo que en cinco
queda. Pues a eso se refiere: es más estructura que otra cosa, ¿vale?

Por lo tanto, costo de mantenimiento: pues hay un montón de clases y tengo que ir a ver qué cosa está
funcionando y qué cosa no está funcionando, ¿sale?

**Ojo: es switchear el chip**, porque podemos decir: «A ver, yo tengo una clase de tipo entidad y
representa a mi tabla. Estoy haciendo ORM, y el ORM por definición hace un mapeo entre las cosas que
viven en el modelo relacional y las cosas que viven en el mundo orientado a objetos, ¿sale? Hace
match. Tengo una tabla, tengo una clase, ¿vale? Tengo un atributo en mi clase, tengo un atributo en mi
tabla. Y así me la puedo llevar. Tengo un atributo este... que es calculado en mi tabla, por ejemplo
para un alumno: la edad. Digo, no es lo mismo la edad que tenían ayer a la edad que tienen hoy,
¿sale? Es diferente. Por un día, pero es diferente; por horas, pero es diferente, ¿vale? Entonces eso
se calcula. ¿Cómo se calcula? Con la fecha actual más tu fecha de nacimiento, ¿no? Y hacen su magia y
san se acabó. Ah, bueno, pues JPA les dice: no te preocupes, mira, hay una anotación que te dice que
el atributo es transitorio (`@Transient`), y ya, no tienes que hacer nada, ponlo y yo hago la magia.
Ah, bueno, qué padre. ¿Va?»

Así de simple. Pero aquí no, aquí es diferente. Es a lo mejor decir: mira, tengo mi clase que
representa la tabla pero a nivel objeto de dominio, a nivel conceptual; pero tengo mi clase que
representa la tabla para hacer el mapeo entre objetos, entre tabla y objeto. Entonces tengo que hacer
un mapeo justo entre mi objeto de dominio y mi objeto entidad para poder persistir el elemento. Ah,
bueno, ya le agregó un poquito más.

**Curva de aprendizaje:** definitivamente, ¿sale? Definitivamente. O sea, es estar de sentarse un
ratito, revisar, y ya después que lo hayan analizado, masticado, digerido y demás, dices: «Ah, no,
pues sí, solo es crear un montón de clases y poner las cosas donde van», ¿vale?

Ahora, esto también es cierto: ahí decía: «No es adecuado para aplicaciones simples, por ejemplo un
CRUD básico». Pues a lo mejor y no. ¿Cómo funciona? Pues a final de cuentas funciona, ¿vale?

---

## El árbol de paquetes

*(Muestra el árbol de paquetes de la arquitectura en la pantalla / 32:30)*

Hablando de estructura, la estructura queda así. ¿Qué debería de tener?

- En el **dominio**, por ejemplo: `model`, `exception`. Dentro del `model`, la clase `Category` que
  era lo que está ahí; el `CategoryException`, que es la excepción específica de: «Pues no encontré la
  entidad, algo pasó», muy genérica, ¿no?, que es una clase que es de tipo `RuntimeException`, ¿vale?
  Luego pues a lo mejor el tema de **puertos**: qué cosa en teoría voy a poder hacer, no el cómo lo voy
  a hacer, ¿sale? A final de cuentas es una interfaz, ¿vale?
- Después pues vámonos a la **aplicación**. No se preocupen, son como tres animaciones, cuatro
  animaciones, ¿sale? ¿Qué necesito? Ah, bueno, pues a lo mejor primero que nada voy a crear el
  servicio, ¿vale? Pero el servicio va a necesitar hacer uso de pues todos los casos de uso que voy a
  implementar. Entonces un caso de uso, una interfaz: ¿qué cosa? Pues insertar, borrar, actualizar,
  este... cambiar el estado, generar gráfica, generar reporte, lo que sea que se les ocurra, ¿vale? Un
  caso de uso, una interfaz. Y justo algún **DTO** que nos auxilie para poder interactuar aquí en esta
  capa, ¿vale?, que tiene cierta responsabilidad en particular.
- Luego tenemos la parte de **infraestructura**. Falta otra... otra animación, no se preocupen, se...
  va a aparecer otra grandota por acá donde viene todo. Y aquí a nivel... ¿dónde está?...
  infraestructura, pues justo volvemos a tener de nuevo el `Controller` y DTOs específicos que se van
  a encargar de validar al momento que lo recibe el cliente, ¿sale? Todas las validaciones que se les
  ocurra: esta cosa es obligatoria (`@NotNull`, `@NotBlank`), debe tener una longitud mínima, una
  longitud máxima (`@Size(min = 4, max = 50)`), este... no nulo, no debe de venir en blanco, este...
  es una dirección de correo (`@Email`), debe de cumplir esta expresión regular (`@Pattern`), lo que
  sea que se les ocurra, ¿vale?

Y bueno, ya después de eso tenemos el controlador. ¿Quién va a hacer la magia? Pues la magia, si
estamos en infraestructura, también me hace falta, ahora sí, específico: la clase `CategoryEntity`,
aquella que representa la tabla tal cual, ¿sale?, que sigue las convenciones de JPA que me dice que el
atributo `nombreCategoria` hace match contra una columna que existe en mi base de datos que se llama
`nombre_categoria`, que tiene una longitud de 50 caracteres, que es obligatorio, por ejemplo, ¿sale?
Más lo que se les ocurra, ¿vale?

Después, ahora sí, ¿con qué tecnología estoy jugando, vale? Voy a crear un repositorio, pues sí, estoy
jugando con Spring Data. Pero Spring Data tiene soporte para `JpaRepository` y para `CrudRepository`,
por ejemplo. Ah, pues a lo mejor voy a jugar con un `JpaRepository`, más la implementación de esta
interfaz ya específica, ¿vale?

Y ya. Ahora sí, ¿qué quiero hacer? Un simple CRUD. Hace ratito les decía: nada más necesito cinco
clases, seis así como que viéndome muy exquisito. ¿Un CRUD con hexagonal? Todas esas. Solo para un
simple CRUD, así queda, ¿vale?

Claro, también en términos de estructura estamos dividiendo. ¿Con quién estoy jugando? Con un
**feature** que se llama `category`. Ah, bueno, ¿qué sigue después? `product`. Pues debe de haber un
feature por ahí que se llama product con toda esa misma estructura. ¿Qué más sigue? Pues le voy a
poner control de usuarios este basado en roles y lo que se les ocurra: un feature por acá que se llama
`users` con todo eso, ¿sale?

¿Qué más tiene? Categorías, productos... Ah, pues me lo voy a llevar y voy a hacer este ventas:
necesito un cliente, pues una de clientes con toda esa estructura. Y así va creciendo la cosa esa,
¿sale?

¿Lo bonito? Pues es de que está más limpiecito, funciona, y por cierto: tenemos clases extras de
configuración, ¿vale? Para esa imagencita dice `BeanConfiguration`, porque pues a lo mejor estamos
creando repositorios, estamos creando clases Java —el concepto es **Java Beans**— de tipo
configuración: ah, bueno, pues aquí registramos todos los... las clases Java de configuración, ¿sale?

Y aquí pues el `GlobalExceptionHandler`, pues que es el nombre común para un este
`@RestControllerAdvice`, que en realidad lo que se encarga es justo de ver qué cosa está ocurriendo y
mostrar el error correspondiente según lo que ocurra. Es decir, este... «no existe», «no lo
encontró», este «ese registro ya existe», este... no sé, ¿vale? Creo que hay ejemplos.

Ahora sí: ahí está la foto si la quieren. Así debería de quedar la estructura.

Entonces cuenten las clases: son 3, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19... y este que no
está expandido que se me olvidó debe de tener como otras tres más. **Son como 22 clases.**

Entonces, para un simple CRUD, a eso se refiere con que bueno, okay, sí, vamos a agarrar
arquitecturas limpias, hexagonal, cebolla, ¡va! Tu proyecto, ¿qué tan complejo es para tener algo como
eso? ¿Es muy simple? Agárrate un MVC y ya, evítate de problemas.

---

## cURL y herramientas para probar APIs

*(Pasa a explicar herramientas de pruebas y cURL / 37:35)*

Y vamos a usarlo. Y aprovechando, matamos dos pájaros de un tiro: no sé en la siguiente unidad, la que
sigue, por ahí dice herramientas para poder interactuar con servicios. La más básica es esa: **cURL**,
¿sale?

Desde consolita nosotros podemos empezar a interactuar contra nuestras APIs y servicios, ¿sale? En la
cosa esa dice herramientas, pues esa es la básica. Ahí tenían por ahí Insomnia, Postman, REST Client y
cualquier cosa que se les ocurra. Pero bueno, como buenos ingenieros que son...

*(Risas en el aula)*

Las cosas se hacen a mano, ¿sale? Escribiendo codiguito en consola para que les diga de groserías y
bueno, vean cómo se corrige.

Entonces empezamos: `curl`, ¿sale? Le pasamos a lo mejor la petición, le decimos: «Ah, mira, ¿sabes
qué? Este cuate vive en `localhost:8080/api/categories`», san se acabó. Necesito que lleve más cosas,
por eso el brinco de línea. ¿Qué le vamos a pasar? Pues el objeto en formato JSON, porque pues en
realidad pues necesitamos que la cosa esa entienda objetos JSON, por eso el `-H` que está por ahí que
tiene que ver con los encabezados que dice `Content-Type: application/json`.

¿Qué vamos a agregar? Categoría «Cómputo», «Artículos de cómputo». Terminamos, ¿qué nos devuelve? Pues
el objeto creado. Para este caso: `idCategory: 2`, `nameCategory: "Cómputo"`,
`descriptionCategory: "Artículos de cómputo"`. Por poner un ejemplo, ¿sale?

Vamos a agregarlo: ¿qué cosa... qué vamos a agregar? Pues otro objeto, pero no le pasamos el nombre,
solo la descripción. Bueno, pues esa cosa debe de tener la capacidad de decirnos: «Error de
validación: `nameCategory`: el nombre de la categoría es obligatorio», ¿vale? No... no les puse la
clase, ahorita se los muestro, está en la definición de la clase del dominio, ¿vale?

Pero esa es la estructura de un cURL. Vamos a mostrar un GET: pues lo mismo,
`localhost:8080/api/categories`, y por ejemplo me dice que tengo esas dos categorías, ¿vale? San se
acabó.

¿Por qué necesitamos herramientas para probar APIs? Porque el navegador... ¿lo podemos ver en el
navegador web? Sí, pero solo que esa cosa solo entiende métodos GET por definición. Ya cuando queremos
jugar con los otros, cuando queremos hacer un POST, pues ¿cómo le paso el objeto, no? Díganme, ¿cómo
le paso el objeto?

**Alumno:** A menos que esté construido para que reciba peticiones POST...

**Profesor:** Bueno, pero eso es... es específico. Sí, eso es específico, ¿vale?

Entonces vamos a mostrar uno, por ejemplo esta, y me dice que es esto; o vamos a mostrar el otro y me
dice que es el de abajo. Simplemente hace una búsqueda. Ah, y como dato curioso también: si hace la
búsqueda y el elemento no existe, esa cosa tendría que tener la capacidad de decirnos que no se
encontró el elemento, ¿sale?

Creo que este es de actualizar: lo mismo, ¿qué es lo que toma? Pues toma el... el este... ¿cómo se
llama? El ID que le pasamos. Aquí vemos que existe, ¿qué tiene de raro? Pues tiene doble «t» en
«artículos», ¿sale? Toma este valor, le pasamos los nuevos valores, le quitamos la «t» de más en
artículos, volvemos a consultar y pues ahí está de alguna manera.

Y el último: vamos a eliminar, ¿vale? Por ejemplo, ahí están todos, eliminamos —nada más hace el
brinco, no devuelve nada— y ya no está, ¿sale? Simplemente le pasamos los verbos con los que queremos
jugar: el GET, el POST, el PUT, el PATCH —si es que tenemos un PATCH—, el DELETE y san se acabó.
Podemos pasarle un OPTIONS en algún momento siempre y cuando esté habilitado, ¿vale?

Así funciona la cosa esa, ¿vale? Y bueno, se acabó.

---

## Recorrido por el código en el IDE

*(El profesor abre su IDE en la computadora para mostrar el código real / 42:20)*

**Profesor:** A ver... ¿Se murió? Que no se muera. Entonces, pregunta: ¿qué hay que ver?, ¿qué les
gustaría que les mostrara?, ¿el objeto de dominio?

**Alumno:** Ajá.

**Profesor:** A ver, vamos a quitar esto. En `domain`, ahí está la categoría. Ahí están sus
validaciones, sus reglas de negocio dentro del objeto de dominio. Estamos validando algunas cosas. Es
una clase normal con métodos get, con métodos set tal vez, o en este caso con un constructor con todos
los argumentos y nos evitamos los setters, ¿vale? Y ahí están todos los métodos y el `toString()`.
¿Por qué? Porque queremos una representación en texto plano.

Luego pues a lo mejor la definición de lo que pretendemos hacer, ¿vale? ¿Qué cosa quiero hacer? Pues
yo quiero guardar, buscar por ID, mostrar todos, eliminar y averiguar si esa cosa existe o no existe,
o cualquier método que se les ocurra, ¿vale?

Bueno, dices: «Okay, pero quiero guardar, ¿y dónde está el actualizar ahí?» Bueno, aquí sí ya
decidimos que vamos a agarrar una tecnología en particular, ¿sale? Entonces que vamos a agarrar JPA o
vamos a agarrar Hibernate. JPA desde su versión 5... 6... versiones —no me acuerdo qué versión— dejó
de tener métodos separados y empezó a trabajar con: voy a modelar entidades con JPA pero voy a
trabajar persistencia con Hibernate. Por lo tanto aplica el método `saveOrUpdate()`. Si existe, lo
actualiza; si no existe, lo crea. Entonces por eso solo tenemos el guardar, porque en realidad es el
mismo método que vamos a reciclar para la actualización.

Luego pues por ejemplo nos vamos acá a la parte de la aplicación... a lo mejor aquí viene lo que
necesitamos. Ah, bueno, ahora sí... ¡Ah! Otro dato curioso: `Category` en el dominio **no tiene
decoradores**, es una clase Java limpia, lo más limpia que se pueda.

Pero por ejemplo estas para crear, pues a lo mejor ya tienen decoradores. Ahora sí mi clase solo es
de... pues a lo mejor tres líneas, ¿no?, con las respectivas reglas, con las respectivas precisiones.
Pues cuando jugué con mi base de datos dije que el ID es autonumérico, por lo tanto no lo necesito,
pero sí necesito el nombre y la descripción. «Ah, bueno, con eso me vas a crear todos los getters,
todos los setters, un constructor sin argumentos, un constructor con argumentos», ¿sale?

### Tarea: el patrón Builder

Y ahí en la pizarra les puse un `@Builder`. **Tarea: ¿qué es, para qué sirve, con qué se come y cómo
se adereza el patrón de diseño Builder, sale?** Que es otro patrón curiosamente. ¿Para cuándo? Para
algún día. Váyanlo trabajando.

### Sigue el recorrido

Y así con los otros. Ahora dices: «Okay, tengo el de crear, voy a actualizar». Pues a lo mejor aquí sí
necesito el ID, ¿por qué? Porque necesito recuperar quién es el ID que va a actualizar. Párenle de
contar, vamos a agarrar ORMs. ¿Cuál? JPA, Hibernate, Cayenne, o los que juegan con... con alguna otra
tecnología: Laravel, Yii, este... Prisma o... bueno, el de Laravel es Eloquent si mal no recuerdo,
¿no? Una cosa... no sé cómo se llama, no me acuerdo. En principio lo que ejecutan es consultas a nivel
SQL, y aquí va a ejecutar un `UPDATE fulanito SET` le va a poner los valores `WHERE id = algo`. Por lo
tanto aquí necesitamos a lo mejor el ID, por eso va, ¿vale? Y así, ¿vale?

Ahora, si vamos con este: «Ah, bueno, pues para la respuesta, ¿cuál es su estructura?» Pues necesito
que me devuelva la construcción del objeto, ¿vale? ¿Qué va a tener? Pues id, name y description,
porque para la respuesta necesito todos los valores, es lo que quiero pintar.

¿Puedo crear una clase con todos los atributos y lo reciclo? O sea, sí, pero la arquitectura dice que
no debe de ser, ¿vale? Si no, estarían violando otra vez alguno que otro principio.

Y aquí están los casos de uso, literal uno por cada cosa. Uno por cada cosa, así de plano, ¿vale? Para
listar todos pues te devuelve una lista; para buscar uno, creo que el buscar uno curiosamente me
devuelve un `Optional`. Y básicamente ese `Optional` me permite hacer cosas como este... lo busco, te
lo devuelvo; si no, te devuelvo otra cosa, comúnmente un nulo, ¿vale? Y ya.

Y luego pues viene el servicio. Pues a lo mejor el servicio... Ah, bueno, el servicio ya queda aquí.
Vamos a ver el de actualizar: algo como eso, ¿vale? Ese día preguntaba el compañero de allá: «Bueno,
pero ¿cómo sé qué cosa voy a actualizar o qué cosa voy a eliminar, no? O sea, ¿cómo le digo cuando
escribimos el DAO?» Bueno, porque eventualmente tendríamos que hacer algo como esto: buscar por ID,
este recuperar... como quiera que le quieras llamar. Y bueno pues busca lo que tiene que buscar,
¿sale?, y se lo va a asignar pues a una... a un objeto con el que pueda trabajar del mismo tipo.

Entonces `Category`, que es un objeto de dominio, `categoriaExistente`, dice: «Pues busca, si lo
encuentras, qué padre; si no lo encuentras, pues ¿qué cosa vas a hacer?» Entonces tenemos el
`orElse()`, que es el normal, y le decimos `null`; o en este caso `orElseThrow()`, pues lanza una
clase que se llama `CategoryException` con el mensaje. ¿Dónde está `CategoryException`? Esa no la
mostramos hace ratito, pero pues `CategoryException` está en el objeto de dominio, en algún lugar de
por acá, ¿vale?, que es este, y que lo único que tiene es la posibilidad de recibir un texto, un
mensaje y pintarlo, punto, y extiende de `RuntimeException`. Listo.

Eso con respecto al servicio. Luego pues a lo mejor nos vamos con la infraestructura y vamos con los
adaptadores, ¿vale? Vamos a agarrar este: ahora sí, en forma. ¿Tienen buena memoria? ¿Cuál es la
diferencia entre ese y el objeto de dominio en términos de atributos definidos? **Ninguno.** Son los
mismos atributos: tiene `idCategory`, tiene `name` y tiene `description`. El objeto de dominio también
tiene esos tres, ¿vale?

La diferencia es que aquí ya tiene cosas específicas casadas con la tecnología: le decimos que esta
representa una tabla, una entidad (`@Entity`, `@Table(name = "categories")`). Ya cuando veamos ORMs
pues nos recordaremos que tenemos dos modelos: el modelo entidad-relación y el modelo relacional. Y en
el modelo entidad-relación a una tabla —bueno, la que va a terminar siendo una tabla— le llamamos
entidad. Entonces, una entidad, y que después de que modelamos el recuadro como entidad y lo pasamos a
SQL para tener la tablita le llamamos coloquialmente —bueno, oficialmente relación, coloquialmente
tabla—. Bueno, necesitamos una tabla, ¿vale?

Después lo típico: getter, setter, este... bueno, mutators, accessors, equals, toString, hashCode, lo
que sea, por separado —que sería lo ideal— o todo junto con un `@Data`, ¿vale? Luego un constructor
con argumentos, un constructor sin argumentos.

Definiciones específicas: esta cosa funciona a nivel de metadatos, ¿vale? Cuando creamos el SQL de
nuestra tabla definimos que es entero, no nulo, autoincrementable y que es llave primaria. Ah, bueno:
llave primaria (`@Id`), es autonumérico (`@GeneratedValue(strategy = GenerationType.IDENTITY)`) y se
llama `id_categoria` (`@Column(name = "id_categoria")`). Eso es lo que tiene eso y es muy casado con
la tecnología en particular, ¿sale?

Después pues a lo mejor vamos con la cosa esta que me dice: «Ah, mira, ¿qué crees? Ahora sí, ya tienes
una entidad, vamos con el otro cachito de tecnología: quiero que sea del tipo `JpaRepository`», ¿vale?
Va a recibir dos valores: la entidad más el ID que es atributo serializado
(`JpaRepository<CategoryEntity, Long>`). Después, métodos específicos: lo que sea que se les ocurra
extras que a lo mejor no están considerados dentro de la interfaz o que a lo mejor quieren que tenga
algún comportamiento diferente, ¿vale?

Y después pues ya viene la implementación. Aquella nos dice el qué, ahora sí: voy a guardar. ¿Qué voy
a hacer para guardar? Bueno, lo divertido es esto: que si no tuviéramos que hacer... utilizar
arquitecturas, solo queda en una línea y el método es justo `jpaRepository.save(entity)` y le pasamos
la entidad. Y ya, y es lo que nos devuelve a lo mejor.

Pero aquí pues tenemos que hacer un **mapper**. ¿Por qué? ¡Ah! Pues porque tengo una clase de tipo
entidad con anotaciones propias de JPA y necesito pues que sea equiparable a la clase que representa
el objeto de dominio. Entonces vamos a hacer un mapper, ¿vale? Toma esto y que se comporte como esto,
¿vale? En fin.

Por consecuencia, pues vamos con el mapper. Y el mapper nada más es esto:

*(Muestra la interfaz del Mapper con MapStruct / 43:18)*

¿Vale? Y ya.

Bueno, ¿no quieren utilizar mapper? Bueno, el mapper en este caso agarré **MapStruct**, ¿sale? De lo
contrario pues está el **ModelMapper** también, o se pueden encontrar algún otro. Si no lo quieren y
lo quieren hacer a mano, pues literalmente hay que escribir métodos para decir: pasar de DTO a entidad
y pasar de entidad a DTO, ¿sale? Y por cada set el get correspondiente, ¿vale? Y ya. Bueno, y ya.

Después iríamos a lo mejor... bueno, ¿el controlador? Ah, bueno, vamos con el controlador, por ejemplo
crear.

Otro DTO, otra clase que tiene los mismos atributos. Sí, la responsabilidad es diferente. Esta cosa
dice que bueno, aquí es donde me tengo que asegurar que esto tenga algo: el nombre de la categoría es
obligatorio (`@NotBlank(message = "...")`). Pero ¿qué crees? Debe de tener un máximo de 50 caracteres,
debe de tener un mínimo de 4 caracteres (`@Size(min = 4, max = 50)`). Y si se incumple: «Ah, pues el
nombre de la categoría no puede exceder los 50 caracteres o el nombre de la categoría debe de estar
entre 4 y 50 caracteres», ¿vale? Por ejemplo. Y así con cada uno de ellos.

Aquí justo ya le estamos poniendo otro cachito de especificaciones de otra tecnología —bueno, es la
misma, pero es el **Bean Validation**, ¿sale?— específicamente, que es **Jakarta Validation** para
nosotros.

Ese es el DTO que recibe y el que va a utilizar justo aquí.

¿Hasta dónde lo llevamos? Ah, pues algo como esto: ah, pues tengo por aquí el este
`CreateCategoryRequestDto`, que es el DTO de hace ratito con todas las validaciones, pasa como objeto
porque requiere un objeto que va a pasar a un objeto JSON y que va a validar de algún modo. Hace su
chamba, ¿vale?, recupera los valores, hace lo que tiene que hacer y simplemente ejecuta el caso de uso
específico, el crear.

Y claro, me devuelve una respuesta: `ResponseEntity.status(HttpStatus.CREATED).body(...)`, cualquier
estado con el que quieran trabajar: el `CREATED` (201), el `OK` (200), el `NO_CONTENT` (204), el...
específicos del protocolo.

### Tarea: códigos de respuesta HTTP

**Jefe de grupo, tarea: no sé si ya se los pedí, si no pues ahí ténganlo a la mano: Códigos de
respuesta del protocolo HTTP, ¿vale? Se requieren, desafortunadamente, ¿vale?**

*(Interrupción breve con un alumno que entra al aula / 46:25 - 47:00)*

**Profesor:** ¿Vale? Y bueno, ya jugaremos con eso en su momento. Y ahora sí, ¿qué más necesita? Ah,
bueno, porque necesitan curiosamente todas estas, ¿no? El Bean pues todas las clases que se
definieron, y pues este a lo mejor es el que también hace el otro cachito de magia.

Ahora sí, el `RestControllerAdvice`, pues por ejemplo dice que para una cosa de esta, una excepción de
este estilo, pues manda a llamar a la clase excepción que se creó que representa el objeto de dominio
modelado, `CategoryException`.

Y aquí es donde pintamos cosas que debería de ver el usuario, una respuesta lo más adecuada: es decir,
eh... pues un `BAD_REQUEST` (400) porque pues a lo mejor no fue el adecuado, este un error interno del
servidor (`INTERNAL_SERVER_ERROR` 500), un no encontrado (`NOT_FOUND` 404), un... según el código que
vaya a arrojar esta cosa, ¿no?

Y pues, señores, así de divertido es esta arquitectura.

¿Dudas? ¿Quejas? ¿Traumas?

**Alumnos:** No.

**Profesor:** Continuamos entonces con lo que sigue, ya para darle carpetazo al número seis.

---

## Microservicios

*(Pasa al siguiente tema: Microservicios / 48:40)*

**Profesor:** ¿Quiénes ya... quiénes ya llevan... están llevando Distribuidos? Hay una materia
obligatoria. Se supone que ahí van a hacer microservicios, ¿no?

**Alumno:** Depende del profe.

**Profesor:** Depende del profe, okay.

Lo que sí, y es un hecho: lo que estaba en la la... bueno, hace ratito por ahí que es un **API, no es
un microservicio**, ¿sale? Aclaremos el punto. ¿Estamos de acuerdo? ¡Va!

Igual, es otro estilo de arquitectura. Este... pues la literatura dice que es el más relevante en la
actualidad, vamos a decirle que sí, ¡va! No le digamos... vamos a decirle que sí, en fin.

¿Y en qué consiste? Pues justo consiste en: dada una aplicación zota grandota, trozarla en partes y
san se acabó. Hacer pequeños bloques funcionales o pequeñas aplicaciones funcionales autosuficientes,
literalmente.

Ejemplo: consideren un sistema de facturación con las normas estrictas de Hacienda, del... de Lolita,
de... de... de... de la entidad recaudadora más importante de México: la Hacienda, ¿vale? Entonces,
¿qué necesitamos? «Ah, bueno, pues necesito generar factura, necesito timbrar factura, necesito a
quién le voy a emitir la factura, necesito los conceptos y todo el relajito», ¿vale?

Entonces, para chutarse ese monstruito digo: «Señores, a ustedes les toca este módulo, a ustedes este
módulo, tan tan tan tan tan, y san se acabó». Según sea el caso. Estamos jugando con Java, con Spring,
entonces pues va a ser en ese estilo, ¿no? No necesariamente.

Entonces, ¿qué nos dice? Que su intención es justo dividir toda la funcionalidad en componentes
pequeños autosuficientes con su respectiva base de datos o no, según sea el caso.

Ahora bien, imagínense esto: una aplicación grandota, ¡así, literal!, y cada módulo se vuelve una
aplicación extra. Punto. ¿Tiene cuatro módulos? Clientes, inventarios, facturación, compras, lo que se
les ocurra. Ya está.

Ahora bien, ¿qué necesitamos? ¡Ah! Bueno, pues necesitamos a alguien que coordine. Curiosamente
necesitamos a un **API Gateway**, ¿sale?, que es el que a lo mejor al que le van a llegar todas las
peticiones y él va a determinar qué cosa se tiene que hacer, la manera en que se van a estar
comunicando, ¿vale?

Y bueno, cada uno de estos microservicios en sí, pues en realidad podría tener como soporte una
aplicación de algún tipo específico. ¿Se acuerdan aquella imagencita que decía «aplicación de propósito
general»? Python, Java y demás, se presta para eso. Es decir, comúnmente estamos anclados a un stack
tecnológico, ¿por qué? Porque lo deciden ustedes, lo decide el arquitecto, lo decide el líder del
proyecto: «Vamos a jugar con esto y se amuelan». Pero a lo mejor dices: sí, les vamos a dar chance:
ustedes programan en PHP, ustedes en Node.js, ustedes en Java decentemente, ustedes en .NET y ustedes,
que están más rudos, lo van a hacer a consolita. Así, bien mono. Y se puede, a final de cuentas,
¿vale?

Ahora bien, ¿hay comunicación entre ellos? Por supuesto que sí, sí sí es posible. Ahora, aquí viene lo
divertido: estos dos a lo mejor dicen: de manera directa este trabaja con MySQL, este trabaja con
Mongo. Pues a lo mejor tengo otro microservicio que trabaja con Postgres, otro que trabaja con DB2,
otro que trabaja con Oracle: es posible, ¿sale?

Ahí el reto es para el **DBA**, porque a final de cuentas debe de garantizar que en el modelo
conceptual de su base de datos esté seccionado la parte que le corresponde a cada manejador y la
manera en que se van a comunicar, y la manera en que tenga que garantizar que: «A ver, sí, pero la
categoría está en MySQL y el producto está en Postgres. Agregué un producto, debe de pertenecer a esta
categoría, ¿vale? Entonces voy por los datos de la categoría que está en otra cosa para meterlo a
esto». O sea, eso es chamba del... de para poder garantizar cuestiones de **integridad**, ¿no? O cuando
genera una factura, una compra, ¿vale?, que por definición en una compra o en una factura se ve
involucrado: clientes, ventas, artículos, detalle de ventas, al menos, ¿vale? ¿Se me entiende?

Entonces, chamba del API Gateway: pues justo pues él es el que va a estar monitoreando, observando y
verificando, ¿vale?

### Patrones de microservicios

¿Se acuerdan de sus patrones de diseño del semestre pasado? Bueno, qué bueno. Hay más nuevos, hay
otros, según lo que quieran hacer. Aquellos son digamos que de propósito general que debieran de
conocer. Ahora aquí: ¿van a hacer microservicios? Pues agarran aquellos —ya los dominaron— y les van a
agregar todos esos, ¿vale?, porque ya son específicos para lo que pretenden hacer. Y esa es la
clasificación, ¿vale?

Lo más seguro que para esta época ya le han de haber agregado alguna otra, segurito. Pero al menos,
como era la generalmente aceptada, teníamos esos, ¿vale?, según la naturaleza de lo que están
modelando: tenemos de descomposición, de integración, para bases de datos, de observabilidad, etcétera,
etcétera, etcétera, ¿sale?

Entonces, ¿cada microservicio tiene una sola base de datos o cada microservicio tiene una base de datos
propia? La respuesta es sí a los dos, depende de ustedes, ¿sale?, en realidad. Por ejemplo, tenemos por
ahí: ¿base de datos por servicio o tenemos base de datos compartida por todos los servicios? ¿Vale?
Solo una genérica y san se acabó, ¿sale? Tema de métricas, el proxy, el gateway, el lo que se les
ocurra.

Bueno, en fin. Me quedan 5 minutos.

*(Revisa diapositivas rápidas de patrones / 56:00)*

A lo mejor un pequeño detallillo por cada uno de ellos:

- **Descomposición por capacidad empresarial**: los servicios corresponden a las capacidades
  comerciales. Cuando hacemos esto, normalmente ustedes cuando hacen el análisis determinan
  específicamente contra qué lo van a trabajar o sobre qué lo van a trabajar, ¿sale? O sea, a lo mejor
  gestión de clientes, gestión de proveedores, muy orientado al negocio.
- **Por dominio**: ¿sale? ¿Cómo se llama esto? **Domain-Driven Design (DDD)**, creo que... Sí, eso,
  ¿vale? Entonces ahora sí: un dominio era la categoría.
- **Para integración**: por ejemplo el API Gateway, el **Backend for Frontend (BFF)**, este... según sea
  el caso.
- **Para bases de datos**: todos esos.

Bueno, y está esa cosa: tenemos el servicio de cuentas, su base de datos que está en MySQL; de
inventario, que está en Postgres; de... ayúdenme, se me fue, traduzcan shipping...

**Alumna:** Envíos.

**Profesor:** Envíos... no, es distribución, vamos a llamarle distribución decentemente, es cuando
mandan, envían, reciben y demás. Esta cosa está en MongoDB. Mejor aún: MySQL, Gemstone, MongoDB.
¿Relacional, orientado a objetos, no relacional? ¿Se puede? Sí se puede, ¿sale? Sí se puede.

Y cada uno tiene sus mecanismos para poder interactuar: un API REST por cada uno de ellos y bueno, pues
un frontal, un web, ¿vale?, eh... para el móvil, simplemente consumir datos con... con su aplicación
para dispositivos móviles y listo.

Bueno, **ventajas**: pues dice que el mantenimiento es una ventaja, pues sí, porque nada más les toca
trabajar con un microservicio en particular, solo se van a enfocar a eso. Desarrollo heterogéneo: pues
sí, porque a lo mejor, insisto, Java, este PHP, este .NET, lo que sea.

**Desventajas**: complejidad de configuración, complejidad en términos de seguridad, complejidad al
hacer el monitoreo, la trazabilidad, este... y cositas por el estilo.

### Por qué el API de hace rato no es un microservicio

¿Por qué el API de hace rato no es un microservicio? Justo por esto: porque al menos necesitamos un
servidor de descubrimiento, un **Discovery Server**, ¿sale? Como estamos jugando con Spring, pues es
**Eureka**, ¿sale? Eureka, se configura.

Necesitamos un **Gateway**: para eso pues necesitamos apoyarnos de **Spring Cloud**, por ejemplo, y
decir quién es... cuál es la forma en que va a estar trabajando el server, las rutas de los diferentes
microservicios, ahora sí, ¿sale?, por definición. El punto es: esta cosa va a saber quién es quién y
cuáles son sus responsabilidades.

Después, la definición del microservicio específico, el microservicio en sí, que a lo mejor va a estar
escuchando por algún lugar, pero se tiene que **registrar** en el servidor de descubrimiento. Se tiene
que presentar: «Hola, soy yo, estoy vivo y hago esto». Y así por cada uno de ellos.

Entonces podemos tener por definición empaquetado microservicios en diferentes contenedores, en
diversas nubes o lo que quieran, en diversos proyectos. Pero hay alguien que los va a estar
gestionando, ¿sale? El que los va a conocer pues el API Gateway y el servidor de descubrimiento para
que ellos se auto-registren y vaya a hacer consultas, ¿vale?

Entonces, al menos para decir que tenemos un microservicio necesitamos:

1. Un proyecto (**Discovery Server**)
2. Dos proyectos (**API Gateway**)
3. Tres proyectos distintos (**El microservicio en sí**)

¿Vale?

---

## Cierre: micro frontends

Y ya cerramos con esto: justo a la media.

Microservicios: así como podemos hacer microservicios, también podemos hacer **Micro Frontends**, y es
el mismo principio, ¿sale?

Por lo tanto puedo tener mi aplicación bien mona donde todo el header esté en Vue, todo el aside esté
en React y todo lo... mono esté en Angular, ¿sale? O en la combinación que quieran: por
funcionalidades, por dominios, los mismos patrones, ¿vale?

Señores, con esto terminamos, más la investigación que se echaron, **terminamos la primera unidad**.

---

## Tareas que salieron de esta clase

| # | Tarea | Minuto | Fecha |
|---|---|---|---|
| 1 | Investigación: patrón de diseño **Builder** (qué es, para qué sirve, con qué se come, cómo se adereza) | ~44:00 | «Para algún día. Váyanlo trabajando.» |
| 2 | **Códigos de respuesta del protocolo HTTP** (tenerlos a la mano) | ~46:20 | Sin fecha; «se requieren, desafortunadamente» |

Confirmado al inicio de la clase (00:30): la **réplica del minisitio de nuevo ingreso** ya quedó
entregada y es con ella con la que se va a trabajar.

Sigue vivo el arrastre de la clase 4: el cambio de `PreparedStatement` a `CallableStatement`, que él
puso «para la próxima semana».
