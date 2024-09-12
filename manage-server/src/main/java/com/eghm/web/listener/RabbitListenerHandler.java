package com.eghm.web.listener;

import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constant.QueueConstant;
import com.eghm.model.ManageLog;
import com.eghm.mq.listener.AbstractListenerHandler;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.sys.ManageLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2022/7/28
 */
@Component
@Slf4j
public class RabbitListenerHandler extends AbstractListenerHandler {

    private final ManageLogService manageLogService;

    private final MemberCouponService memberCouponService;

    public RabbitListenerHandler(JsonService jsonService, AlarmService alarmService, ManageLogService manageLogService, MemberCouponService memberCouponService) {
        super(jsonService, alarmService);
        this.manageLogService = manageLogService;
        this.memberCouponService = memberCouponService;
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

}
