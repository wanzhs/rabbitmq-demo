package com.example.demo;

import com.example.demo.config.RabbitMqConfig;
import com.example.demo.sender.RabbitMqSender;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.RabbitMqConfig.*;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Test
    public void test() {
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


//        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 *1000);
//        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 *1000);
//        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 *1000);

//        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 *1000);
//        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 *1000);
//        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 *1000);
//
//        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 *1000);
//        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 *1000);
//        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 *1000);
//
        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 * 1000);
        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 * 1000);
        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 * 1000);
    }

}
