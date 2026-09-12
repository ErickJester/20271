# Transcripción — Clase del 2 de septiembre

**Materia**: Administración de servicios en red (7CM3)
**Tema**: Direccionamiento IPv4 y clases de red
**Duración registrada**: 00:00 – 56:53

> **Nota sobre esta transcripción.** Está **incompleta**. El bloque de ejercicios
> (38:00 – 54:05) sólo conserva las intervenciones del profesor: las direcciones IP que
> estaban proyectadas y que los alumnos pasaron a resolver **no quedaron registradas**, así
> que en el artifact de la clase esos reactivos están reconstruidos a partir de lo que sí se
> oye (las respuestas y las correcciones), y marcados como tales. Tampoco se registró el
> último ejercicio proyectado, el que el profesor dejó pendiente porque "no se veía".
>
> Todo lo que en el artifact aparece como cita textual (`.quote`) sale de este archivo. Lo que
> no está aquí y sí allá está señalado como material de completado.

---

## [00:00 – 05:05] Repaso inicial: direcciones IP, bits y clases

**Profesor:** ...a diferenciar cada uno de esos dispositivos. Estamos hablando entonces de una dirección... una dirección IP, ¿no? ¿Qué más? ¿Qué más, jóvenes? ¿Qué más me pueden decir de su tarea que hicieron? Que la mayoría entregó. Adelante.

**Alumna:** Que el espacio de direccionamiento es de 32 bits.

**Profesor:** Okay, su direccionamiento es con base a 32 bits, ¿no? ¿Qué más?

**Alumna:** La dirección se compone por... cierta cantidad de bits corresponden a la red y otra parte a los hosts.

**Profesor:** Perfecto. Entonces, de esos 32 bits los vamos a fraccionar en octetos, ¿no? De los cuales, como bien su compañera mencionaba, estos bits vamos a tener una parte para identificar a la red de dónde precede o de dónde procede, y a la parte de host. Siempre nosotros vamos a manejar eso: una parte para red y una parte para host, ¿estamos de acuerdo? ¿Qué más? ¿Qué más vieron en su tarea? Adelante, adelante.

**Alumno:** Que las direcciones se dividen en cinco clases dependiendo de las necesidades...

**Profesor:** Hablamos de clases, ¿no? Okay. Hablamos de direccionamiento por clases, donde hay clases ¿qué? Hay una A, hay una B, hay una C, hay una D y hay una E. ¿Estamos de acuerdo? Okay. Díganme, la clase A, ¿qué características tienen que recuerden ustedes?

**Alumna:** Máscara diagonal ocho (/8).

**Profesor:** Okay, ya empezamos a hablar de máscaras. ¿Qué más? Entonces la B tiene una diagonal ¿qué?

**Alumnos:** Dieciséis (/16).

**Profesor:** La C es una diagonal...

**Alumnos:** Veinticuatro (/24).

**Profesor:** ¿Qué más?

**Alumna:** En el caso de la clase D, está destinada a multicast.

**Profesor:** Okay, esto es para multicast. ¿Qué más?

**Alumna:** Y la clase E está reservada para investigación.

**Profesor:** Es para investigación, ¿no? ¿Qué más de la A? Recuérdenme.

**Alumna:** También, por ejemplo, esa es una forma de clasificar en clases, pero en lugar de depender de esas clases actualmente hay una que se llama CIDR, en la cual directamente va a indicar cuántos bits forman el prefijo de la red mediante una barra seguida del número, como diagonal 8, diagonal 16... y de esa manera ya no dependes de las clases y a veces con ese tipo de clases sobraban redes, entonces quitas esa parte para las redes específicas que tú quieras.

**Profesor:** Lo que tú quieras. Entonces ya estamos hablando del subneteo. ¿Estamos de acuerdo? Ahorita antes de llegar al subneteo vamos a explicar esto, pero vamos bien. Ajá.

**Alumna:** La clase A es de 0.0.0.0...

**Profesor:** De la clase A tenemos un rango ¿de qué? ¿De la 1 hasta dónde?

