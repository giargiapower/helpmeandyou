package com.GestoreCredenziali.GestoreCredenziali;

import com.GestoreCredenziali.GestoreCredenziali.Model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private Queue queue;
    private static Logger logger = LogManager.getLogger(RabbitSender.class.toString());


    public void send(Account account) {
       // Account account = new Account();
        rabbitTemplate.convertAndSend(queue.getName(), account);
        logger.info("Sending Message to the Queue : " + account.getNome());
        System.out.println("Sending Message to the Queue : " + account.getNome());
    }
}
