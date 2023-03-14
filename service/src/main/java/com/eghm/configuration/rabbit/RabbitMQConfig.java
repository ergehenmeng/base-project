package com.eghm.configuration.rabbit;

import com.eghm.common.enums.ExchangeQueue;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mq配置
 *
 * @author 二哥很猛 2022/6/10 11:21
 */
@Configuration
@Slf4j
@AutoConfigureAfter(RabbitAutoConfiguration.class)
@AllArgsConstructor
public class RabbitMQConfig {

    private final AmqpAdmin amqpAdmin;

    @Bean
    public void init() {
        log.info("****初始化rabbit消息队列开始****");
        for (ExchangeQueue value : ExchangeQueue.values()) {
            Queue queue = QueueBuilder.durable(value.getQueue()).build();
            amqpAdmin.declareQueue(queue);
            String exchangeType = value.getExchangeType().name().toLowerCase();
            ExchangeBuilder builder = new ExchangeBuilder(value.getExchange(), exchangeType).durable(true);
            if (value.isDelayed()) {
                builder.delayed().withArgument("x-delayed-type", exchangeType);
            }
            Exchange exchange = builder.build();
            amqpAdmin.declareExchange(exchange);
            amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(value.getRoutingKey()).noargs());
        }
        log.info("****初始化rabbit消息队列结束****");
    }

}
