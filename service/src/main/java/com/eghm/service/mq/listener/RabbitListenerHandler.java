package com.eghm.service.mq.listener;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.QueueConstant;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.event.impl.TicketEvent;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.SystemException;
import com.eghm.model.ManageLog;
import com.eghm.model.WebappLog;
import com.eghm.model.dto.ext.AsyncKey;
import com.eghm.model.dto.ext.LoginRecord;
import com.eghm.service.business.handler.dto.ItemOrderCreateContext;
import com.eghm.service.business.handler.dto.OrderCancelContext;
import com.eghm.service.cache.CacheService;
import com.eghm.service.sys.ManageLogService;
import com.eghm.service.sys.WebappLogService;
import com.eghm.service.user.LoginService;
import com.eghm.state.machine.StateHandler;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Consumer;

import static com.eghm.common.constant.CacheConstant.ERROR_PLACE_HOLDER;
import static com.eghm.common.constant.CacheConstant.SUCCESS_PLACE_HOLDER;

/**
 * @author 二哥很猛
 * @date 2022/7/28
 */
@Component
@AllArgsConstructor
@Slf4j
public class RabbitListenerHandler {

    private final WebappLogService webappLogService;

    private final LoginService loginService;

    private final ManageLogService manageLogService;

    private final CacheService cacheService;

    private final StateHandler stateHandler;

    /**
     * 零售商品消息队列订单过期处理
     * TODO 待完善事件类型
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.ITEM_PAY_EXPIRE_QUEUE)
    public void itemExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.AUTO_CANCEL, message, channel);
    }

    /**
     * 门票队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.TICKET_PAY_EXPIRE_QUEUE)
    public void ticketExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.CANCEL, message, channel);
    }

    /**
     * 民宿队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.HOMESTAY_PAY_EXPIRE_QUEUE)
    public void homeStayExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.CANCEL, message, channel);
    }

    /**
     * 餐饮券队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.VOUCHER_PAY_EXPIRE_QUEUE)
    public void voucherExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.CANCEL, message, channel);
    }

    /**
     * 线路队列订单过期处理
     * @param orderNo 订单编号
     */
    @RabbitListener(queues = QueueConstant.LINE_PAY_EXPIRE_QUEUE)
    public void lineExpire(String orderNo, Message message, Channel channel) throws IOException {
        this.doOrderExpire(orderNo, TicketEvent.CANCEL, message, channel);
    }
    
    /**
     * 订单30分钟过期处理
     * @param orderNo 订单编号
     * @param event 状态机事件, 不同品类事件不一样
     * @param message mq消息
     * @param channel mq channel
     */
    private void doOrderExpire(String orderNo, IEvent event, Message message, Channel channel) throws IOException {
        OrderCancelContext context = new OrderCancelContext();
        context.setOrderNo(orderNo);
        processMessageAck(context, message, channel, orderCancelContext ->
                // 开启状态机流转
                stateHandler.fireEvent(ProductType.prefix(orderNo), OrderState.NONE.getValue(), event, orderCancelContext)
        );
    }
    
    /**
     * 移动端异常日志
     */
    @RabbitListener(queues = QueueConstant.WEBAPP_LOG_QUEUE)
    public void webappLog(WebappLog webappLog, Message message, Channel channel) throws IOException {
        processMessageAck(webappLog, message, channel, webappLogService::insertWebappLog);
    }

    /**
     * 移动端登陆日志
     */
    @RabbitListener(queues = QueueConstant.LOGIN_LOG_QUEUE)
    public void loginLog(LoginRecord loginRecord, Message message, Channel channel) throws IOException {
        processMessageAck(loginRecord, message, channel, loginService::insertLoginLog);
    }

    /**
     * 管理后台操作日志
     */
    @RabbitListener(queues = QueueConstant.MANAGE_OP_LOG_QUEUE)
    public void manageOpLog(ManageLog log, Message message, Channel channel) throws IOException {
        processMessageAck(log, message, channel, manageLogService::insertManageLog);
    }

    /**
     * 消息队列门票下单
     * @param dto 订单信息
     */
    @RabbitListener(queues = QueueConstant.TICKET_ORDER_QUEUE)
    public void ticketOrder(ItemOrderCreateContext dto, Message message, Channel channel) throws IOException {
        this.processMessageAckAsync(dto, message, channel, order -> {
            // TODO 下单逻辑处理
        });
    }

    @RabbitListener(queues = "test_queue")
    public void test(String msg, Message message, Channel channel) throws IOException {
        processMessageAck(msg, message, channel, s -> {
            log.info("接收到消息 : [{}]", s);
        });
    }

    /**
     * 处理MQ中消息,并手动确认,并将结果放入缓存方便客户端查询
     * @param msg 消息
     * @param message message
     * @param channel channel
     * @param consumer 业务
     * @param <T> 消息类型
     * @throws IOException e
     */
    public <T extends AsyncKey> void processMessageAckAsync(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            consumer.accept(msg);
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), SUCCESS_PLACE_HOLDER);
        } catch (SystemException e) {
            log.error("队列[{}]处理消息业务异常 [{}] [{}] [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e.getCode(), e.getMessage());
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), e.getMessage());
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e);
            cacheService.setValue(CacheConstant.MQ_ASYNC_KEY + msg.getKey(), ERROR_PLACE_HOLDER);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 处理MQ中消息,并手动确认
     * @param msg 消息
     * @param message message
     * @param channel channel
     * @param consumer 业务
     * @param <T> 消息类型
     * @throws IOException e
     */
    public static <T> void processMessageAck(T msg, Message message, Channel channel, Consumer<T> consumer) throws IOException {
        try {
            consumer.accept(msg);
        } catch (Exception e) {
            log.error("队列[{}]处理消息异常 [{}] [{}]", message.getMessageProperties().getConsumerQueue(), msg, message, e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
