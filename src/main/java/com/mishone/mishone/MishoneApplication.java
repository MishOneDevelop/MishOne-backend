package com.mishone.mishone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Activa el soporte de tareas programadas
public class MishoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MishoneApplication.class, args);
	}

}
