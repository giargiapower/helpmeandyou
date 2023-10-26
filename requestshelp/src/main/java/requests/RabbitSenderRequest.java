package requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
public class RabbitSenderRequest{

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey2}")
    private String routing2key;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.queue2}")
    private Queue queue;

    private static Logger logger = LogManager.getLogger(RabbitSenderRequest.class.toString());


    public void send(requests.model.RichiestaAiuto richiesta) {
        requests.model.Indirizzo indirizzo = richiesta.getIndirizzo();
        requests.model.Message message = new requests.model.Message(richiesta.getId(), richiesta.getGiorno().toString(), indirizzo.getProvincia(), richiesta.getIdMateriale());
        rabbitTemplate.convertAndSend(exchange, routing2key, message);
        logger.info("Sending Message to the Queue");
    }

}