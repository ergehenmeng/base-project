package com.eghm.web.listener;

import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constants.QueueConstant;
import com.eghm.dto.ext.OrderPayNotify;
import com.eghm.dto.ext.RefundAudit;
import com.eghm.dto.ext.SocketMsg;
import com.eghm.enums.ProductType;
import com.eghm.model.ManageLog;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.sys.ManageLogService;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.eghm.constants.CommonConstant.WEBSOCKET_PREFIX;

/**
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Component
@Slf4j
public class ManageListenerHandler extends AbstractListenerHandler {

    private final ManageLogService manageLogService;

    private final MemberCouponService memberCouponService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ManageListenerHandler(JsonService jsonService, AlarmService alarmService, ManageLogService manageLogService, MemberCouponService memberCouponService, SimpMessagingTemplate simpMessagingTemplate) {
        super(jsonService, alarmService);
        this.manageLogService = manageLogService;
        this.memberCouponService = memberCouponService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 管理后台操作日志
     */
    @RabbitListener(queues = QueueConstant.MANAGE_OP_LOG_QUEUE)
    public void manageOpLog(ManageLog log, Message message, Channel channel) throws IOException {
        processMessageAck(log, message, channel, manageLogService::insertManageLog);
    }

    /**
     * 优惠券自动过期
     */
    @RabbitListener(queues = QueueConstant.COUPON_EXPIRE_QUEUE)
    public void couponExpire(Long couponId, Message message, Channel channel) throws IOException {
        processMessageAck(couponId, message, channel, memberCouponService::couponExpire);
    }

    /**
     * 订单支付成功后，发送发货通知
     */
    @RabbitListener(queues = QueueConstant.ORDER_PAY_NOTICE_QUEUE)
    public void payNotice(OrderPayNotify notify, Message message, Channel channel) throws IOException {
        processMessageAck(notify, message, channel, msg -> {
            if (msg.getProductType() == ProductType.ITEM) {
                simpMessagingTemplate.convertAndSend(WEBSOCKET_PREFIX + "/order/broadcast/" + notify.getMerchantId(), SocketMsg.delivery(Lists.newArrayList(msg.getOrderNo())));
            }
        });
    }

    /**
     * 退款申请后，发送审核通知
     */
    @RabbitListener(queues = QueueConstant.ORDER_AUDIT_NOTICE_QUEUE)
    public void refundNotice(RefundAudit notify, Message message, Channel channel) throws IOException {
        processMessageAck(notify, message, channel, msg -> {
            if (msg.getOrderNo().startsWith(ProductType.ITEM.getPrefix())) {
                simpMessagingTemplate.convertAndSend(WEBSOCKET_PREFIX + "/order/broadcast/" + notify.getMerchantId(), SocketMsg.refund(Lists.newArrayList(msg.getOrderNo())));
            }
        });
    }
 }
