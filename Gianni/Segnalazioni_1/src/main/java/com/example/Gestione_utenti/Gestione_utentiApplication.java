package com.example.Gestione_utenti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class Gestione_utentiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gestione_utentiApplication.class, args);
	}

}
