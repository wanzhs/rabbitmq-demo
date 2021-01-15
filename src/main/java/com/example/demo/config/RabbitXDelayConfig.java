package com.example.demo.config;


import com.example.demo.util.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列设置
 *
 * @author wanzhongsu
 * @date 2021/1/15 16:46
 */
@Slf4j
@Configuration
public class RabbitXDelayConfig {

    @Value("${rabbitmq.username:guest}")
    private String userName;

    @Value("${rabbitmq.password:guest}")
    private String password;

    @Value("${rabbitmq.host:127.0.0.1}")
    private String host;

    @Value("${rabbitmq.port:5672}")
    private int port;

    @Value("${rabbitmq.virtual-host-charge:/}")
    private String virtualHostCharge;

    @Value("${rabbitmq.publisher-confirms:true}")
    private boolean publisherConfirms;

    @Value("${rabbitmq.publisher-returns:true}")
    private boolean publisherReturns;

    @Bean("cdzConnectionFactory")
    public ConnectionFactory cdzConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setPublisherReturns(publisherReturns);
        connectionFactory.setVirtualHost(virtualHostCharge);
        return connectionFactory;
    }

    @Bean("payRabbitTemplate")
    public RabbitTemplate payRabbitTemplate(@Qualifier("cdzConnectionFactory") ConnectionFactory payConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(payConnectionFactory);
        return template;
    }

    @Bean("payRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory
    payRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                      @Qualifier("cdzConnectionFactory") ConnectionFactory payConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setConcurrentConsumers(10);
        factory.setPrefetchCount(10);
        configurer.configure(factory, payConnectionFactory);
        return factory;
    }

    @Bean
    public String abcExchange(@Qualifier("cdzConnectionFactory") ConnectionFactory cdzConnectionFactory) {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        Channel channel = cdzConnectionFactory.createConnection().createChannel(false);
        try {
            channel.exchangeDeclare(RabbitConstant.EXCHANGE_QUERY_ABC, "x-delayed-message", true, false, false, args);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (Exception e) {
                log.error("mq channel close fail", e);
            }
        }
        return RabbitConstant.EXCHANGE_QUERY_ABC;
    }

    @Bean
    public String accountPayQueue(@Qualifier("cdzConnectionFactory") ConnectionFactory connectionFactory) {
        Channel channel = connectionFactory.createConnection().createChannel(false);
        try {
            channel.queueDeclare(RabbitConstant.QUEUE_ACCOUNT, true, false,
                    false, null);
            channel.queueBind(RabbitConstant.QUEUE_ACCOUNT, RabbitConstant.EXCHANGE_QUERY_ABC,
                    RabbitConstant.ROUTING_ACCOUNT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (Exception e) {
                log.error("mq channel close fail", e);
            }
        }
        return RabbitConstant.QUEUE_ACCOUNT;
    }
}
