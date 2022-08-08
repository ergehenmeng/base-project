package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.ScheduledTask;
import com.eghm.model.dto.ext.OrderRefund;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/8
 */
@Component("taskService")
@AllArgsConstructor
@Slf4j
public class TaskService {

    private final OrderService ticketOrderService;

    private final OrderRefundLogService orderRefundLogService;

    @ScheduledTask
    public void ticketPaying(String param) {
        log.info("门票支付定时任务开始执行 [{}]", param);
        List<String> payingList = ticketOrderService.getPayingList();
        for (String orderNo : payingList) {
            ticketOrderService.orderPay(orderNo);
        }
        log.info("门票支付定时任务执行完毕 [{}]", param);
    }

    @ScheduledTask
    public void ticketRefunding(String param) {
        List<OrderRefund> refundList = orderRefundLogService.getTicketRefunding();
        for (OrderRefund refund : refundList) {
            ticketOrderService.orderRefund(refund.getOutTradeNo(), refund.getOutRefundNo());
        }
    }

}
