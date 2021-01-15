package com.example.demo.controller;

import com.example.demo.service.IRabbitService;
import com.example.demo.util.RabbitConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * todo
 *
 * @author wanzhongsu
 * @date 2021/1/15 16:30
 */
@RestController
@RequestMapping("/x/delay/plugin")
public class XDelayPluginController {
    @Resource
    private IRabbitService rabbitService;

    @GetMapping("/test")
    public void test() throws Exception {
        String msg = "first sent";
        rabbitService.sendJson(RabbitConstant.EXCHANGE_QUERY_ABC,
                RabbitConstant.ROUTING_ACCOUNT, msg, 10);
        Thread.sleep(1 * 1000);
        msg = "second sent";
        rabbitService.sendJson(RabbitConstant.EXCHANGE_QUERY_ABC,
                RabbitConstant.ROUTING_ACCOUNT, msg, 3);
    }
}
