package com.eghm.configuration.rabbit;

import com.eghm.common.enums.RabbitQueue;
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
     * 死信队列交换机, 用于处理过期未处理或被拒绝的消息
     */
    @Bean
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(RabbitQueue.DEAD_LETTER.getExchange()).build();
    }

    /**
     * 死信队列, 用于处理过期未处理或被拒绝的消息
     */
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(RabbitQueue.DEAD_LETTER.getQueue()).build();
    }

    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RabbitQueue.DEAD_LETTER.getRoutingKey()).noargs();
    }

    /**
     * 订单过期交换机
     */
    @Bean("orderPayExpireExchange")
    public Exchange orderPayExpireExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return ExchangeBuilder.topicExchange(RabbitQueue.ORDER_PAY_EXPIRE.getExchange()).delayed().durable(true).withArguments(args).build();
    }

    /**
     * 订单过期处理队列
     */
    @Bean("orderPayExpireQueue")
    public Queue orderPayExpireQueue() {
        return QueueBuilder.durable(RabbitQueue.ORDER_PAY_EXPIRE.getQueue()).build();
    }


    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding orderPayExpireBinding() {
        return BindingBuilder.bind(orderPayExpireQueue()).to(orderPayExpireExchange()).with(RabbitQueue.ORDER_PAY_EXPIRE.getRoutingKey()).noargs();
    }


    /**
     * 订单创建交换机
     */
    @Bean("orderCreateExchange")
    public Exchange orderCreateExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.ORDER_CREATE.getExchange()).durable(true).build();
    }

    /**
     * 订单创建处理队列
     */
    @Bean("orderCreateQueue")
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(RabbitQueue.ORDER_CREATE.getQueue()).build();
    }


    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding orderCreateBinding() {
        return BindingBuilder.bind(orderCreateQueue()).to(orderCreateExchange()).with(RabbitQueue.ORDER_CREATE.getRoutingKey()).noargs();
    }


    /**
     * 订单完成交换机
     */
    @Bean("orderCompleteExchange")
    public Exchange orderCompleteExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return ExchangeBuilder.topicExchange(RabbitQueue.ORDER_COMPLETE.getExchange()).delayed().durable(true).withArguments(args).build();
    }

    /**
     * 订单完成处理队列
     */
    @Bean("orderCompleteQueue")
    public Queue orderCompleteQueue() {
        return QueueBuilder.durable(RabbitQueue.ORDER_COMPLETE.getQueue()).build();
    }


    /**
     * 交换机与队列绑定
     */
    @Bean
    public Binding orderCompleteBinding() {
        return BindingBuilder.bind(orderCompleteQueue()).to(orderCompleteExchange()).with(RabbitQueue.ORDER_PAY_EXPIRE.getRoutingKey()).noargs();
    }
}
