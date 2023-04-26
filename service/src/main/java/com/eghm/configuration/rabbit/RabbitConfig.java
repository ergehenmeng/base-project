package com.eghm.configuration.rabbit;

import com.eghm.constant.CommonConstant;
import com.eghm.enums.ExchangeQueue;
import com.eghm.configuration.log.LogTraceHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
public class RabbitConfig {
    
    private final AmqpAdmin amqpAdmin;
    
    private final RabbitTemplate rabbitTemplate;

    @Bean
    public void init() {
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
        rabbitTemplate.setBeforePublishPostProcessors(message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader(CommonConstant.TRACE_ID, LogTraceHolder.get());
            return message;
        });
        log.info("\n=================================================\n\t" +
                "          初始化rabbit消息队列完成\n" +
                "=================================================");
    }
    
    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple", matchIfMissing = true)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setAfterReceivePostProcessors(message -> {
            MessageProperties properties = message.getMessageProperties();
            String traceId = properties.getHeader(CommonConstant.TRACE_ID);
            MDC.put(CommonConstant.TRACE_ID, traceId);
            LogTraceHolder.put(traceId);
            return message;
        });
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
