package com.example.demo.listener;


import com.example.demo.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = RabbitMqConfig.QUEUE_QUESTION_EXPIRE)
public class RabbitMqReceiver {

    /**
     *  死信监听
     * */

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitHandler
    public void process(String content) {

        logger.info("received msg:{} ", content);

    }
}
