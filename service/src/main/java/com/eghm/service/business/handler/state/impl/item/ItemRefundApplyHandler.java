package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.AuditState;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.RefundState;
import com.eghm.exception.BusinessException;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.impl.AbstractRefundApplyHandler;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @date 2022/9/9
 */
@Service("itemApplyRefundHandler")
@Slf4j
public class ItemRefundApplyHandler extends AbstractRefundApplyHandler {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    public ItemRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.itemOrderService = itemOrderService;
        this.orderService = orderService;
        this.orderRefundLogService = orderRefundLogService;
    }

    @Override
    protected OrderRefundLog doProcess(RefundApplyContext context, Order order) {
        ItemOrder itemOrder = itemOrderService.selectByIdRequired(context.getItemOrderId());
        if (!itemOrder.getOrderNo().equals(context.getOrderNo())) {
            log.error("订单id与订单编号不匹配,无法申请退款 [{}] [{}]", context.getItemOrderId(), context.getOrderNo());
            throw new BusinessException(ErrorCode.ORDER_NOT_MATCH);
        }
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), context.getItemOrderId());
        if (refundNum >= itemOrder.getNum()) {
            log.error("该商品已全部申请退款, 无须再次申请 [{}] [{}]", context.getOrderNo(), context.getItemOrderId());
            throw new BusinessException(ErrorCode.ORDER_REFUND_APPLY);
        }
        int totalRefund = context.getNum() + refundNum;
        if (totalRefund > itemOrder.getNum()) {
            log.error("商品总退款数量大于下单数量 [{}] [{}] [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), itemOrder.getNum(), context.getNum(), refundNum);
            throw new BusinessException(ErrorCode.REFUND_MUM_MATCH);
        }
        itemOrder.setRefundState(totalRefund == itemOrder.getNum() ? ItemRefundState.REFUND : ItemRefundState.REBATE);

        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setItemOrderId(itemOrder.getId());
        LocalDateTime now = LocalDateTime.now();
        refundLog.setApplyTime(now);
        refundLog.setState(0);
        refundLog.setAuditState(AuditState.APPLY);
        order.setRefundState(RefundState.APPLY);
        // 更新订单信息
        orderService.updateById(order);
        // 新增退款记录
        orderRefundLogService.insert(refundLog);
        // 更新商品订单
        itemOrderService.updateById(itemOrder);

        return refundLog;
    }

    @Override
    protected void after(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("零售商品订单退款申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog.getId());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.REFUND_PASS;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
