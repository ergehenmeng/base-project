package com.eghm.enums;

import com.eghm.constants.QueueConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用于定义RabbitMQ的枚举, 注意:routingKey不支持多配置
 *
 * @author 二哥很猛
 * @since 2022/6/12 18:47
 */
@Getter
@AllArgsConstructor
public enum ExchangeQueue {

    /**
     * 管理后台操作日志队列
     */
    MANAGE_LOG("manage_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.MANAGE_LOG_QUEUE}, "", false),

    /**
     * 移动端用户登录日志队列
     */
    LOGIN_LOG("login_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.LOGIN_LOG_QUEUE}, "", false),

    /**
     * 移动端异常日志
     */
    WEBAPP_LOG("webapp_log_exchange", ExchangeType.DIRECT, new String[]{QueueConstant.WEBAPP_LOG_QUEUE}, "", false);

    /**
     * mq交换机名称
     */
    private final String exchange;

    /**
     * 交换机类型
     */
    private final ExchangeType exchangeType;

    /**
     * mq队列名称
     */
    private final String[] queue;

    /**
     * 路由key
     */
    private final String routingKey;

    /**
     * 是否为延迟队列
     */
    private final boolean delayed;

}
