package com.eghm.mq.service.impl;

import com.eghm.cache.CacheService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.enums.ExchangeQueue;
import com.eghm.mq.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛 2022/6/10 15:55
 */
@Slf4j
@RequiredArgsConstructor
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final CacheService cacheService;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendDelay(ExchangeQueue queue, Object msg, int delay) {
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), msg, message -> {
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
    public void sendAsync(ExchangeQueue queue, AsyncKey msg) {
        rabbitTemplate.convertAndSend(queue.getExchange(), queue.getRoutingKey(), msg);
        cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), CacheConstant.PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
    }

}
