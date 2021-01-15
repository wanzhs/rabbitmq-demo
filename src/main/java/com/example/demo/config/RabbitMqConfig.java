package com.example.demo.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_QUESTION_EXPIRE = "exchange-question-expire";
    public static final String QUEUE_QUESTION_EXPIRE = "queue-question-expire";
    public static final String ROUTINGKEY_QUESTION_EXPIRE = "sroutingKey-question-expire";

    public static final String EXCHANGE_QUESTION_EXPIRE_DL = "exchange-question-expire-dl";
    public static final String QUEUE_QUESTION_EXPIRE_DL = "queue-question-expire-dl";
    public static final String ROUTINGKEY_QUESTION_EXPIRE_DL = "sroutingKey-question-expire-dl";
    @Resource
    private ConnectionFactory connectionFactory;

    @Bean
    public String createAutomatically() {
        RabbitAdmin rabbitAdmin = rabbitAdmin(connectionFactory);
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", EXCHANGE_QUESTION_EXPIRE);
        arguments.put("x-dead-letter-routing-key", ROUTINGKEY_QUESTION_EXPIRE);
        Queue dlQueue = new Queue(QUEUE_QUESTION_EXPIRE_DL, true, false, false, arguments);

        DirectExchange dlExchange = new DirectExchange(EXCHANGE_QUESTION_EXPIRE_DL);
        rabbitAdmin.declareExchange(dlExchange);
        rabbitAdmin.declareQueue(dlQueue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(dlQueue).to(dlExchange).with(ROUTINGKEY_QUESTION_EXPIRE_DL));


        Map<String, Object> args = new HashMap<>(2);
        args.put("x-delayed-type", "direct");
        Queue queue = new Queue(QUEUE_QUESTION_EXPIRE, true, false, false, args);

        DirectExchange exchange = new DirectExchange(EXCHANGE_QUESTION_EXPIRE);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_QUESTION_EXPIRE));
        return "abc";
    }

    ;

    private RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
