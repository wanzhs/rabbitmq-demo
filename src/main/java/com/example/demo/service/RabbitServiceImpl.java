package com.example.demo.service;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author:luqi
 * @description: rabbitMq 发送
 * @date:2018/11/8_15:54
 * @param:
 * @return:
 */
@Slf4j
@Service("rabbitService")
public class RabbitServiceImpl implements IRabbitService {


    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendJson(String exchange, String queueName, Object object) {
        String jsonStr = JSONUtil.toJsonStr(object);
        rabbitTemplate.convertAndSend(exchange, queueName, jsonStr);
    }

    @Override
    public void sendJson(String exchange, String queueName, Object object, int time) {
        if (time <= 0) {
            //直接发送
            sendJson(exchange, queueName, object);
        } else {
            int mTime = time * 1000;
            //延时发送
            MessagePostProcessor processor = message1 -> {
                message1.getMessageProperties().setDelay(mTime);
                return message1;
            };
            String jsonStr = JSONUtil.toJsonStr(object);
            rabbitTemplate.convertAndSend(exchange, queueName, jsonStr, processor);
        }
    }
}
