package com.example.demo.service;


public interface IRabbitService {


    /**
     * 发送json
     */
    void sendJson(String exchange, String queueName, Object object);

    /**
     * 延时发送 秒
     */
    void sendJson(String exchange, String queueName, Object object, int time);
}