**Alumnos:** 127.

**Profesor:** ¿127? ¡Tache! ¿Por qué?

**Alumno:** Porque el 127 es para loopback.

**Profesor:** ¿Estamos de acuerdo? Entonces va de la 1 a la 126, ¿estamos de acuerdo? La clase B va de la...

**Alumnos:** 128 a 191.

**Profesor:** 191. La C...

**Alumnos:** De 192 a 223.

**Profesor:** 223. Para una D, va de la 224 a la...

**Alumnos:** 239.

**Profesor:** 239. Y por último, una E va de la 240 a...

**Alumnos:** 255.

**Profesor:** 255, ¿estamos de acuerdo? Okay, vamos recordando poco a poco todo lo que en algún momento vimos. ¿Estamos bien?

---

## [05:05 – 15:00] Máscaras de red, notación y subredes

**Profesor:** Okay. Después, lo que comentaban: esto ya estamos hablando de máscaras de red, ¿okay? Entonces, nativamente yo así lo veo: cuando nosotros estemos con una clase A, su máscara de red es una diagonal 8 (/8). Nosotros, ¿cómo representamos esa diagonal 8, se acuerdan? ¿Con decimales?

**Alumnos:** 255.0.0.0.

**Profesor:** ¿Vale? Mientras que para una clase B, ¿cómo queda?

**Alumnos:** 255.255.0.0.

**Profesor:** ¿Y para una clase C?

**Alumnos:** 255.255.255.0.

**Profesor:** ¿Vale? Entonces, aquí nos está diciendo que para una diagonal 8, el primer octeto es exclusivo ¿de qué?

**Alumnos:** De red.

**Profesor:** De red. ¿Estamos de acuerdo? Para una clase B estamos viendo que vamos a utilizar dos octetos en los cuales los dos primeros siempre van a ser o los vamos a considerar como porción de red, ¿vale? Y para una clase C... ¿estamos de acuerdo? Porción de red. Pero también nosotros manejamos ¿qué? Porción de red y porción de host. ¿Estamos de acuerdo? Cuando entremos al subneteo, tenemos porción de red, porción de subred y porción de host. ¿Estamos? Muy bien. Entonces, con base a esto, si yo quiero representar una diagonal 12 (/12), ¿cómo le tengo que hacer? ¿Cuántos bits son los que requiero yo poner para decir que estoy utilizando una diagonal 12?

**Alumno:** Son cuatro bits.

**Profesor:** Cuatro más que estoy agarrando, ¿estamos de acuerdo? Si es una clase A y me están pidiendo una diagonal 12, entonces estoy diciendo que es 1 2 3 4 5 6 7 8 normales, que esos son inamovibles, esos no los puedo yo mover dependiendo de la clase, pero sí la siguiente porción cuando yo subneteo, es decir, cuando hago más pequeña mi red de inicio, puedo agarrar los bits que necesite yo. En este caso yo voy a agarrar 1 2 3 4, ¿y cuántos me quedan para host? 1 2 3 4, me quedan 8 del siguiente octeto y 8 del último octeto. ¿Estamos de acuerdo? ¿Sí? Okay. Entonces, este número de aquí me está diciendo los bits que yo estoy utilizando para mi porción de red, ¿vale? Para un caso de una diagonal 23 (/23), ¿cómo quedaría? ¿Qué me está diciendo? ¿Cuántos bits debo de utilizar para la porción de red?

**Alumnos:** 23.

**Profesor:** 23. Es decir, es una clase B y me estoy metiendo ¿con qué? Con el tercer octeto, ¿para qué? Para crear subredes ¿de quién? De una clase B. ¿Estamos? Entonces, ¿qué tengo que poner? Tengo que poner 8 bits, 8 bits (van 16), 17, 18, 19, 20, 21, 22, 23, y el último sería 0. ¿Estamos de acuerdo? Ya nada más nos falta completar el último con ceros, ¿no? Okay. Por último, vámonos con una clase C. Díganme una dirección de clase C, jóvenes.

