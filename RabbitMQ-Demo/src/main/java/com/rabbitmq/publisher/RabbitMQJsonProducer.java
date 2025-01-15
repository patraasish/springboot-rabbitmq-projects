package com.rabbitmq.publisher;

import com.rabbitmq.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.json.queue.routing.key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;



    public void sendJSONMessage(User user ){

        LOGGER.info(String.format("json message sent -> %s",user.toString()));
        rabbitTemplate.convertAndSend(exchange,routingKey,user);
    }

}
