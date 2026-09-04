# Tarea 2 — Cuestionario de redes de telefonía celular

**Escuela:** IPN — ESCOM / UPIIZ · Ingeniería en Sistemas Computacionales
**Asignatura:** Desarrollo de Aplicaciones Móviles Nativas — Grupo 7CM4
**Alumno:** Angel Frausto Robles
**Tema:** Redes de telefonía celular
**Fecha:** _[fecha de entrega]_

> Respuestas elaboradas con base en el documento de la Tarea 1: *DAMN Intro TecnoCelular.pdf*.

---

## CUESTIONARIO 1 — Respuesta breve

**1. ¿Cómo se comunican las células de una red de telefonía celular?**
De forma inalámbrica, mediante ondas de radio que viajan entre los teléfonos y las estaciones base (torres). El dispositivo convierte la voz o los datos en ceros y unos y luego en ondas electromagnéticas. Se usa transmisión **dúplex**: dos frecuencias al mismo tiempo, una para enviar y otra para recibir. Las antenas de las torres reciben e interpretan la señal y la mandan a una central que enruta la llamada o conexión. El territorio se divide en **celdas** para aprovechar el espacio y evitar interferencias.

**2. ¿Cuál es el rango de frecuencias en el que operan las redes de telefonía celular?**
Típicamente entre **700 MHz y 2.7 GHz** para redes 2G a 4G. En México (IFT) se usan la banda de **800 MHz**, la banda **PCS** (1850–1910 MHz y 1930–1990 MHz) y la banda **AWS** (1710–1780 MHz y 2110–2180 MHz). El 5G puede llegar a frecuencias mucho más altas, hasta unos **80 GHz**.

**3. ¿Qué diferencias existen entre las bandas bajas y altas en términos de alcance y penetración?**
Regla física: a mayor frecuencia, menor alcance y menor capacidad de atravesar obstáculos.
- **Bandas bajas (< 1 GHz, p. ej. 700–900 MHz):** gran cobertura (una antena cubre muchos kilómetros) y alta penetración (atraviesan paredes, edificios y sótanos). Ideales para zonas rurales, carreteras e interiores.
- **Bandas altas (> 2 GHz, p. ej. 2.6 GHz, 3.5 GHz y ondas milimétricas de 5G):** corto alcance (la señal se debilita en pocos cientos de metros) y baja penetración (muros, árboles e incluso la lluvia la bloquean), pero ofrecen alta velocidad y mucho ancho de banda. Ideales para zonas urbanas densas, estadios y centros comerciales.

**4. Describe el proceso de transición de una celda a otra (handover).**
Ocurre en milisegundos, sin cortar la llamada ni la descarga:
1. **Monitoreo constante:** el teléfono mide todo el tiempo la señal de la antena actual y de las vecinas.
2. **Reporte a la red:** envía periódicamente esos datos de calidad de señal a la central.
3. **Decisión de cambio:** cuando la señal de la antena actual cae por debajo de un límite y la de una vecina es más fuerte, la red inicia el intercambio.
4. **Asignación de canal:** la red pide a la nueva antena que reserve una frecuencia o canal para el teléfono.
5. **Desconexión y reconexión:** el teléfono se desconecta de la antena vieja y se conecta a la nueva casi al instante.

**5. ¿Qué ocurre cuando una celda se satura?**
Se produce **congestión de red** (la antena se queda sin canales libres):
- Llamadas caídas o bloqueadas ("Error de conexión" / "Red ocupada").
- Velocidad de datos en picada, porque el ancho de banda se reparte entre demasiados usuarios.
- Efecto **"respiración de celda"**: la antena reduce su área de cobertura para mantener la calidad de los usuarios cercanos, dejando sin señal a los de los bordes.
- Priorización de emergencias: la red puede cortar datos comunes para garantizar las llamadas a servicios de emergencia.

**6. ¿Cómo se decide dónde colocar una nueva antena de telefonía móvil?**
Mediante **planificación de radiofrecuencia (RF)**, considerando:
- Análisis de tráfico e historial (dónde fallan más las llamadas o baja la velocidad).
- Modelado en software 3D que simula cómo rebotan las ondas en edificios, árboles y montañas.
- Densidad de población y puntos de interés (centros comerciales, desarrollos nuevos, transporte).
- Límites de interferencia con las torres vecinas para que no usen las mismas frecuencias a la vez.
- Viabilidad técnica y legal: acceso a energía eléctrica, conexión a fibra óptica y permisos del gobierno local.

