package com.example.Segnalazioni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SegnalazioniApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegnalazioniApplication.class, args);
	}

}
