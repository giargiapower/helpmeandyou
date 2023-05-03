package com.GestoreCredenziali.GestoreCredenziali;

import com.GestoreCredenziali.GestoreCredenziali.File.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableConfigurationProperties({
		FileStorageProperties.class
})
@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class})
public class GestoreCredenzialiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestoreCredenzialiApplication.class, args);
	}

}