**Alumno:** 192.168.1.2.

**Profesor:** 192.168.1.2. Así como está esa dirección IP, ¿qué diagonal es?

**Alumnos:** 24.

**Profesor:** 24. ¿Por qué 24?

**Alumna:** Porque toma tres octetos para la red.

**Profesor:** Exactamente. ¿Por qué? Ya no hay más octetos para agarrar para subnetear, ¿estamos de acuerdo? El último octeto que tiene aquí lo tengo destinado ¿para quién? Para los puros hosts. Entonces, ¿cuántas direcciones IP o cuántos dispositivos puedo conectar aquí?

**Alumnos:** 254.

**Profesor:** 254. Va de la 1 a la 254. ¿Y dónde queda el 0? ¿Y dónde queda el 255?

**Alumno:** Uno es para la red...

**Profesor:** A la red. Siempre que nosotros veamos un cero al final, es nuestro identificador de red dependiendo de dónde partimos. Y por último, ¿el 255 qué es?

**Alumnos:** Broadcast.

**Profesor:** Es broadcast. Para nosotros, ¿qué es un mensaje de broadcast, jóvenes? ¿Recuerdan?

**Alumna:** Es un mensaje que se envía a todos los hosts.

**Profesor:** Un mensaje de saludo. ¿Estamos de acuerdo? Es como cuando llego aquí al salón y les digo «Hola, buenos días». Todos me escuchan, la mayoría me contesta, otros no me contestan, es decir, les estoy enviando un saludo a todos. Y eso es a lo que también hacemos referencia a la dirección de broadcast que nosotros debemos de quitar. Siempre nosotros en hosts vamos a quitar dos: uno, el identificador de red que siempre lo vamos a ver con cero al final, y la dirección de broadcast, que es la que utilizamos para enviar un saludo para todos. ¿Sale, jóvenes? ¿Hasta el momento vamos bien? Okay. Bien.

---

## [15:00 – 24:00] Diapositivas: conceptos de IPv4, modelo OSI y enrutamiento

**Profesor:** Ahorita vamos a presentar el documento que les subí, que ya algunos ya lo vieron y si no ahorita lo vemos, para que recordemos todo el repaso de las clases y demás. ¿Estamos? ¿Dudas hasta aquí? ¿Recordaron ya un poquito de lo que alguna vez vieron? Sí, aunque sea por encimita. Ya después lo vamos a plasmar en ejemplos que ustedes van a desarrollar. Okay, entonces... ¿Se ve bien o apagamos la luz de atrás? ¿Sí? Vale.

Okay, estos son apuntes que yo manejo para el repaso. No los vamos a leer todos, vamos a hacer nada más pausa en lo que yo considero importante. Ya ustedes al ratito en su casa le dan otra repasada más en forma, ¿les parece?

Entonces, estamos hablando de IPv4, que es la versión del protocolo de Internet. Es el principal protocolo utilizado a nivel de red en el modelo TCP/IP. Fue descrito inicialmente en unos documentos que se llaman el RFC 791. Opera en la capa de red del modelo OSI. La unidad de información que intercambia se denomina paquete o datagrama. Es un protocolo no orientado a conexión. ¿Qué entienden ustedes o recuerdan ustedes por protocolo no orientado a conexión? ¿No? Okay. Cuando hablamos nosotros de protocolo no orientado a conexión, es decir: nosotros mandamos información pero no es necesario que nos contesten que sí llegó, ¿sale? Nosotros seguimos mandando información sin necesidad de saber si llegó o no llegó. A diferencia de lo que es el protocolo TCP, que ahí sí forzosamente lo que mandemos me tiene que llegar una contestación de decir «¿Sabes qué? Sí llegó», y si no, vuélvelo a mandar, ¿por qué? Porque no llegó el paquete. ¿Estamos de acuerdo?

