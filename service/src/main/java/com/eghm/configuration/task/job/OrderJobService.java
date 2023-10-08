package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.CronMark;
import com.eghm.dto.ext.OrderRefund;
import com.eghm.model.Order;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.handler.access.AccessHandler;
import com.eghm.service.business.handler.context.PayNotifyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/8
 */
@Slf4j
@Component("orderJobService")
@AllArgsConstructor
public class OrderJobService {

    private final OrderService orderService;

    private final CommonService commonService;

    private final OrderRefundLogService orderRefundLogService;

    /**
     * 定时任务处理: 支付中的订单
     */
    @CronMark
    public void payProcess(String param) {
        LoggerUtil.print("订单支付中定时任务开始执行");
        List<Order> processList = orderService.getProcessList();
        for (Order order : processList) {
            PayNotifyContext context = new PayNotifyContext();
            context.setOrderNo(order.getOrderNo());
            context.setOutTradeNo(order.getOutTradeNo());
            try {
                commonService.getHandler(order.getOrderNo(), AccessHandler.class).payNotify(context);
            } catch (Exception e) {
                log.error("支付中的订单处理异常 [{}]", order.getOrderNo(), e);
            }
        }
        LoggerUtil.print("订单支付中定时任务执行完毕");
    }

    /**
     * 门票退款定时任务
     */
    @CronMark
    public void refundProcess(String param) {
        LoggerUtil.print("订单退款中定时任务开始执行");
        List<OrderRefund> refundList = orderRefundLogService.getRefundProcess();
        for (OrderRefund refund : refundList) {
            RefundNotifyContext context = new RefundNotifyContext();
            context.setOutRefundNo(refund.getOutRefundNo());
            context.setOutTradeNo(refund.getOutTradeNo());
            try {
                commonService.getHandler(refund.getOutRefundNo(), AccessHandler.class).refundNotify(context);
            } catch (Exception e) {
                log.error("退款中的订单处理异常 [{}]", refund.getOutTradeNo(), e);
            }
        }
        LoggerUtil.print("订单退款中定时任务执行完毕");
    }

}
