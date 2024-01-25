package com.eghm.service.business.handler.state.impl.item;

import com.eghm.enums.ErrorCode;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.*;
import com.eghm.exception.BusinessException;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.OrderRefundLogService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;
import static com.eghm.enums.ErrorCode.REFUND_DELIVERY;

/**
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemApplyRefundHandler")
@Slf4j
public class ItemOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    public ItemOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService) {
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
        // 如果是未发货,则退款金额=商品金额+快递费, 如果是已发货则退款金额=商品金额
        int expressFee = 0;
        if (itemOrder.getDeliveryState() == DeliveryState.WAIT_TAKE || itemOrder.getDeliveryState() == DeliveryState.CONFIRM_TASK) {
            if (context.getApplyType() == 1) {
                throw new BusinessException(REFUND_DELIVERY);
            }
        } else {
            expressFee = itemOrder.getExpressFee();
        }
        int totalAmount = order.getPrice() * context.getNum();
        if ((totalAmount + expressFee) < context.getApplyAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX.getCode(), String.format(REFUND_AMOUNT_MAX.getMsg(), DecimalUtil.centToYuan(context.getApplyAmount())));
        }

        itemOrder.setRefundState(ItemRefundState.REFUND);

        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setExpressFee(expressFee);
        refundLog.setItemOrderId(itemOrder.getId());
        refundLog.setMerchantId(order.getMerchantId());
        LocalDateTime now = LocalDateTime.now();
        refundLog.setApplyTime(now);
        refundLog.setState(0);
        refundLog.setAuditState(AuditState.APPLY);
        order.setRefundState(RefundState.APPLY);
        order.setRefundAmount(order.getRefundAmount() + context.getApplyAmount());
        // 更新订单信息
        orderService.updateById(order);
        // 新增退款记录
        orderRefundLogService.insert(refundLog);
        // 更新商品订单
        itemOrderService.updateById(itemOrder);

        return refundLog;
    }

    @Override
    protected void checkRefund(RefundApplyContext context, Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), context.getItemOrderId());
        if (refundNum > 0) {
            throw new BusinessException(ErrorCode.ITEM_REFUND);
        }
    }

    @Override
    protected void after(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("零售商品订单退款申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog.getId());
    }

    @Override
    public IEvent getEvent() {
        return ItemEvent.REFUND_APPLY;
    }

    @Override
    public ProductType getStateMachineType() {
        return ProductType.ITEM;
    }
}
