package com.eghm.mq.service;

import com.eghm.dto.ext.BaseAsyncKey;
import com.eghm.enums.ExchangeQueue;

/**
 * @author 二哥很猛 2022/6/10 15:55
 */
public interface MessageService {

    /**
     * 发送延迟消息
     *
     * @param queue 队列或交换机
     * @param msg   消息
     * @param delay 延迟多少秒
     */
    void sendDelay(ExchangeQueue queue, Object msg, int delay);

    /**
     * 发送延迟消息
     *
     * @param exchange   队列或交换机
     * @param routingKey key
     * @param msg        消息
     * @param delay      延迟多少秒
     */
    void sendDelay(String exchange, String routingKey, Object msg, int delay);

    /**
     * 发送普通消息
     *
     * @param queue 队列及交换机信息
     * @param msg   消息
     */
    void send(ExchangeQueue queue, Object msg);

    /**
     * 发送普通消息
     *
     * @param exchange   队列及交换机信息
     * @param routingKey key
     * @param msg        消息
     */
    void send(String exchange, String routingKey, Object msg);

    /**
     * 发送异步消息(后续消息处理结果会放在缓存中)
     *
     * @param queue 队列及交换机
     * @param msg   消息
     */
    void sendAsync(ExchangeQueue queue, BaseAsyncKey msg);

    /**
     * 发送异步消息(后续消息处理结果会放在缓存中)
     *
     * @param exchange   队列及交换机信息
     * @param routingKey key
     * @param msg        消息
     */
    void sendAsync(String exchange, String routingKey, BaseAsyncKey msg);
}