Okay. Fragmenta y reensambla los paquetes si es necesario. Direccionamiento mediante direcciones lógicas de 32 bits que ya vimos. Lo que se maneja ahora es el IPv6, y el IPv6 estamos hablando de 128 bits, o sea, un chorizo bastante pesado, ¿okay? Pero con que nosotros entendamos este direccionamiento es más que suficiente para poder saltar a IPv6. Es prácticamente lo mismo, nada más que cambian las direcciones si nosotros lo vemos como hexadecimales. Tamaño máximo del paquete de 65,535 bytes y se define en la RFC 791.

Ejecuta las siguientes operaciones: define un paquete y un esquema de direccionamiento. El direccionamiento que nosotros vimos de entrada, ¿qué fue? Direccionamiento por clases, en la cual vimos que es una A, una B y una C. La D y la E sí sabemos, pero no las vamos a utilizar, nada más esas tres primeras, ¿okay? Con los rangos correspondientes quitándole a quién: a la 127, que como su compañero lo dijo bien, es para loopback. ¿Nosotros para qué utilizamos el loopback, se acuerdan? Para pruebas. Pruebas para qué: para ver si nuestra tarjeta de red está funcionando y otras utilidades que podemos hacer con esa dirección IP de loopback.

Permite que un paquete sea enviado desde un host. Para nosotros, ¿qué es un host?

**Alumnos:** Es un dispositivo conectado a la red.

**Profesor:** Es un dispositivo que está conectado a nuestra red, ¿estamos de acuerdo? En donde en una clase C máximo tenemos conectados 254 con una dirección. ¿Estamos? Para una clase A tenemos... son pocas redes y muchos hosts, son 16 millones. Por eso es de que nos quitamos ya todas las direcciones IP, por eso es de que ahora tenemos otros mecanismos que nos proporcionan esas direcciones IP para que nosotros podamos salir afuera y navegar. ¿Estamos?

La estructura de la dirección IP es una dirección única, no se puede duplicar. Nosotros tampoco podemos hacer eso porque inmediatamente ¿qué pasa? Nos marca un error, es como si fuese un número telefónico. Todos los dispositivos participantes en la red, incluyendo los ruteadores... Los mecanismos que nosotros vamos a utilizar para comunicarnos entre redes es un dispositivo intermedio que se llama el router. En Redes 1, ¿qué utilizaron ustedes para comunicarse entre su red LAN? ¿Qué dispositivo intermedio utilizaron?

**Alumnos:** El switch.

**Profesor:** El switch, exactamente. ¿El switch qué es? ¿En qué capa trabaja?

**Alumnos:** En la capa 2.

**Profesor:** En la capa 2, exactamente. También me imagino que llegaron a verlo o platicaron que también hay switch capa 3. Ese switch capa 3 tiene la posibilidad de convertirse en ¿qué? En un router. ¿Estamos? Muy bien. Cuando estuvimos en Redes 1 y utilizábamos el switch, ¿utilizábamos direcciones IP forzosamente para podernos comunicar? Pues no, ¿por qué? Porque trabajamos en capa 2, en capa de enlace de datos. ¿Cómo nos comunicábamos? Por el MAC de nuestra tarjeta de red. ¿Estamos de acuerdo? Aquí sí es importante: nosotros sí, al utilizar un router, necesitamos forzosamente decirle de qué red viene y con qué dispositivo de la otra red se quiere comunicar, ¿vale? Para que vayan viendo las diferencias entre una y otra.

---

## [24:00 – 38:00] Jerarquías, IANA, clases y rangos

**Profesor:** La dirección IP lógica es de 32 bits, consta de dos partes: la primera parte identifica la red (el identificador de red que siempre lo vamos a ver con un cero al final), mientras que todo lo restante después de ese cero se convierte en host, dependiendo de los segmentos que estemos utilizando de la clase que estemos checando. ¿Estamos bien, jóvenes?

Bien, y aquí nos dice más o menos la jerarquía que utiliza, ¿no? Esta es una dirección clase ¿qué?

**Alumnos:** A.

**Profesor:** A. El primer octeto nos lo dice, ¿no? Es el número 10, y nos dice que una clase A va de la 1 a la 126. Sin embargo, ¿qué dice, jóvenes? Diagonal 24 (/24). ¿Qué nos está diciendo esto?

