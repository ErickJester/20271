package com.ipn.mx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 29sept: clase de arranque que genera Spring Initializr. @SpringBootApplication activa la configuración
// automática y hace que Spring busque componentes (@RestController, @Service, @Entity...) en este paquete
// com.ipn.mx y en todos los que cuelgan de él, como com.ipn.mx.evento. Por eso las demás clases deben quedar
// dentro de com.ipn.mx: si estuvieran fuera, Spring no las vería.
@SpringBootApplication
public class ApiEventosApplication {

	// El main arranca todo: levanta el servidor web embebido (puerto 8080), conecta con MySQL y deja la API lista.
	public static void main(String[] args) {
		SpringApplication.run(ApiEventosApplication.class, args);
	}

}
