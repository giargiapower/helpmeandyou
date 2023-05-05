package magazzino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class})
public class MagazzinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazzinoApplication.class, args);
    }

}
