package com.eghm.configuration.task.job;

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

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    /**
     * 门票支付定时
     * @param param
     */
    public void ticketPaying(String param) {
        log.info("门票支付定时任务开始执行 [{}]", param);
        List<String> payingList = orderService.getPayingList();
        for (String orderNo : payingList) {
            orderService.orderPayNotify(orderNo);
        }
        log.info("门票支付定时任务执行完毕 [{}]", param);
    }

    /**
     * 门票退款定时任务
     * @param param
     */
    public void ticketRefunding(String param) {
        List<OrderRefund> refundList = orderRefundLogService.getTicketRefunding();
        for (OrderRefund refund : refundList) {
            orderService.orderRefundNotify(refund.getOutTradeNo(), refund.getOutRefundNo());
        }
    }

}
