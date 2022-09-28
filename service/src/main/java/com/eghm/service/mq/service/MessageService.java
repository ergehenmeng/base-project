package com.eghm.service.mq.service;

import com.eghm.model.dto.ext.AsyncKey;

/**
 * @author 二哥很猛 2022/6/10 15:55
 */
public interface MessageService {

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

    /**
     * 发送异步消息(后续消息处理结果会放在缓存中)
     * @param msg 消息
     * @param exchange 交换机名称
     */
    void sendAsync(AsyncKey msg, String exchange);

    /**
     * 发送异步消息(后续消息处理结果会放在缓存中)
     * @param msg 消息
     * @param routeKey 路由key 前提exchange支持
     * @param exchange 交换机
     */
    void sendAsync(AsyncKey msg, String routeKey, String exchange);
}
