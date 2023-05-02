package com.GestoreCredenziali.GestoreCredenziali;

import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class GestoreCredenzialiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestoreCredenzialiApplication.class, args);
	}

}
