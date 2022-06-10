package com.eghm.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * mq配置
 *
 * @author wyb 2022/6/10 11:21
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 订单过期交换机
     */
    private static final String EXCHANGE_ORDER_DELAY = "exchange_order_delay";

    /**
     * 订单过期处理队列
     */
    private static final String QUEUE_ORDER_DELAY = "queue_order_delay";

    /**
     * 死信队列交换机
     */
    private static final String EXCHANGE_DEAD_LETTER = "exchange_dead_letter";

    /**
     * 私信队列
     */
    private static final String QUEUE_DEAD_LETTER = "queue_dead_letter";

    /**
     * 死信队列交换机, 用于处理过期未处理或被拒绝的消息
     */
    @Bean
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_DEAD_LETTER).build();
    }

    /**
     * 死信队列, 用于处理过期未处理或被拒绝的消息
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_LETTER).build();
    }

    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("").noargs();
    }

    /**
     * 订单过期交换机
     */
    @Bean("orderExpireExchange")
    public Exchange orderExpireExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return ExchangeBuilder.fanoutExchange(EXCHANGE_ORDER_DELAY).delayed().durable(true).withArguments(args).build();
    }

    /**
     * 订单过期处理队列
     */
    @Bean("orderExpireQueue")
    public Queue orderExpireQueue() {
        return QueueBuilder.durable(QUEUE_ORDER_DELAY).build();
    }

    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding orderExpireBinding() {
        return BindingBuilder.bind(orderExpireQueue()).to(orderExpireExchange()).with("").noargs();
    }



}
