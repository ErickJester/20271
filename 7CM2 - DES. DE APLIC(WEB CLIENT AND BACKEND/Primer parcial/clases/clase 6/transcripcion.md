# Clase 6 — Entregables del proyecto, y Docker en el laboratorio

- **Materia**: C704 · Web client and backend development frameworks (grupo 7CM2)
- **Fecha**: miércoles 9 de septiembre de 2026 *(inferida del horario lunes/miércoles; el profesor
  dice "todavía tenemos como una semanita" hablando del 11 de septiembre, lo cual no cuadra con esa
  fecha — conviene confirmarla)*
- **Duración aproximada**: ~50 minutos de sesión (marcas de tiempo del 00:00 al ~49:08), con un
  bloque largo de trabajo en máquina de por medio
- **Tipo de sesión**: **práctica de laboratorio**. La mitad de la clase fue el profesor pasando
  entre las computadoras; el pizarrón y las diapositivas solo enmarcan el ejercicio.
- **Origen**: transcripción detallada de la grabación, ya limpia (no requirió el pipeline de
  `pdftotext` + decodificación de jerga que sí necesitaron las clases 1 a 4)
- **Artifact reconstruido**: [`clase6-apache-nginx-docker.html`](clase6-apache-nginx-docker.html)

> Los tecnicismos de backend, bases de datos y contenedores se respetan tal cual. Los pasajes entre
> paréntesis y en cursiva son acotaciones de lo que pasaba en el aula, no palabras del profesor.

---

## Los entregables del avance del proyecto

**Profesor:** O pues agarren cualquier libro de bases de datos, por ejemplo el de Adoración de
Miguel —sí, hay un autor que se llama así en bases de datos—, que tiene un montón de ejercicios
resueltos de bases de datos. Se agarran una de esas bases de datos y lo que van a hacer es... se la
llevan a sistemas, ¿no? Lo que sea, lo que se les ocurra.

Entonces es libre. En términos de eso, es cuatro personas máximo. Puede ser uno, dos, tres, cuatro y
me estoy arriesgando.

*(Risas en el aula)*

**Profesor:** No, si de por sí dicen que el cuarto pues es el que va por... el que les echa porras,
¿no?

Es libre, hasta ahí. ¿Vamos bien o nos regresamos?

Entonces, ¿qué vamos a entregar hasta ahorita?

1. **Diccionario de datos**: la tablita esa bonita que decía: número uno, este... atributo, este...
   ID, eh... sus restricciones: es numérico, es este autogenerado, es llave primaria, es no nulo,
   este... y su descripción: «Representa a... bla, bla, bla, bla». ¿Sale?
2. Ya que tienen, ya hicieron el análisis y demás, pues necesito que le agreguen el **Modelo
   Entidad-Relación**, ¿sale?, el E-R. ¿Vale?
3. Ya que tienen el entidad-relación y demás cosas, pues vamos a pensar que es el **relacional**,
   ¿va? Que del relacional, por ejemplo...

*(El profesor dibuja o escribe en el pizarrón / 02:00 - 02:35)*

**Profesor:** ...que para el otro, cualquier este herramienta IDE les ayuda a generar eso a partir
de sus bases de datos. Así de bonito, ¿sale?, para no conflictuarse tanto: el **Workbench** se los
hace, el **DBeaver** se los hace, **Navicat** se los hace, este... cualquier hierba de esas y ya,
con eso cumplen esta.

Pero para tener eso, pues primero debieron de haber tenido esto; mejor aún, la base de datos ya
debió de haber estado creada. Por lo tanto —o sea, no va en orden—, pero necesito el **script de
creación de su base de datos**, o sea el archivo SQL. Ya lo tienen, échenlo a andar y agarran una de
estas que haga **reingeniería** y ¡órale!, ya está. Listo, ¿vale?

Estas cositas de aquí, para no perder la costumbre: su base de datos al menos que esté en eso,
¿sale?: al menos en **Tercera Forma Normal (3FN)**. Ya pasaron por bases de datos.

¿Qué no debe de tener su base de datos? Menos cualquier cosa que tenga que ver con usuarios, ¿sale?
Es decir, deben de ser **al menos cinco tablas** sin esta cosa, ¿va?

—«Es que sí necesito control de usuarios». Sí, lo va a tener.

—«Es que necesito definir roles». Sí, lo va a tener.

Pero tiene que ver con el **módulo de gestión de usuarios**, y en gestión de usuarios al menos
tenemos usuarios y roles, y la unión entre usuarios y roles son tres tablas más. Pero esas van de
cajón. Entonces, esas no; entonces al menos cinco.

¿Qué otra cosa les pedí?

* Portada
* Diccionario de datos
* Modelo entidad-relación
* Script SQL
* Diseño de...
* Diccionario de datos

Y hasta ahorita nada más eso, ¿vale? Por eso la asignación no está, porque hasta ahorita es nada más
esto. Pero sí ya deben de ir pensando en qué debe de llevar, ¿vale?

Digo, en el peor de los casos, ¿qué otra cosa les voy a pedir como avance del primer parcial? Pues a
lo mejor, no sé, pues el modelado del sistema, ¿no? Pero no, me conformo con esto. Hasta ahorita.
Ya, aclarada la duda. Ahorita lo cerramos, no lo quiero.

---

## La tarea del minisitio, y el primer `docker run`

*(El profesor cambia a las diapositivas de Docker / 05:30)*

**Profesor:** Vamos a jugar hoy con esto. Vamos a terminar este cachito, ¿vale? Vamos a terminar
este cachito.

*(Pasa a la lámina)*

**Profesor:** Ahí está. Que fue lo último que revisamos de esta presentación y quedamos que lo demás
lo íbamos a dejar pendiente.

Se llevaron una tarea, ¿sale? Su tarea consistía en que se metieran a internet bien bonito y que
literal pues **bajaran todo el minisitio**. ¿Cómo? No sé: botón derecho, «Ver código fuente»,
copiar, pegar y me armo toda la estructura; este... no sé cómo lo hayan hecho, ¿va?

Pero para usuarios Linux o Unix, Linux, pues está el poderosísimo **wget**. ¿Ya? `wget`, la liga, un
`-r`, ¡vámonos!, se trae todo lo que se tenga que traer. En Windows no sé cómo. En Mac se puede,
solo que el `wget` no está nativo. Ustedes dirán cómo, no sé.

Entonces, ¿eso por qué? Ah, bueno, porque lo vamos a construir. Vamos a pensar que pues lo metimos
acá, ¿vale? Por ejemplo: `user`, mi usuario, Mis Documentos, hay una carpeta que se llama `escom`,
otra que se llama `2027-1`, otra que se llama `escom`, otra que se llama `back-front`, otra que se
llama `contenedores`, otra que se llama `minisitio`... el punto es que así está mi estructura de
directorios, ¿no?

Y dentro de eso pues metí una carpetita que se llama `nuevoingreso_27-1`, ¿vale? Ejemplo: dentro de
esa carpetita pues podemos hacer algo como esto:

```
docker run --name escom_ni -d -it -p 3000:80 httpd:latest -v ...
```

El `ni` es de «nuevo ingreso», ¿vale? `-d`, `-it`, `-p`: queremos que esta cosa, si en dado caso
llega a funcionar —esperemos que sí—, escuche peticiones para mí en el **puerto 3000**. Que
internamente esta cosa va a estar trabajando en el **puerto 80**, eso es otra cosa, ¿vale? ¿Por qué?
Bueno, pues porque a lo mejor el que queremos cargar en el puerto 80 es la última imagen estable del
**Apache HTTP Server**, ¿vale?

Y la `-v` tiene que ver con los **volúmenes**, porque a final de cuentas necesito que tenga un
poquito de **persistencia** de algún modo. Bueno: de aquí para acá, más bien de aquí, de los dos
puntos para allá, es: `/usr/local/apache2/htdocs`. ¿Por qué? Porque por definición en un servidor
web Apache, de la familia Ubuntu, de la familia Debian, ¿sale?, el `apache2` se va acá, el
directorio de trabajo se va acá, por ejemplo, de la familia Linux genérica. Entonces se va a la
carpeta `htdocs`, que es donde va a estar publicado lo que necesitemos.

De los dos puntos para la izquierda es **dónde se encuentra lo que sea con lo que queremos
trabajar**, ¿vale? Es toda esa ruta grandota. O sea, se asocia a la ruta donde estoy trabajando.
Para eso, en Linux, recuérdenme, ¿qué hace...? ¿Qué hace el `pwd`?

**Alumno:** Mostrar el directorio donde te encuentras.

**Profesor:** Mostrar el directorio actual. Funciona igual: o sea, estoy trabajando aquí, ¿vale?
Entonces esto funciona para sistemas Unix, específicamente para Unix: `${PWD}`, ¿sale?

Y bueno, y ya que está, pues si todo va bien, ¿cuál es el resultado que esperamos obtener? Algo como
un `localhost:3000` y me debe de decir —salud—: «Nuevo ingreso semestre 27-1», que es el actual, y
ya, de ejecutar esto. Al menos ese es su resultado esperado.

`pwd` para Unix, Linux y lo que se les ocurra; para Windows, `cd` o `${PWD}` pero con este... ¿cómo
se llaman?

**Alumno:** Llaves.

**Profesor:** Llaves, gracias, llaves. Llavecitas. Iba a decir corchetes. Llavecitas, ¿vale?

Y ya, ya lo echamos a andar. Ese es el resultado esperado, ¿vale?

---

## La segunda ruta: el Dockerfile

**Profesor:** Bueno, ¿no lo quiero echar a andar así? Está bien. Vamos con un **Dockerfile**. ¿Por
qué no? Vamos a crear un Dockerfile, ¿vale? La regla del Dockerfile es justo eso: preferentemente
**sin extensión y con «D» mayúscula**. En realidad sin extensión y con «D» mayúscula, ¿vale?

¿Qué le decimos? «Mira, ve y carga con una imagen»:

```
FROM httpd
```

La imagen es `httpd`, sin versiones, por lo tanto se va por la última imagen estable existente en el
**Docker Hub**.

Vamos a copiar el contenido del minisitio al directorio de Apache donde sea que vamos a trabajar.
Entonces, ¿dónde está esta cosa? Pues estoy aquí, curiosamente estoy aquí. Acuérdense que tenemos
punto (`.`) para decir que estamos en el directorio actual si mal no recuerdo:

```
COPY . /usr/local/apache2/htdocs/
```

«Estoy aquí y llévatelo», ¿vale?

Y luego pues vamos a exponerlo por algún puerto en particular, por ejemplo el puerto 80:

```
EXPOSE 80
```

¿Vale? Le decimos: «Pues exponlo», listo.

En términos de estructura, pues a lo mejor así lo vemos en el **Visual Studio Code**:
`nuevoingreso_27-1`, tiene `media`, `thankyou`, `css` y todas las cosas que se descargaron que tiene
eso, porque ese es el contenido de la chuncha esa. Solo tiene un `index.html`. A nivel directorio
pues es así: `minisitio`, `nuevoingreso`, tan tan tan tan, a nivel explorador de Windows. Hay que
agregarle el Dockerfile, ¿sale?

Una vez que ya le agregamos el Dockerfile a la cosa esa, a nuestro proyecto bien mono, bien bonito,
pues a lo mejor, ¿qué sigue? Hay que **construir la imagen**:

```
docker build -t mi_imagen .
```

`-t` o `--tag`, el nombre que le quieran poner, por ejemplo `davis/mi_imagen:latest`, espacio y el
punto. La `t` es el tag, que es el nombre; el punto es el **directorio actual**, ¿sale? Es decir,
donde me encuentro busca el archivo de configuración de Docker.

Echamos a andar esa cosa, ¿qué esperamos obtener? Pues que haga algo como eso: que vaya, busque,
descargue, que haga copias, este... lo que sea. ¿Qué cosa trae? Por ejemplo: `httpd:latest` y se va
por la última versión, ¿sale?

Y por acá dice que hace una copia: `COPY . /usr/local/apache2/htdocs/`, ¿vale?, que es esta línea
que estaba, bueno, en la diapositiva anterior. Y ahí está, ¿sale?

Y luego pues podemos hacer un `docker image ls` y pues en realidad nos dice: «¿Sabes qué? Aquí hay
una imagen creada recientemente, ya está, está a disposición, haz con ella lo que quieras», ¿no? Ya
tenemos la imagen.

Luego pues hay que echarla a andar. ¿Cómo echamos a andar la imagen? Así:

```
docker run --name el_nombre_que_le_van_a_poner -d -it -p 3500:80 mi_imagen
```

El que quieran, no precisamente eso. `-d`, `-it`, `-p` y le decimos: «Quiero que esta cosa responda
en el 3500; de manera interna se está trabajando por el 80. ¿Qué imagen va a tomar? La que acabamos
de crear», ¿vale?

Ahí está. Y hace eso.

Y como resultado, el mismo de hace rato, solo que por el 3500, ¿vale?

---

## La práctica del día

**Profesor:** Tienen lo que resta de la clase: **móntenlo en un Apache Web Server**. Entreténganse.

Es más: móntenlo en los dos, en un **Apache** y en una cosa rara que se llama **Nginx**, ¿sale? Si
quieren un Apache HTTPD Server, que para los cuates es el Apache, y el otro es N-G-I-N-X (`nginx`).
Creo que así se escribe, Nginx, N-G-I-N-X.

Y ya. Lo divertido es: este pónganlo en el **puerto 8080** y este pónganlo en el **puerto 8081**.

¡Diviértanse, jóvenes!

Para que vean qué soy buena onda, ¿cuál les dejo? ¿Esa? ¿Esa? ¿Esa? ¿O esa?

**Alumno:** Todas.

**Profesor:** ¿Cuál les dejo? ¿Esa? ¿Esta? ¿Esta? ¿Esta? ¿O esa? ¿Cuál?

**Alumno:** La anterior.

**Profesor:** ¿Esa?

**Alumno:** Sí.

**Profesor:** Esa se queda.

*(Tiempo de trabajo individual y en equipos en el laboratorio / 17:00 - 31:40)*

*(Los alumnos van trabajando en sus terminales, configurando Dockerfiles, haciendo pull de las
imágenes de `httpd` y `nginx`, mapeando volúmenes y puertos)*

---

## El inventario del laboratorio

**Profesor:** Para poder echar a andar eso necesitan **Docker** instalado, ¿eh? Como dato curioso,
aclaremos el punto. Se supone que en estas máquinas... y ya que entramos al laboratorio,
averíguenme, solo por curiosidad: necesitamos Docker, se supone que lo tienen. En algún momento
vamos a jugar con **Java**, averíguenme qué versión tiene. Se supone que debe de tener **VS Code**
(Visual Studio Code). Se supone que debería tener instalado **DBeaver** o **MySQL**... Ah, sí:
**IntelliJ**, y... **Insomnia** o de perdido el **Postman**, ¿sale?

Es con lo que debe... al menos todo lo que deberían de tener esas máquinas de los laboratorios. Yo
espero que sí los tengan, y si no, y si van a traer sus equipos —que ya vi que varios traen sus
equipos—, pues tengamos todos. Eventualmente.

*(Pausa mientras asiste a dudas puntuales en las computadoras de los estudiantes / 19:40 - 31:40)*

**Profesor:** ¿Ya puedo borrar esto?

**Alumnos:** ¡No, no, no! Lo del proyector sí.

**Profesor:** ¿Lo del proyector? Ya está.

---

## Fechas de entrega

*(Conversación sobre fechas de entrega y tareas pendientes / 31:45 - 36:00)*

**Profesor:** De las 20,000 actividades que su compañero me imagino ya les compartió...

*(Risas)*

**Profesor:** ...asuman que **todo se entrega el 11 de septiembre**.

**Alumnos:** ¡Ah, no manches!

**Profesor:** Que no se atrasen.

**Alumno:** ¿Y las que van...?

**Profesor:** Sí, porque todavía tenemos como una semanita, ¿no? Ya si para el 11 de septiembre,
digamos que ya estamos como por el 9 y les dejo una actividad, no va a ser para el 11, va a ser para
una semana después.

**Alumno:** Subió como 18...

**Alumno 2:** No, como 15.

**Profesor:** 15. Con la novedad de que: «¡Es un montón!», pero algunas ni siquiera son actividades,
son **tareas de lectura** en que confío en que ustedes van a leer, van a revisar y demás cosas. Si
lo hacen, bien; y si no, pues...

---

## Docker Compose y varios contenedores

*(Explicación adicional de Docker Compose y múltiples contenedores / 36:00 - 45:10)*

**Profesor:** Si quieren montar los servicios... bueno, más bien varios servicios, móntenlos en un
archivito de este estilo: `docker-compose.yml`.

Y ahora sí puedes reciclar tu... tu misma estructura: un Dockerfile para tu Apache, un Dockerfile
para tu Tomcat, un Dockerfile para lo que tú quieras. Y cuando levantas el servicio: `service`, por
ejemplo, para Apache, todas estas cosas, ¿no? Y en realidad va a tomar lo indicado en el Dockerfile:
`./apache`, `./tomcat`, `./lo que sea`.

Entonces, ¿aquí necesitas el Tomcat? Pues te empiezas otro: `tomcat`, misma estructura que el
siguiente documento. Allá abajito este... ¿qué otro servidor hay? Bueno, el Nginx. Y así se la
llevan, ¿sale?

Y para echar a andar eso, pues simplemente:

```
docker compose up
```

Y san se acabó. Para matarlo:

```
docker compose down
```

Y vámonos.

*(El profesor se acerca a resolver una duda sobre comandos en Windows y rutas / 39:40 - 43:30)*

**Profesor:** Si es Windows, PowerShell o... cualquiera de los dos, y literal, asegúrate de tener
instalado Docker. Y casi casi copias, pegas eso así como está. No, no es cierto: esa no, porque esa
fue con una imagen.

Solo que como es Windows hay que cambiar por llavecitas, por barritas por este... corchetes, por...

**Alumno:** Llaves.

**Profesor:** Llaves, gracias, llaves.

Y esto te tiene que dar como respuesta que se va a conectar al **Docker Hub**, va a descargar la
última imagen estable de HTTPD, va a tomar tu directorio actual, va a crear una copia hacia acá, le
va a poner como nombre esto, te va a permitir de manera desatendida, te va a permitir que puedas
conectarte desde el símbolo de sistema a ella, te va a habilitar el puerto 3000 y el resultado
esperado es que tú lo abras en tu navegador en lugar de `http://localhost:8080`, pues
`http://localhost:3000`.

En su versión 2, si no lo quieres hacer así —como les decía hace rato: ¿cuál les dejo?—, en su
versión 2, dentro de la estructura de tu proyecto que ya lo descargaste, metes tu Dockerfile,
descargas la última versión de HTTPD, copia de donde estoy y expones el puerto 80.

Si sigues esta estrategia, hay que construir la imagen: le pones `docker build -t` el tag con el
nombre que le quieras poner, espacio y el punto. Ya que se ejecutó, te aparece esto: se creó la
imagen, ya está funcionando y ahora hay que correrla, hay que generarla con un `docker run`, va a
tener este nombre con esta imagen. El resultado es que aparezca igual: `localhost:3500` para ese
ejemplo, ¿vale?

¿Dudas, jóvenes?

---

## Cierre: contenedores, microservicios y despliegue

*(Reflexión final sobre servidores, microservicios y despliegue / 45:15 - 49:08)*

**Profesor:** El cuestionamiento del compañero se me hizo interesante: «¿Estamos jugando con
servidores?» La respuesta es **sí**. De alguna manera, estamos jugando con contenedores, ¿sale? Solo
que en ese contenedor, que es ese espacio grandote, esa cajota que ven en los trenes y en los barcos
así bien bonito —no sé, allá por Azcapotzalco hay un montón de contenedores—, ahí están empaquetando
el servidor web, que es el Apache, ¿sale?

En otro contenedor, en otra cajota de esas que está aislada, estamos empaquetando un Nginx. En otra
cajota por ahí estamos metiendo un **Node.js** porque a lo mejor cargamos un proyecto en Node...
Alcancé a ver unas cosillas por ahí que... como comentadas, ¿no? Pero bueno, qué puede ser.

O en una de esas vamos a empaquetar un proyecto en **Spring** para decir: «Aquí va a vivir la cosa
esta». Aquí vamos a empaquetar un proyecto en **Angular** y aquí va a vivir la cosa esta. Y esas
cosas tienen que **platicar** en algún momento.

¿Y estamos jugando con contenedores? Sí, pero de manera interna nuestro proyecto Spring pues trabaja
con un **Tomcat**, una versión minimalista de Tomcat que se llama **Tomcat embebido**. Entonces ahí
va a vivir la cosa esa.

Pero eso no quiere decir que así como está no podamos aventar a una **nube** la que ustedes elijan,
¿vale? O inclusive agarrar nuestro proyecto base y montarlo en un **PaaS** (Platform as a Service).
¿Por qué no? Ustedes tienen cuenta de **Azure** gratis, a menos que ya se hayan acabado sus
créditos.

Por ejemplo, justo: cuando nosotros jugamos con lo tradicional para la web —llámese PHP, HTML, CSS—
agarramos el servidor convencional y ¡paf!, funciona, me responde y sirve. Pero cuando jugamos con
**Java**, sí o sí requiere un **servidor de aplicaciones**, sí o sí, porque ningún servidor web
gratuito en forma o económico les va a dar soporte para Java.

De por ejemplo nubes, vamos a jugar con... **Render**, por ejemplo. ¿Tiene soporte para Java? No,
pero **tiene soporte para Docker**. Y si lo pueden meter en un Docker, ¡ah!, pueden meter lo que
quieran, ¿va? Por ahí va el asunto.

Divertido, a final de cuentas termina siendo repaso.

*(Un alumno pregunta sobre el mapeo de puertos y la redirección de tráfico)*

**Profesor:** ¿Qué servicio estás levantando?

**Alumno:** El de la landing page.

**Profesor:** Ajá, okay, necesitas un servidor web. El puerto por defecto es el **80**. Cualquier web
server te escucha por el 80. Entonces tú estás **enmascarando** el puerto: 8081, ya tienes; utilizas
la misma imagen, ¡va! Vas a crear otra: tomas la misma imagen del Apache Web Server, por ejemplo,
que viene en el 80 pero lo vas a redireccionar al otro y es **otro contenedor**, no construyes...

Esto es nada más para exponer algo basado en Docker.
