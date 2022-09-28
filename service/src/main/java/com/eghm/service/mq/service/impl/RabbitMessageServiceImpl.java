package com.eghm.service.mq.service.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.CommonConstant;
import com.eghm.model.dto.ext.AsyncKey;
import com.eghm.service.cache.CacheService;
import com.eghm.service.mq.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/10 15:55
 */
@Service("rabbitMessageService")
@Slf4j
public class RabbitMessageServiceImpl implements MessageService {

    private AmqpTemplate amqpTemplate;

    private CacheService cacheService;

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired(required = false)
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void sendDelay(Object msg, String exchange, int delay) {
        this.sendDelay(msg, "", exchange, delay);
    }

    @Override
    public void sendDelay(Object msg, String routeKey, String exchange, int delay) {
        amqpTemplate.convertAndSend(exchange, routeKey, msg, message -> {
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
        this.send(msg, "", exchange);
    }

    @Override
    public void sendAsync(AsyncKey msg, String exchange) {
        amqpTemplate.convertAndSend(exchange, "", msg);
        cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), CacheConstant.PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
    }

    @Override
    public void sendAsync(AsyncKey msg, String routeKey, String exchange) {
        amqpTemplate.convertAndSend(exchange, routeKey, msg);
        cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), CacheConstant.PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
    }

}
