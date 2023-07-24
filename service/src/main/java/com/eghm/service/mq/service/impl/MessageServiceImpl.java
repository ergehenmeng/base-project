package com.eghm.service.mq.service.impl;

import com.eghm.constant.CacheConstant;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;
import com.eghm.service.cache.CacheService;
import com.eghm.service.mq.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/10 15:55
 */
@Service("messageService")
@Slf4j
public class MessageServiceImpl implements MessageService {

    private RabbitTemplate rabbitTemplate;

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }
    
    @Autowired(required = false)
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendDelay(ExchangeQueue queue, Object msg, int delay) {
        this.sendDelay(queue.getExchange(), queue.getRoutingKey(), msg, delay);
    }

    @Override
    public void sendDelay(String exchange, String routingKey, Object msg, int delay) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg, message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setDelay(delay * 1000);
            return message;
        });
    }

    @Override
    public void send(ExchangeQueue queue, Object msg) {
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), msg);
    }

    @Override
    public void send(String exchange, String routingKey, Object msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }

    @Override
    public void sendAsync(ExchangeQueue queue, AsyncKey msg) {
        this.sendAsync(queue.getExchange(), queue.getRoutingKey(), msg);
    }

    @Override
    public void sendAsync(String exchange, String routingKey, AsyncKey msg) {
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
        cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), CacheConstant.PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
    }
}
