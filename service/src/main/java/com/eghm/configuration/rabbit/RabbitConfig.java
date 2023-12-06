package com.eghm.configuration.rabbit;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.eghm.configuration.log.LogTraceHolder;
import com.eghm.constant.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
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

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
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
