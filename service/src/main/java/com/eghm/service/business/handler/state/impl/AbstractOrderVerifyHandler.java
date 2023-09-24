package com.eghm.service.business.handler.state.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.OrderState;
import com.eghm.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.VerifyLog;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.VerifyLogService;
import com.eghm.service.business.handler.context.OrderVerifyContext;
import com.eghm.service.business.handler.state.OrderVerifyHandler;
import com.eghm.service.common.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单核销
 * @author wyb
 * @since 2023/5/25
 */
@Slf4j
@AllArgsConstructor
public abstract class AbstractOrderVerifyHandler implements OrderVerifyHandler {

    private final OrderVisitorService orderVisitorService;

    private final OrderService orderService;

    private final VerifyLogService verifyLogService;

    private final JsonService jsonService;

    @Override
    public void doAction(OrderVerifyContext context) {
        Order order = orderService.getByOrderNo(context.getOrderNo());
        this.before(context, order);
        this.doProcess(context, order);
        this.end(context, order);
    }

    /**
     * 核销前置处理
     * @param context 待核销的订单或游客信息
     * @param order 订单信息
     */
    protected void before(OrderVerifyContext context, Order order) {
        log.info("开始核销订单[{}] [{}]", this.getStateMachineType(), jsonService.toJson(context));
        // 登陆人或订单的merchantId都可能为空,因此需要交叉判断(为空时表示自营商铺或者自营商铺的核销员)
        boolean match = (context.getMerchantId() != null && !context.getMerchantId().equals(order.getMerchantId())) || (order.getMerchantId() != null && !order.getMerchantId().equals(context.getMerchantId()));
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
     * @param context 待核销的订单或游客信息
     * @param order 订单信息
     */
    protected void doProcess(OrderVerifyContext context, Order order) {
        long verifyId = IdWorker.getId();

        int visited = orderVisitorService.visitorVerify(order.getOrderNo(), context.getVisitorList(), verifyId);

        // 为空则表示根据订单号核销全部未核销的订单 如果待核销的数量为0也表示全部核销
        if (CollUtil.isEmpty(context.getVisitorList()) || orderVisitorService.getUnVerify(context.getOrderNo()) <= 0) {
            order.setState(OrderState.APPRAISE);
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
     * @param context 待核销的订单或游客信息
     * @param order 订单信息
     */
    protected void end(OrderVerifyContext context, Order order) {
    }

}
