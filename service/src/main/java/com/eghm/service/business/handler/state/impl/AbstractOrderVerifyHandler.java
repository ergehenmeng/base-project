package com.eghm.service.business.handler.state.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.common.JsonService;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.VerifyLog;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.state.machine.ActionHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 订单核销
 *
 * @author wyb
 * @since 2023/5/25
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderVerifyHandler implements ActionHandler<OrderVerifyContext> {

    private final OrderVisitorService orderVisitorService;

    private final OrderService orderService;

    private final VerifyLogService verifyLogService;

    private final JsonService jsonService;

    private final CommonService commonService;

    @Override
    public void doAction(OrderVerifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(context, order);
        this.doProcess(context, order);
        this.end(context, order);
    }

    /**
     * 核销前置处理
     *
     * @param context 待核销的订单或游客信息
     * @param order   订单信息
     */
    protected void before(OrderVerifyContext context, Order order) {
        log.info("开始核销订单[{}] [{}]", this.getStateMachineType(), jsonService.toJson(context));
        boolean match = commonService.checkIsIllegal(order.getMerchantId(), context.getMerchantId());
        if (match) {
            log.error("无法核销其他店铺的订单 [{}] [{}]", context.getMerchantId(), order.getMerchantId());
            throw new BusinessException(ErrorCode.ILLEGAL_VERIFY);
        }
        // 如果订单在退款中,则不允许核销
        if (order.getState() == OrderState.REFUND) {
            throw new BusinessException(ErrorCode.ORDER_REFUND_PROCESS);
        }
        if (order.getState() != OrderState.UN_USED) {
            throw new BusinessException(ErrorCode.ORDER_NOT_VERIFY);
        }
    }

    /**
     * 核销订单订单
     *
     * @param context 待核销的订单或游客信息
     * @param order   订单信息
     */
    protected void doProcess(OrderVerifyContext context, Order order) {
        long verifyId = IdWorker.getId();

        int visited = orderVisitorService.visitorVerify(order.getOrderNo(), context.getVisitorList(), verifyId);

        // 为空则表示根据订单号核销全部未核销的订单 如果待核销的数量为0也表示全部核销
        if (CollUtil.isEmpty(context.getVisitorList()) || orderVisitorService.getUnVerify(context.getOrderNo()) <= 0) {
            order.setCompleteTime(LocalDateTime.now());
            order.setState(OrderState.COMPLETE);
        }
        orderService.updateById(order);
        VerifyLog verifyLog = new VerifyLog();
        verifyLog.setId(verifyId);
        verifyLog.setMerchantId(order.getMerchantId());
        verifyLog.setOrderNo(order.getOrderNo());
        verifyLog.setRemark(context.getRemark());
        verifyLog.setUserId(context.getUserId());
        verifyLog.setNum(visited);
        verifyLogService.insert(verifyLog);
        context.setVerifyNum(visited);
    }

    /**
     * 订单后置处理
     *
     * @param context 待核销的订单或游客信息
     * @param order   订单信息
     */
    protected void end(OrderVerifyContext context, Order order) {
    }

    /**
     * 没有游客信息的核销, 默认全部
     *
     * @param context 上下文
     * @param order 订单
     */
    public void noVisitProcess(OrderVerifyContext context, Order order) {
        order.setState(OrderState.COMPLETE);
        order.setCompleteTime(LocalDateTime.now());
        orderService.updateById(order);

        VerifyLog verifyLog = new VerifyLog();
        verifyLog.setMerchantId(order.getMerchantId());
        verifyLog.setOrderNo(order.getOrderNo());
        verifyLog.setRemark(context.getRemark());
        verifyLog.setUserId(context.getUserId());
        verifyLog.setNum(order.getNum());
        verifyLogService.insert(verifyLog);
        context.setVerifyNum(order.getNum());
    }
}
