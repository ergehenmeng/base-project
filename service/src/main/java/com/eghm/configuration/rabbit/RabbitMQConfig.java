package com.eghm.configuration.rabbit;

import com.eghm.common.constant.CommonConstant;
import com.eghm.common.enums.ExchangeQueue;
import com.eghm.configuration.log.LogTraceHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

    /**
     * 自定义配置
     */
    @PostConstruct
    public void config() {
        RabbitAdmin rabbitAdmin = (RabbitAdmin) this.amqpAdmin;
        log.info("****初始化rabbit日志追踪配置****");
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        rabbitTemplate.setBeforePublishPostProcessors(message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader(CommonConstant.TRACE_ID, LogTraceHolder.get());
            return message;
        });
        rabbitTemplate.setAfterReceivePostProcessors(message -> {
            MessageProperties properties = message.getMessageProperties();
            String traceId = properties.getHeader(CommonConstant.TRACE_ID);
            MDC.put(CommonConstant.TRACE_ID, traceId);
            LogTraceHolder.put(traceId);
            return message;
        });
    }
}