**Alumno:** Que ya está subneteada.

**Profesor:** Que ya está subneteada. ¿Estamos? Ya utilizamos este octeto y este octeto, y nos está diciendo que a partir de la .0, que es mi identificador de red, cuando ya cambia de 0 a 1 hasta el 254 ya estamos hablando de host, ¿okay? Aquí, por ejemplo, nuestra primera IP utilizable que es para nuestro router es la .1, inmediatamente después de nuestro identificador de red. Pero partimos de ¿qué? De una clase tipo A subneteada a ¿qué? A una clase C. ¿Sale?

Todo esto del direccionamiento IP depende de una organización que se llama la IANA. La IANA es a nivel mundial que empezó a dar este tipo de direcciones conforme a las clases que en ese momento se utilizaron. A nosotros en Latinoamérica nos corresponde LACNIC, y en este caso para nosotros donde estamos es NIC México, la Network Information Center. Administra el espacio de direcciones a nivel de país.

Para la clase A, ya sabemos que es una diagonal 8, esa es su máscara de red, y nos está diciendo que la primera porción exclusivamente es de red, ¿sale? ¿Qué notan en esta dirección IP?

**Alumno:** Que está subneteada.

**Profesor:** Que está subneteada, ¿por qué? Porque está como si fuese una clase B, ¿no? Porque estamos utilizando el siguiente octeto. De este lado es una clase... es la 172, ¿no? Los dos primeros: dirección de red. Esta es una nativa, llamémosle así. ¿Estamos? Por último, la C: las tres porciones (diagonal 24) y por último el cero. Nos está diciendo que es un identificador de red de una clase C nativa. Y nos está diciendo también que, dependiendo la clase, los octetos que se estén utilizando dentro de la misma son inamovibles. ¿Qué movemos? Los ceros de los demás segmentos que necesitemos para realizar un subneteo.

Para una clase A podemos tener 16,777,214 equipos conectados a cada una de las redes. Para nosotros sacar el número de equipos que podemos conectar a cada una de nuestras redes es el número de ceros que queda disponible, en este caso para una clase A quedan 24 ceros disponibles (2^24 - 2). ¿Por qué le quitamos dos?

**Alumnos:** El ID de red y el broadcast.

**Profesor:** El ID de red y el broadcast. ¿Estamos? Siempre de cajón vamos a utilizar esa fórmula.

Ahora, fíjense en otra forma de diferenciar las clases rápidamente cuando nos presenten bits: en la clase A, el primer bit siempre va a comenzar con 0. Para la clase B, los primeros bits van a ser 1 0. Y para la clase C va a ser 1 1 0. ¿Dudas hasta aquí, chavos?

**Alumnos:** No.

**Profesor:** Bien.

---

## [38:00 – 54:05] Ejercicios prácticos en el pizarrón / proyección

> Las direcciones proyectadas no quedaron registradas. Sólo se conservan las intervenciones
> del profesor y las respuestas coreadas del grupo.

**Profesor:** Si ya más o menos lo captamos, vamos a pasar a que se nos quite un poquito el sueño, nos pongamos en marcha y recordemos lo que vimos hasta el momento, ¿les parece? ¿Derecha, izquierda o al centro?

**Alumnos:** Derecha.

**Profesor:** Derecha dijeron todos. ¿Derecha mía o derecha de ustedes? Empezamos por acá. Porfa, uno por uno, ¿sale? Para que se nos quite un poquito el sueño. La pura clase, y vamos checando todos que coincidamos, ¿vale? Adelante, porfa.

*(Los alumnos pasan uno por uno al frente a identificar la clase de las direcciones IP proyectadas.)*

**Profesor:** El que sigue. La que acaban de poner, ¿es clase qué?

**Alumnos:** C.

**Profesor:** Es C, ¿no? Bien, el que sigue, el que sigue, por favor.

**Profesor:** El siguiente, por favor.

