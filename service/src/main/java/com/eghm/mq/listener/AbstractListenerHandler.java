package com.eghm.mq.listener;

import com.eghm.cache.CacheService;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.exception.BusinessException;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.util.function.Consumer;

import static com.eghm.constants.CacheConstant.ERROR_PLACE_HOLDER;
import static com.eghm.constants.CacheConstant.SUCCESS_PLACE_HOLDER;
import static com.eghm.utils.StringUtil.isBlank;

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

    private final CacheService cacheService;

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

    /**
     * 处理MQ中消息,并手动确认,并将结果放入缓存方便客户端查询
     *
     * @param msg      消息
     * @param message  message
     * @param channel  channel
     * @param consumer 业务
     * @param <T>      消息类型
     * @throws IOException e
     */
    protected <T extends AsyncKey> void processMessageAckAsync(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            log.info("开始处理MQ异步消息 [{}]", jsonService.toJson(msg));
            if (this.canConsumer(msg.getKey())) {
                consumer.accept(msg);
                // 消费成功,将结果放入缓存方便前端查询结果
                cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), SUCCESS_PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
            } else {
                log.warn("消息已超时,不做任何业务处理 [{}]", msg);
            }
        } catch (BusinessException e) {
            log.error("队列[{}]处理消息业务异常 [{}] [{}] [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e.getCode(), e.getMessage());
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), e.getMessage(), CommonConstant.ASYNC_MSG_EXPIRE);
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e);
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), ERROR_PLACE_HOLDER, CommonConstant.ASYNC_MSG_EXPIRE);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 判断消费是否可以要继续处理下单逻辑
     * 1. 消息在队列长达30分钟, 肯定不允许下单成功
     * 2. 如果前端一直轮训获取结果,到上限后会直接提示商品太火爆,因此如果下单还在队列中,则不允许下单
     *
     * @param asyncKey key
     * @return 是否允许下单
     */
    private boolean canConsumer(String asyncKey) {
        String hasValue = cacheService.getValue(CacheConstant.MQ_ASYNC_KEY + asyncKey);
        // 可能key过期了
        if (isBlank(hasValue)) {
            return false;
        }
        String accessStr = hasValue.replace(CacheConstant.PLACE_HOLDER, "");
        // 前端还没请求呢, 可以直接处理
        if (isBlank(accessStr)) {
            return true;
        }
        // 表示已经处理过了, 此次是重试
        if (accessStr.contains(ERROR_PLACE_HOLDER)) {
            return true;
        }
        int accessNum = Integer.parseInt(accessStr);
        return accessNum < CommonConstant.MAX_ACCESS_NUM;
    }
}
