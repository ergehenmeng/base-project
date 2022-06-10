package com.eghm.service.mq.impl;

import com.eghm.service.mq.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wyb 2022/6/10 15:55
 */
@Service("rabbitService")
@Slf4j
public class RabbitServiceImpl implements RabbitService {

    private AmqpTemplate amqpTemplate;

    @Autowired(required = false)
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendDelay(Object msg, String exchange, int delay) {
        this.sendDelay(msg, null, exchange, delay);
    }

    @Override
    public void sendDelay(Object msg, String routeKey, String exchange, int delay) {
        amqpTemplate.convertAndSend(exchange, null, msg, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setDelay(delay * 1000);
            return message;
        });
    }

    @Override
    public void send(Object msg, String routeKey, String exchange) {
        amqpTemplate.convertAndSend(exchange, routeKey, msg);
    }

    @Override
    public void send(Object msg, String exchange) {
        this.send(msg, null, exchange);
    }


}
