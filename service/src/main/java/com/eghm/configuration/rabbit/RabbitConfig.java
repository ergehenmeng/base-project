package com.eghm.configuration.rabbit;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ExchangeQueue;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * mq配置
 *
 * @author 二哥很猛 2022/6/10 11:21
 */
@Slf4j
@AllArgsConstructor
public class RabbitConfig {
    
    private final AmqpAdmin amqpAdmin;
    
    private final RabbitTemplate rabbitTemplate;

    private final DingTalkService dingTalkService;

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
            dingTalkService.sendMsg(String.format("mq消息发送失败,交换机:[%s] 路由key:[%s] 错误码:[%s] 错误信息:[%s]",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode(), returned.getReplyText()));
        });
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("mq消息确认失败, id:[{}] 错误消息:[{}]", correlationData != null ? correlationData.getId() : null, cause);
                dingTalkService.sendMsg(String.format("mq消息确认失败, 错误信息:[%s]", cause));
            }
        });
        LoggerUtil.print("rabbit消息队列初始化完成");
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
            LogTraceHolder.putTraceId(traceId);
            return message;
        });
        factory.setTaskExecutor(TtlExecutors.getTtlExecutor(new SimpleAsyncTaskExecutor("@RabbitListener线程-")));
        configurer.configure(factory, connectionFactory);
        return factory;
    }


}
