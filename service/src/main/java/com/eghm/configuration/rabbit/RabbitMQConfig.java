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
 * @author 二哥很猛 2022/6/10 11:21
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

    /**
     * 优惠券领取创建交换机
     */
    @Bean("couponReceiveExchange")
    public Exchange couponReceiveExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.COUPON_RECEIVE.getExchange()).durable(true).build();
    }

    /**
     * 优惠券领取创建处理队列
     */
    @Bean("couponReceiveQueue")
    public Queue couponReceiveQueue() {
        return QueueBuilder.durable(RabbitQueue.COUPON_RECEIVE.getQueue()).build();
    }

    /**
     * 优惠券领取交换机与队列绑定
     */
    @Bean
    public Binding couponReceiveBinding() {
        return BindingBuilder.bind(couponReceiveQueue()).to(couponReceiveExchange()).with(RabbitQueue.COUPON_RECEIVE.getRoutingKey()).noargs();
    }

    /**
     * 门票交换机
     */
    @Bean("ticketOrderExchange")
    public Exchange ticketOrderExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.TICKET_ORDER.getExchange()).durable(true).build();
    }

    /**
     * 门票处理队列
     */
    @Bean("ticketOrderQueue")
    public Queue ticketOrderQueue() {
        return QueueBuilder.durable(RabbitQueue.TICKET_ORDER.getQueue()).build();
    }

    /**
     * 门票交换机与队列绑定
     */
    @Bean
    public Binding ticketOrderBinding() {
        return BindingBuilder.bind(ticketOrderQueue()).to(ticketOrderExchange()).with(RabbitQueue.TICKET_ORDER.getRoutingKey()).noargs();
    }

    /**
     * 餐饮交换机
     */
    @Bean("voucherOrderExchange")
    public Exchange voucherOrderExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.VOUCHER_ORDER.getExchange()).durable(true).build();
    }

    /**
     * 餐饮处理队列
     */
    @Bean("voucherOrderQueue")
    public Queue voucherOrderQueue() {
        return QueueBuilder.durable(RabbitQueue.VOUCHER_ORDER.getQueue()).build();
    }

    /**
     * 餐饮交换机与队列绑定
     */
    @Bean
    public Binding voucherOrderBinding() {
        return BindingBuilder.bind(voucherOrderQueue()).to(voucherOrderExchange()).with(RabbitQueue.VOUCHER_ORDER.getRoutingKey()).noargs();
    }

    /**
     * 民宿交换机
     */
    @Bean("homestayOrderExchange")
    public Exchange homestayOrderExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.HOMESTAY_ORDER.getExchange()).durable(true).build();
    }

    /**
     * 民宿处理队列
     */
    @Bean("homestayOrderQueue")
    public Queue homestayOrderQueue() {
        return QueueBuilder.durable(RabbitQueue.HOMESTAY_ORDER.getQueue()).build();
    }

    /**
     * 民宿交换机与队列绑定
     */
    @Bean
    public Binding homestayOrderBinding() {
        return BindingBuilder.bind(homestayOrderQueue()).to(homestayOrderExchange()).with(RabbitQueue.HOMESTAY_ORDER.getRoutingKey()).noargs();
    }

    /**
     * 商品交换机
     */
    @Bean("productOrderExchange")
    public Exchange productOrderExchange() {
        return ExchangeBuilder.topicExchange(RabbitQueue.PRODUCT_ORDER.getExchange()).durable(true).build();
    }

    /**
     * 商品处理队列
     */
    @Bean("productOrderQueue")
    public Queue productOrderQueue() {
        return QueueBuilder.durable(RabbitQueue.PRODUCT_ORDER.getQueue()).build();
    }

    /**
     * 商品交换机与队列绑定
     */
    @Bean
    public Binding productOrderBinding() {
        return BindingBuilder.bind(productOrderQueue()).to(productOrderExchange()).with(RabbitQueue.PRODUCT_ORDER.getRoutingKey()).noargs();
    }
}
