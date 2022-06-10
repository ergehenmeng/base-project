package com.eghm.service.mq;

/**
 * @author wyb 2022/6/10 15:55
 */
public interface RabbitService {

    /**
     * 发送延迟消息
     * @param msg 消息
     * @param exchange 交换机
     * @param delay 延迟多少秒
     */
    void sendDelay(Object msg, String exchange, int delay);

    /**
     * 发送延迟消息
     * @param msg 消息
     * @param routeKey 路由key 前提exchange支持
     * @param exchange 交换机
     * @param delay 延迟多少秒
     */
    void sendDelay(Object msg, String routeKey, String exchange, int delay);

    /**
     * 发送普通消息
     * @param msg 消息
     * @param routeKey 路由key 前提exchange支持
     * @param exchange 交换机
     */
    void send(Object msg, String routeKey, String exchange);

    /**
     * 发送普通消息
     * @param msg 消息
     * @param exchange 交换机
     */
    void send(Object msg, String exchange);
}
