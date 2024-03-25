package com.eghm.service.business.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.AsyncKey;
import com.eghm.dto.ext.RefundAudit;
import com.eghm.enums.ExchangeQueue;
import com.eghm.service.business.OrderMQService;
import com.eghm.mq.service.MessageService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.utils.TransactionUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 订单处理类
 *
 * @author 二哥很猛
 * @since 2022/7/28
 */

@Slf4j
@AllArgsConstructor
@Service("orderMQService")
public class OrderMQServiceImpl implements OrderMQService {

    private final MessageService messageService;

    private final SysConfigApi sysConfigApi;

    @Override
    public void sendOrderExpireMessage(ExchangeQueue exchangeQueue, String orderNo) {
        int expireTime = sysConfigApi.getInt(ConfigConstant.ORDER_EXPIRE_TIME);
        log.info("订单过期延迟队列发送消息 [{}] [{}]", exchangeQueue, orderNo);
        messageService.sendDelay(exchangeQueue, orderNo, expireTime);
    }

    @Override
    public void sendOrderCreateMessage(ExchangeQueue exchangeQueue, AsyncKey context) {
        context.setKey(IdUtil.fastSimpleUUID());
        messageService.sendAsync(exchangeQueue, context);
    }

    @Override
    public void sendOrderCompleteMessage(ExchangeQueue exchangeQueue, String orderNo) {
        // 事务提交后再进行发送消息
        TransactionUtil.afterCommit(() -> {
            log.info("订单完成发送实时消息 [{}] [{}]", exchangeQueue, orderNo);
            messageService.send(ExchangeQueue.ORDER_COMPLETE, orderNo);
            int evaluateTime = sysConfigApi.getInt(ConfigConstant.ORDER_EVALUATE_TIME);
            log.info("订单完成发送延迟评价消息 [{}] [{}]", exchangeQueue, orderNo);
            messageService.sendDelay(exchangeQueue, orderNo, evaluateTime);
            int delayRoutingTime = sysConfigApi.getInt(ConfigConstant.DELAY_ROUTING_TIME);
            log.info("订单完成发送延迟分账消息 [{}] [{}]", exchangeQueue, orderNo);
            messageService.sendDelay(exchangeQueue, orderNo, delayRoutingTime);
        });
    }

    @Override
    public void sendRefundAuditMessage(ExchangeQueue exchangeQueue, RefundAudit audit) {
        int confirmTime = sysConfigApi.getInt(ConfigConstant.ORDER_REFUND_CONFIRM_TIME, 172800);
        messageService.sendDelay(exchangeQueue, audit, confirmTime);
    }

    @Override
    public void sendReturnRefundAuditMessage(ExchangeQueue exchangeQueue, RefundAudit audit) {
        int confirmTime = sysConfigApi.getInt(ConfigConstant.ORDER_RETURN_REFUND_TIME, 604800);
        messageService.sendDelay(exchangeQueue, audit, confirmTime);
    }
}
