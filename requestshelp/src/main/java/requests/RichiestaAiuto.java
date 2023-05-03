package requests;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class})
public class RichiestaAiuto {

	public static void main(String[] args) {
		SpringApplication.run(RichiestaAiuto.class, args);
	}

}