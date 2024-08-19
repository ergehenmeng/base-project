package com.eghm.configuration.rabbit;

import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ExchangeQueue;
import com.eghm.common.AlarmService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;

/**
 * RabbitMQ 消息队列初始化配置
 * 说明: 该配置类与RabbitConfig不放在一起的原因是因为该类采用构造方法注入的方式，MessageConverter定义会循环依赖
 *
 * @author 二哥很猛
 * @since 2023/12/6
 */
@Slf4j
@AllArgsConstructor
public class RabbitInitConfig {

    private final AmqpAdmin amqpAdmin;

    private final AlarmService alarmService;

    private final RabbitTemplate rabbitTemplate;

    @Bean
    public void init() {
        for (ExchangeQueue value : ExchangeQueue.values()) {
            String exchangeType = value.getExchangeType().name().toLowerCase();
            ExchangeBuilder builder = new ExchangeBuilder(value.getExchange(), exchangeType).durable(true);
            if (value.isDelayed()) {
                builder.delayed().withArgument("x-delayed-type", exchangeType);
            }
            Exchange exchange = builder.build();
            amqpAdmin.declareExchange(exchange);
            for (String queueName : value.getQueue()) {
                Queue queue = QueueBuilder.durable(queueName).build();
                amqpAdmin.declareQueue(queue);
                amqpAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(value.getRoutingKey()).noargs());
            }
        }
        rabbitTemplate.setBeforePublishPostProcessors(message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader(CommonConstant.TRACE_ID, LogTraceHolder.getTraceId());
            return message;
        });
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("mq消息发送失败,交换机:[{}] 路由key:[{}] 错误码:[{}] 错误信息:[{}] 消息:[{}]",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText(), new String(returned.getMessage().getBody()));
            alarmService.sendMsg(String.format("mq消息发送失败,交换机:[%s] 路由key:[%s] 错误码:[%s] 错误信息:[%s]",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText()));
        });
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("mq消息确认失败, id:[{}] 错误消息:[{}]", correlationData != null ? correlationData.getId() : null, cause);
                alarmService.sendMsg(String.format("mq消息确认失败, 错误信息:[%s]", cause));
            }
        });
        LoggerUtil.print("rabbit消息队列初始化完成");
    }
}
