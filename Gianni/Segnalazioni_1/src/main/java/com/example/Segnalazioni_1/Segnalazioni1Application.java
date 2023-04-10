package com.example.Segnalazioni_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class Segnalazioni1Application {

	public static void main(String[] args) {
		SpringApplication.run(Segnalazioni1Application.class, args);
	}

}
