package magazzino;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(origins = "http://localhost:4200")
@Component
@RabbitListener(queues = "rabbitmq.queue2", id = "listener")
public class RabbitReciver {
    @Autowired
    magazzino.repo.MagazzinoRepository magRepository;

    @Autowired
    magazzino.repo.MaterialeRepository matRepository;

    @Autowired
    magazzino.repo.RichiestaAiutoMatRepository ricRepository;

    private static Logger logger = LogManager.getLogger(RabbitReciver.class.toString());


    @RabbitHandler
    public void receiver(requests.model.Message messaggio) {
        logger.info("message received");
        java.util.Optional<magazzino.model.Materiale> materiale = matRepository.findById(messaggio.getMateriale_id());
        if (materiale.isPresent()) {
            java.time.LocalDate giorno = java.time.LocalDate.parse(messaggio.getGiorno());
            magazzino.model.RichiestaAiutoMat newRichiesta = new magazzino.model.RichiestaAiutoMat(messaggio.getRequest_id(), giorno, messaggio.getProvincia(), materiale.get());
            // Aggiungo la richiesta alla lista di richieste del materiale
            materiale.get().aggiungiRichiesta(newRichiesta);
            // Salvo la richiesta sul database
            ricRepository.save(newRichiesta);
            // Salvo le modifiche sul database
            matRepository.save(materiale.get());
        }
    }
}


