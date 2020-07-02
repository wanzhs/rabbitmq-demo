package com.example.demo.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_QUESTION_EXPIRE = "exchange-question-expire";
    public static final String QUEUE_QUESTION_EXPIRE = "queue-question-expire";
    public static final String ROUTINGKEY_QUESTION_EXPIRE = "sroutingKey-question-expire";

    public static final String EXCHANGE_QUESTION_EXPIRE_DL = "exchange-question-expire-dl";
    public static final String QUEUE_QUESTION_EXPIRE_DL = "queue-question-expire-dl";
    public static final String ROUTINGKEY_QUESTION_EXPIRE_DL = "sroutingKey-question-expire-dl";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
