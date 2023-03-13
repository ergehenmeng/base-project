package com.eghm.configuration.rabbit;

import com.eghm.common.enums.RabbitQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * mq配置
 *
 * @author 二哥很猛 2022/6/10 11:21
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class RabbitMQConfig {

    private final RabbitAdmin rabbitAdmin;

    @PostConstruct
    public void init() {
        log.info("****开始初始化rabbit消息队列****");
        for (RabbitQueue value : RabbitQueue.values()) {
            Queue queue = QueueBuilder.durable(value.getQueue()).build();
            rabbitAdmin.declareQueue(queue);
            String exchangeType = value.getExchangeType().name().toLowerCase();
            ExchangeBuilder builder = new ExchangeBuilder(value.getExchange(), exchangeType).durable(true);
            if (value.isDelayed()) {
                builder.delayed().withArgument("x-delayed-type", exchangeType);
            }
            Exchange exchange = builder.build();
            rabbitAdmin.declareExchange(exchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(value.getRoutingKey()).noargs());
        }
        log.info("****初始化rabbit消息队列成功****");
    }

}
