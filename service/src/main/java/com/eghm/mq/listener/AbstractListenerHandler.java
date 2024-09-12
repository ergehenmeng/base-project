package com.eghm.mq.listener;

import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * 抽象的监听器rabbit处理类
 * 说明: 为了进行移动端和管理后台的通讯, 需要将mq的virtual-host设置为同一个
 * 同时由于移动端和管理后台都依赖service服务, 为了尽量减少消息消费时乱串(即:移动端的消息,在管理后台的服务上消费), 需要将消息消费监听写在web层
 * 注意: 在特殊的业务场景下, 可能需要管理后台发消息, 移动端消费(反之亦然), 定义队列和交换机时尽量命名规范
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractListenerHandler {

    private final JsonService jsonService;

    private final AlarmService alarmService;

    /**
     * 处理MQ中消息,并手动确认
     *
     * @param msg      消息
     * @param message  message
     * @param channel  channel
     * @param consumer 业务
     * @param <T>      消息类型
     * @throws IOException e
     */
    public <T> void processMessageAck(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            consumer.accept(msg);
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}]", message.getMessageProperties().getConsumerQueue(), jsonService.toJson(msg), e);
            alarmService.sendMsg(String.format("队列[%s]消息消费失败[%s]", message.getMessageProperties().getConsumerQueue(), jsonService.toJson(msg)));
        } finally {
            MessageProperties properties = message.getMessageProperties();
            if (Boolean.TRUE.equals(properties.getRedelivered())) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
    }
}
