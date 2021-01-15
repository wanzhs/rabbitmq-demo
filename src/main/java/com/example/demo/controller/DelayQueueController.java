package com.example.demo.controller;

import com.example.demo.config.RabbitMqConfig;
import com.example.demo.sender.RabbitMqSender;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.RabbitMqConfig.*;

/**
 * todo
 *
 * @author wanzhongsu
 * @date 2021/1/15 16:13
 */
@RestController
@RequestMapping("/delay/queue")
public class DelayQueueController {

    @Resource
    private RabbitMqSender rabbitMqSender;


    @GetMapping("/test1")
    public void test1() {
        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 * 1000);
        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 * 1000);
        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 * 1000);
    }

    @GetMapping("/test2")
    public void test2() {
        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 * 1000);
        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 * 1000);
        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 * 1000);

    }

    @GetMapping("/test3")
    public void test3() {
        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 * 1000);
        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 * 1000);
        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 * 1000);
    }

    @GetMapping("/test4")
    public void test4() {
        rabbitMqSender.sendMsg("456", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 5 * 1000);
        rabbitMqSender.sendMsg("798", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 1 * 1000);
        rabbitMqSender.sendMsg("123", RabbitMqConfig.EXCHANGE_QUESTION_EXPIRE_DL, RabbitMqConfig.ROUTINGKEY_QUESTION_EXPIRE_DL, 10 * 1000);
    }
}
