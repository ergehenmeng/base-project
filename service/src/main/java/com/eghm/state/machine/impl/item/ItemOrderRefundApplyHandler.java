package com.eghm.state.machine.impl.item;

import com.eghm.common.OrderMqService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.RefundAudit;
import com.eghm.enums.*;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.exception.BusinessException;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.mq.service.MessageService;
import com.eghm.service.business.*;
import com.eghm.state.machine.access.AbstractAccessHandler;
import com.eghm.state.machine.access.impl.ItemAccessHandler;
import com.eghm.state.machine.context.ItemRefundApplyContext;
import com.eghm.state.machine.context.RefundApplyContext;
import com.eghm.state.machine.impl.AbstractOrderRefundApplyHandler;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.utils.SpringContextUtil;
import com.eghm.vo.business.order.item.ItemOrderRefundVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.REFUND_AMOUNT_MAX;
import static com.eghm.enums.ErrorCode.REFUND_DELIVERY;
import static com.eghm.enums.OrderState.PARTIAL_DELIVERY;

/**
 * 零售退款申请
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemOrderRefundApplyHandler")
@Slf4j
public class ItemOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler<ItemRefundApplyContext> {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final OrderMqService orderMqService;

    private final SysConfigApi sysConfigApi;

    private final MessageService messageService;

    public ItemOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService,
                                       ItemGroupOrderService itemGroupOrderService, OrderMqService orderMqService, SysConfigApi sysConfigApi, MessageService messageService) {
        super(orderService, orderRefundLogService, orderVisitorService);
        this.itemOrderService = itemOrderService;
        this.orderService = orderService;
        this.orderRefundLogService = orderRefundLogService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.orderMqService = orderMqService;
        this.sysConfigApi = sysConfigApi;
        this.messageService = messageService;
    }

    @Override
    protected void before(ItemRefundApplyContext context, Order order) {
        super.before(context, order);
        this.checkAndSetPayload(context);
    }

    @Override
    protected OrderRefundLog doProcess(ItemRefundApplyContext context, Order order) {
        ItemOrder itemOrder = context.getItemOrder();
        itemOrder.setRefundState(ItemRefundState.REFUND);
        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setExpressFee(context.getExpressFee());
        refundLog.setItemOrderId(itemOrder.getId());
        refundLog.setRefundAmount(context.getRefundAmount());
        refundLog.setMerchantId(order.getMerchantId());
        refundLog.setApplyTime(LocalDateTime.now());
        refundLog.setScoreAmount(context.getScoreAmount());
        // 零售商品退款时默认是一个商品全部退, 不存在买了2件退1件的情况
        refundLog.setNum(itemOrder.getNum());
        refundLog.setState(0);
        // 退款金额+手续费
        // 正常情况下,零售只支持退款审核,默认48小时后自动退款, 但是特殊情况下,零售支持直接退款(拼团失败的订单,这些由平台主动发起的退款不需要审核)
        if (this.getRefundType(order) == RefundType.AUDIT_REFUND) {
            refundLog.setAuditState(AuditState.APPLY);
            orderRefundLogService.insert(refundLog);
            order.setRefundState(RefundState.APPLY);
            orderService.updateById(order);
            itemOrderService.updateById(itemOrder);
            return refundLog;
        }
        refundLog.setAuditTime(LocalDateTime.now());
        refundLog.setAuditState(AuditState.PASS);
        refundLog.setAuditRemark("系统自动审核");
        refundLog.setRefundNo(order.getProductType().generateTradeNo());
        orderRefundLogService.insert(refundLog);
        order.setRefundState(RefundState.PROGRESS);
        // 审核通过后会将已退款金额和已退积分累加到订单上
        order.setRefundAmount(order.getRefundAmount() + context.getRefundAmount());
        order.setRefundScoreAmount(order.getRefundScoreAmount() + context.getScoreAmount());
        orderService.updateById(order);
        itemOrderService.updateById(itemOrder);
        // 尝试发起退款(注意零元购要特殊处理)
        super.tryStartRefund(refundLog, order);
        return refundLog;
    }

    @Override
    protected void end(ItemRefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("零售商品订单退款申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
        if (this.getRefundType(order) == RefundType.DIRECT_REFUND) {
            itemGroupOrderService.refundGroupOrder(order);
        } else {
            RefundAudit audit = new RefundAudit();
            audit.setRefundId(refundLog.getId());
            audit.setOrderNo(context.getOrderNo());
            audit.setMerchantId(order.getMerchantId());
            audit.setRefundAmount(refundLog.getRefundAmount());
            if (refundLog.getApplyType() == 1) {
                log.info("零售退款(仅退款)申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
                orderMqService.sendRefundAuditMessage(ExchangeQueue.ITEM_REFUND_CONFIRM, audit);
            } else {
                log.info("零售退款(退货退款)申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
                orderMqService.sendReturnRefundAuditMessage(ExchangeQueue.ITEM_REFUND_CONFIRM, audit);
            }
            messageService.send(ExchangeQueue.ORDER_REFUND_AUDIT, audit);
        }
    }

    @Override
    protected void orderStateCheck(ItemRefundApplyContext context, Order order) {
        if (order.getState() != OrderState.UN_USED && order.getState() != OrderState.WAIT_TAKE &&
                order.getState() != OrderState.WAIT_DELIVERY && order.getState() != PARTIAL_DELIVERY &&
                order.getState() != OrderState.WAIT_RECEIVE && order.getState() != OrderState.COMPLETE) {
            log.error("订单状态不是待使用或待发货, 无法退款 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.STATE_NOT_REFUND);
        }
        this.afterSaleTimeExpireCheck(context, order);
    }

    @Override
    protected void refundNumCheck(ItemRefundApplyContext context, Order order) {
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), context.getItemOrderId());
        if (refundNum > 0) {
            throw new BusinessException(ErrorCode.ITEM_REFUND);
        }
    }

    @Override
    protected AbstractAccessHandler getAccessHandler() {
        return SpringContextUtil.getBean(ItemAccessHandler.class);
    }

    /**
     * 申请退款订单基础校验通过后, 进行零售退款专项校验,
     * 同时将查询订单订单等关信息设置到context中减少后续查询
     *
     * @param context 上下文
     */
    private void checkAndSetPayload(ItemRefundApplyContext context) {
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
        boolean isDelivery = (itemOrder.getDeliveryState() == DeliveryState.WAIT_TAKE || itemOrder.getDeliveryState() == DeliveryState.CONFIRM_TASK) && context.getApplyType() == 2;
        if (isDelivery) {
            throw new BusinessException(REFUND_DELIVERY);
        }
        ItemOrderRefundVO refund = orderService.getItemRefund(context.getItemOrderId(), context.getMemberId(), false);
        int totalAmount = refund.getRefundAmount() + refund.getExpressFeeAmount();
        if (totalAmount != context.getRefundAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX, DecimalUtil.centToYuan(totalAmount));
        }
        context.setExpressFee(refund.getExpressFeeAmount());
        context.setScoreAmount(refund.getScoreAmount());
        context.setItemOrder(itemOrder);
    }

    /**
     * 检查订单是否超过售后时间
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void afterSaleTimeExpireCheck(RefundApplyContext context, Order order) {
        if (order.getState() == OrderState.COMPLETE) {
            int anInt = sysConfigApi.getInt(ConfigConstant.SUPPORT_AFTER_SALE_TIME, 604800);
            LocalDateTime refundExpireTime = order.getCompleteTime().plusSeconds(anInt);
            if (refundExpireTime.isAfter(LocalDateTime.now())) {
                log.error("订单已超过售后时间, 无法退款 [{}] [{}]", context.getOrderNo(), refundExpireTime);
                throw new BusinessException(ErrorCode.ITEM_REFUND_EXPIRE);
            }
        }
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