**Profesor:** ¿Vamos bien? ¿La última no se ve? Ah, bueno, esa la dejamos pendiente. ¿Pero vamos bien todas las demás?

**Alumno:** Profe, ¿la tercera no sería...?

**Profesor:** ¿La tercera cuál fue? ¿Esta? ¿Estamos de acuerdo? Recuerden que la A tiene un octeto de red, la B mitad y mitad, y la C tres de red y uno de host.

**Profesor:** ¿Todo está bien? Entonces, sigamos. Fíjense en este ejemplo: esta dirección es la 112.0.0.0. ¿Qué clase es?

**Alumnos:** A.

**Profesor:** Es una clase A. ¿Y fíjense al final qué es lo que obtenemos aquí? Es una 112.1.2.3, es decir, ya se subneteó. Sin embargo, aquí vemos perfectamente de dónde viene esta subred. Viene de aquí, ¿estamos de acuerdo? Este 112.0.0.0 es el identificador de quién... de donde partió esta subred. ¿Por qué? Porque esos datos nosotros se los vamos a meter ¿a quién? Al router, para que identifique nuestros dispositivos que están conectados y de dónde provienen.

Ahora díganme la dirección de broadcast de esta dirección IP: 10.0.0.0. ¿Cuál sería?

**Alumnos:** 10.255.255.255.

**Profesor:** Los hosts tienen que estar encendidos (en 1s), entonces queda como 10.255.255.255. Ahora vámonos para una clase B: 140.10.0.0. ¿Cuál sería su dirección de broadcast?

**Alumnos:** 140.10.255.255.

**Profesor:** Ahora una C: 192.168.1.0. ¿Cuál es su dirección de broadcast?

**Alumnos:** 192.168.1.255.

**Profesor:** ¿Estamos? Esto partiendo de que todo sea bonito y agradable, porque ya cuando subneteamos cambia, pero sí tenemos que tener en cuenta de dónde venimos y cuáles son las facilidades.

Ahora, con esta serie de bits, díganme a qué clase pertenece. Porfa, ¿puedes pasar a resolver cada uno de ellos en donde nos están diciendo que ahora vamos a ver la clase dependiendo de los bits?

**Alumno:** El primero es clase C...

**Profesor:** Señala por qué para ti es clase C. ¿En dónde estás viendo tú que es clase C?

**Alumno:** En los primeros bits: 1 1 0.

**Profesor:** Es clase C, sí es clase C. Nos tenemos que fijar en los primeros bits: el primer bit es 1, el segundo bit es 1 y el tercer bit es 0 (110). ¿Estamos de acuerdo? Adelante, por favor, señorita.

**Profesor:** Perfecto, clase A, porque vimos que el primer bit corresponde a un 0. ¿Quién sigue, por favor? Adriel, pasa.

**Profesor:** Muy bien. Vamos a comprobar. Díganme este binario a qué corresponde en decimal con su calculadora: 10000111.

**Alumnos:** 135.

**Profesor:** 135. ¿Qué clase es?

**Alumnos:** B.

**Profesor:** ¿Sí? Okay, entonces, ¿corresponde o no corresponde? Sí, este 135 cabe dentro de una clase B. Nos ahorramos todo el show de ver los números, simplemente con los bits lo identificamos. ¿Dudas, preguntas?

---

## [54:05 – 56:53] Conclusiones y cierre

**Profesor:** ¿Vamos a desayunar, les parece? Okay, chavos, empezamos así tranquilitos, poco a poco se va a ir complicando, ¿vale? Nos vemos mañana primeramente.

**Alumno:** *(Pregunta al profesor sobre las clases de red y la cantidad de hosts/redes.)*

**Profesor:** Lo que analizamos el día de hoy fue que para pocos hosts y muchas redes es mejor para nosotros como administradores dedicar nada más a manejar el número de identificadores que se puedan... Recuerden que el número de hosts que vamos a tener... ya después con CIDR podemos subnetear para las redes específicas que queramos.

**Alumnos:** *(Pláticas informales de salida mientras guardan sus cosas.)*
