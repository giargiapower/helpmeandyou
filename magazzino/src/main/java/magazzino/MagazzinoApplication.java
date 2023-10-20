package magazzino;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.class})
public class MagazzinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagazzinoApplication.class, args);
    }

}
