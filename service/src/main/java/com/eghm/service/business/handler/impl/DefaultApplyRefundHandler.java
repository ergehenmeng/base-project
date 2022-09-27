package com.eghm.service.business.handler.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.OrderState;
import com.eghm.common.enums.ref.RefundState;
import com.eghm.common.enums.ref.RefundType;
import com.eghm.common.exception.BusinessException;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.model.dto.business.order.ApplyRefundDTO;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.ApplyRefundHandler;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.eghm.common.enums.ErrorCode.TOTAL_REFUND_MAX;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
@AllArgsConstructor
@Slf4j
@Service("defaultApplyRefundHandler")
public class DefaultApplyRefundHandler implements ApplyRefundHandler {

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final OrderVisitorService orderVisitorService;

    @Override
    public void process(ApplyRefundDTO dto) {
        Order order = orderService.getByOrderNo(dto.getOrderNo());
        this.before(dto, order);

        OrderRefundLog refundLog = this.doProcess(dto, order);

        this.after(dto, order, refundLog);
    }

    /**
     * 执行退款操作
     * 1. 更新订单状态
     * 2. 添加退款记录
     * 3. 如果直接退款,则发起退款申请
     * @param dto 退款信息
     * @param order 订单信息
     * @return 退款记录
     */
    protected OrderRefundLog doProcess(ApplyRefundDTO dto, Order order) {
        OrderRefundLog refundLog = DataUtil.copy(dto, OrderRefundLog.class);
        LocalDateTime now = LocalDateTime.now();
        refundLog.setApplyTime(now);
        refundLog.setState(0);
        if (order.getRefundType() == RefundType.AUDIT_REFUND) {
            refundLog.setAuditState(AuditState.APPLY);
            order.setRefundState(RefundState.APPLY);
        } else {
            this.checkRefund(dto, order);
            refundLog.setAuditState(AuditState.PASS);
            refundLog.setAuditRemark("系统自动审核");
            order.setRefundState(RefundState.PROGRESS);
            orderService.startRefund(refundLog, order);
        }
        orderService.updateById(order);
        orderRefundLogService.insert(refundLog);
        return refundLog;
    }

    /**
     * 退款申请成功的后置处理
     * @param dto 请求参数
     * @param order 订单信息
     * @param refundLog 退款记录
     */
    protected void after(ApplyRefundDTO dto, Order order, OrderRefundLog refundLog) {
        log.info("订单退款申请成功 [{}] [{}] [{}]", dto, order.getState(), order.getRefundState());
        orderVisitorService.lockVisitor(order.getProductType(), dto.getOrderNo(), refundLog.getId(), dto.getVisitorIds());
    }

    /**
     * 校验订单信息是否满足本次退款申请
     * @param dto 订单申请信息
     * @param order 主订单
     */
    protected void before(ApplyRefundDTO dto, Order order) {
        if (!dto.getUserId().equals(order.getUserId())) {
            log.error("订单不属于当前用户,无法退款 [{}] [{}] [{}]", dto.getOrderNo(), order.getUserId(), dto.getUserId());
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (order.getRefundType() == RefundType.NOT_SUPPORTED) {
            log.error("该订单不支持退款 [{}]", dto.getOrderNo());
            throw new BusinessException(ErrorCode.REFUND_NOT_SUPPORTED);
        }
        if (order.getState() != OrderState.UN_USED) {
            log.error("订单状态不是待使用,无法退款 [{}] [{}]", dto.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.STATE_NOT_REFUND);
        }
        if (order.getRefundState() != null && order.getRefundState() != RefundState.REFUSE) {
            log.error("订单退款状态非法 [{}] [{}]", dto.getOrderNo(), order.getRefundState());
            throw new BusinessException(ErrorCode.REFUND_STATE_INVALID);
        }
    }

    /**
     * 直接退款时,需要二次校验是否满足本次退款要求
     * @param dto 订单申请信息
     * @param order 主订单
     */
    protected void checkRefund(ApplyRefundDTO dto, Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(dto.getOrderNo(), null);
        if ((refundNum + dto.getNum()) > order.getNum()) {
            log.error("累计退款金额(含本次)大于总支付金额 [{}] [{}] [{}] [{}]", order.getOrderNo(), order.getNum(), refundNum, dto.getNum());
            throw new BusinessException(TOTAL_REFUND_MAX);
        }
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public OrderRefundLogService getOrderRefundLogService() {
        return orderRefundLogService;
    }
}