**7. ¿Qué dice la OMS sobre la radiación emitida por las antenas de telefonía?**
Según la OMS y la ICNIRP, las antenas emiten **radiación no ionizante**, que no tiene energía suficiente para dañar el ADN ni las células. Los organismos internacionales regulan la **potencia máxima de emisión**, no una distancia mínima de separación. En México la Cofepris vigila que los operadores no rebasen los límites de densidad de potencia permitidos.

**8. ¿Qué diferencias existen entre la radiación de una torre de telefonía y un módem Wi-Fi?**
Ambos emiten el mismo tipo de radiación **no ionizante**. Las diferencias son de potencia y distancia:

| Característica | Módem Wi-Fi casero | Torre celular grande |
|---|---|---|
| Potencia típica | ~0.1 W (100 mW) | 20 a 100+ W (200–1000 veces más) |
| Frecuencias | 2.4, 5 y 6 GHz | 700 MHz a 3.5 GHz (hasta 80 GHz en 5G) |
| Cobertura | 20–50 m | 1–15 km |
| Tipo de antena | Omnidireccional | Direccional (por sectores) |
| Nivel que recibes en casa | Mayor (estás muy cerca) | Menor (se atenúa antes de llegar) |

Por la ley del cuadrado inverso, aunque la torre es mucho más potente, al estar lejos su señal llega muy debilitada; el módem, al estar a pocos metros, aporta un nivel mayor.

**9. ¿Por qué el Wi-Fi gasta menos batería que los datos celulares?**
- **Distancia a la antena:** el módem está a pocos metros, así que el teléfono transmite con potencia mínima; para alcanzar una torre lejana tiene que "gritar" con mucha más fuerza.
- **Estabilidad de la señal:** el Wi-Fi es una red fija; con datos celulares el teléfono hace *handover* constante entre antenas, y ese monitoreo y cambio de frecuencias consume mucha energía.
- **Zonas de baja cobertura:** con mala señal celular el teléfono sube su potencia de transmisión al máximo, se calienta y agota la batería.

**10. ¿Cuáles son las implicaciones del Criterio de Nyquist en telecomunicaciones?**
El criterio establece que la frecuencia de muestreo debe ser al menos el doble de la frecuencia máxima de la señal: **fs ≥ 2·fmax**. Implicaciones:
1. **Evitar el aliasing:** si no se cumple, las componentes de frecuencia se superponen y no se puede reconstruir la señal original.
2. **Requisitos de muestreo:** define la frecuencia mínima de muestreo para capturar toda la información de la señal analógica.
3. **Aplicaciones prácticas:** clave en la digitalización de señales (p. ej. audio analógico a digital fiel al original).
4. **Diseño de sistemas de comunicación:** ayuda a definir los parámetros de muestreo y filtrado para una transmisión y recepción correctas.

---

## CUESTIONARIO 2 — Opción múltiple

| # | Pregunta | Respuesta |
|---|---|---|
| 1 | Tipo de señales que usan las redes celulares | **b) Señales de radio** |
| 2 | Frecuencia común en las bandas usadas en México | **b) 800 MHz** |
| 3 | Característica de las bandas bajas de frecuencia | **b) Alta penetración** |
| 4 | En el handover, qué hace el teléfono primero | **b) Monitorea la señal de las antenas** |
| 5 | Fenómeno cuando una celda está saturada | **b) Congestión de red** |
| 6 | Tipo de radiación que emiten las antenas según la OMS | **b) No ionizante** |
| 7 | Factor que NO se considera al colocar una nueva antena | **b) Color del terreno** |
| 8 | Banda de Wi-Fi conocida por su mayor alcance | **a) 2.4 GHz** |
| 9 | Por qué el modo ahorro de batería es beneficioso | **b) Alarga la salud de la batería** |
| 10 | Relación entre frecuencia de muestreo y frecuencia máxima (Nyquist) | **c) Al menos el doble** |
