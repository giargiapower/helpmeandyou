package requests;

import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import requests.repository.AccountRepository;

@CrossOrigin(origins = "http://localhost:4200")
@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitReciver {

    @Autowired
    AccountRepository accountRepository;

    private static Logger logger = LogManager.getLogger(RabbitReciver.class.toString());

    @RabbitHandler
    public void  receiver(Account account) {
        logger.info("Account listener invoked - Consuming Message with Account Identifier : " + account.getEmail());
        /**ho dovuto fare questa cafonata percè rabbit ha dei problemi con il type id ovvero mi chiama
         la classe come com.GestioneCredenziali.....Account e non Account e basta dunque il reciever
         non capisce che classe sia . DUNQUE FORZO IL CASTING PER RISOLVERE IL PROBLEMA**/
        requests.model.Account accountEsistente = new requests.model.Account(account.getEmail(), account.getPassword(), account.getNome(), account.getCognome(), account.getTelefono(), account.getIndirizzo(), account.getStato(), account.getPath_curriculum(), account.getPath_documento(), account.getCategory());
        accountEsistente.setId(account.getId());
        accountRepository.save(accountEsistente);
    }

}
