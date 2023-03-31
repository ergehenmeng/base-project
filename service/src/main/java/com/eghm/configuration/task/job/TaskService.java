package com.eghm.configuration.task.job;

import com.eghm.model.Order;
import com.eghm.dto.ext.OrderRefund;
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
     * 定时任务处理: 支付中的订单
     */
    public void payProcess(String param) {
        log.info("门票支付定时任务开始执行 [{}]", param);
        List<Order> processList = orderService.getProcessList();
        // TODO 待完成
        log.info("门票支付定时任务执行完毕 [{}]", param);
    }

    /**
     * 门票退款定时任务
     */
    public void refundProcess(String param) {
        List<OrderRefund> refundList = orderRefundLogService.getRefundProcess();
        // TODO 待完成
    }

}
