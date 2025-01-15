package com.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.json.queue.name}")
    private String jsonQueue;

    @Value("${rabbitmq.json.queue.routing.key}")
    private String jsonQueueRoutingKey;

    //spring bean for rabbitmq
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    //bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    //bean for binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    //spring bean for rabbitmq queue (for store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }


   //bean for binding between json queue and exchange using routing key
    @Bean
    public Binding jsonQueuebinding(){
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(jsonQueueRoutingKey);
    }


    //bean for convert message object to json
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    //bean for set converter to RabbitTemplate
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){

        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }



    //connectionFactory
    //RabbitTemplate
    //RabbitAdmin
}
