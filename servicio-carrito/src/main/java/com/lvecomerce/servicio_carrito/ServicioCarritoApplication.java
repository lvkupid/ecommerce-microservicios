package com.lvecomerce.servicio_carrito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServicioCarritoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioCarritoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
  		return new RestTemplate();
 	}

}
