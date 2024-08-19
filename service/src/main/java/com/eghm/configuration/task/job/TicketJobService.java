package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.CronMark;
import com.eghm.service.business.TicketOrderService;
import com.eghm.service.business.handler.access.impl.TicketAccessHandler;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.utils.LoggerUtil;
import com.eghm.vo.business.order.ticket.TicketVerifyVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/9/24
 */
@Slf4j
@AllArgsConstructor
@Component("ticketJobService")
public class TicketJobService {

    private final TicketOrderService ticketOrderService;

    private final TicketAccessHandler ticketAccessHandler;

    /**
     * 门票订单核销
     */
    @CronMark
    public void verifyOrder() {
        LoggerUtil.print("门票自动核销定时任务开始执行");
        List<TicketVerifyVO> orderList = ticketOrderService.getUnVerifyList();
        for (TicketVerifyVO order : orderList) {
            OrderVerifyContext context = new OrderVerifyContext();
            context.setRemark("系统自动核销");
            context.setOrderNo(order.getOrderNo());
            context.setMerchantId(order.getMerchantId());
            context.setUserId(1L);
            try {
                ticketAccessHandler.verifyOrder(context);
            } catch (Exception e) {
                log.error("门票订单核销失败 [{}]", order.getOrderNo(), e);
            }
            log.info("门票订单自动核销 [{}] [{}]", order.getOrderNo(), context.getVerifyNum());
        }
        LoggerUtil.print("门票自动核销定时任务执行完毕");
    }
}
