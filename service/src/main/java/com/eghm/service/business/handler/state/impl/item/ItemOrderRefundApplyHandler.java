package com.eghm.service.business.handler.state.impl.item;

import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.RefundAudit;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.*;
import com.eghm.exception.BusinessException;
import com.eghm.model.ItemOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.business.*;
import com.eghm.service.business.handler.access.impl.ItemAccessHandler;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.business.handler.context.RefundNotifyContext;
import com.eghm.service.business.handler.state.impl.AbstractOrderRefundApplyHandler;
import com.eghm.service.sys.DingTalkService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import com.eghm.utils.SpringContextUtil;
import com.eghm.utils.TransactionUtil;
import com.eghm.vo.business.order.item.ItemOrderRefundVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.eghm.enums.ErrorCode.*;
import static com.eghm.enums.ref.OrderState.PARTIAL_DELIVERY;

/**
 * @author 二哥很猛
 * @since 2022/9/9
 */
@Service("itemOrderRefundApplyHandler")
@Slf4j
public class ItemOrderRefundApplyHandler extends AbstractOrderRefundApplyHandler {

    private final ItemOrderService itemOrderService;

    private final OrderService orderService;

    private final OrderRefundLogService orderRefundLogService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final OrderMQService orderMQService;

    private final SysConfigApi sysConfigApi;

    private final DingTalkService dingTalkService;

    public ItemOrderRefundApplyHandler(OrderService orderService, OrderRefundLogService orderRefundLogService, OrderVisitorService orderVisitorService, ItemOrderService itemOrderService,
                                       ItemGroupOrderService itemGroupOrderService, OrderMQService orderMQService, SysConfigApi sysConfigApi, DingTalkService dingTalkService) {
        super(orderService, orderRefundLogService, orderVisitorService, dingTalkService);
        this.itemOrderService = itemOrderService;
        this.orderService = orderService;
        this.orderRefundLogService = orderRefundLogService;
        this.itemGroupOrderService = itemGroupOrderService;
        this.orderMQService = orderMQService;
        this.sysConfigApi = sysConfigApi;
        this.dingTalkService = dingTalkService;
    }

    @Override
    protected void before(RefundApplyContext context, Order order) {
        super.before(context, order);
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
        if ((itemOrder.getDeliveryState() == DeliveryState.WAIT_TAKE || itemOrder.getDeliveryState() == DeliveryState.CONFIRM_TASK) && context.getApplyType() == 2) {
            throw new BusinessException(REFUND_DELIVERY);
        }
        ItemOrderRefundVO refund = orderService.getItemRefund(context.getItemOrderId(), context.getMemberId(), false);
        int totalAmount = refund.getRefundAmount() + refund.getExpressFeeAmount();
        if (totalAmount != context.getRefundAmount()) {
            throw new BusinessException(REFUND_AMOUNT_MAX.getCode(), String.format(REFUND_AMOUNT_MAX.getMsg(), DecimalUtil.centToYuan(totalAmount)));
        }
        context.setExpressFee(refund.getExpressFeeAmount());
        context.setScoreAmount(refund.getScoreAmount());
        context.setItemOrder(itemOrder);
    }

    @Override
    protected OrderRefundLog doProcess(RefundApplyContext context, Order order) {
        ItemOrder itemOrder = context.getItemOrder();
        itemOrder.setRefundState(ItemRefundState.REFUND);

        OrderRefundLog refundLog = DataUtil.copy(context, OrderRefundLog.class);
        refundLog.setExpressFee(context.getExpressFee());
        refundLog.setItemOrderId(itemOrder.getId());
        refundLog.setRefundAmount(context.getRefundAmount());
        refundLog.setMerchantId(order.getMerchantId());
        refundLog.setApplyTime(LocalDateTime.now());
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

        refundLog.setAuditState(AuditState.PASS);
        refundLog.setAuditRemark("系统自动审核");
        refundLog.setRefundNo(order.getProductType().generateTradeNo());
        orderRefundLogService.insert(refundLog);

        order.setRefundState(RefundState.PROGRESS);
        order.setRefundAmount(order.getRefundAmount() + context.getRefundAmount());
        orderService.updateById(order);

        itemOrderService.updateById(itemOrder);
        // 尝试发起退款(注意零元购要特殊处理)
        this.tryRefund(refundLog, order);
        return refundLog;
    }

    @Override
    protected void checkRefund(RefundApplyContext context, Order order) {
        super.checkRefundState(context, order);
        this.checkOrderState(context, order);
        int refundNum = orderRefundLogService.getTotalRefundNum(context.getOrderNo(), context.getItemOrderId());
        if (refundNum > 0) {
            throw new BusinessException(ErrorCode.ITEM_REFUND);
        }
    }

    @Override
    protected void after(RefundApplyContext context, Order order, OrderRefundLog refundLog) {
        log.info("零售商品订单退款申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
        if (this.getRefundType(order) == RefundType.DIRECT_REFUND) {
            itemGroupOrderService.refundGroupOrder(order);
        } else {
            RefundAudit audit = new RefundAudit();
            audit.setRefundId(refundLog.getId());
            audit.setOrderNo(context.getOrderNo());
            audit.setRefundAmount(refundLog.getRefundAmount());
            if (refundLog.getApplyType() == 1) {
                log.info("零售退款(仅退款)申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
                orderMQService.sendRefundAuditMessage(ExchangeQueue.ITEM_REFUND_CONFIRM, audit);
            } else {
                log.info("零售退款(退货退款)申请成功 [{}] [{}] [{}]", context.getOrderNo(), context.getItemOrderId(), refundLog);
                orderMQService.sendReturnRefundAuditMessage(ExchangeQueue.ITEM_REFUND_CONFIRM, audit);
            }
        }
    }

    /**
     * 尝试发起退款, 如果是零元付,则不真实发起支付,而是模拟退款成功
     *
     * @param refundLog 支付流水记录
     * @param order 订单编号
     */
    protected void tryRefund(OrderRefundLog refundLog, Order order) {
        if (refundLog.getRefundAmount() > 0) {
            TransactionUtil.afterCommit(() -> orderService.startRefund(refundLog, order), throwable -> {
                dingTalkService.sendMsg(String.format("订单发起退款异常 %s", order.getOrderNo()));
            });
        } else {
            log.error("退款金额为0, 直接模拟退款成功 [{}] [{}]", refundLog.getRefundNo(), refundLog.getRefundAmount());
            RefundNotifyContext context = new RefundNotifyContext();
            context.setTradeNo(order.getTradeNo());
            context.setRefundNo(refundLog.getRefundNo());
            context.setAmount(0);
            context.setSuccessTime(LocalDateTime.now());
            SpringContextUtil.getBean(ItemAccessHandler.class).refundSuccess(context);
        }
    }

    @Override
    protected void checkOrderState(RefundApplyContext context, Order order) {
        if (order.getState() != OrderState.UN_USED && order.getState() != OrderState.WAIT_TAKE &&
                order.getState() != OrderState.WAIT_DELIVERY && order.getState() != PARTIAL_DELIVERY &&
                order.getState() != OrderState.WAIT_RECEIVE && order.getState() != OrderState.COMPLETE) {
            log.error("订单状态不是待使用或待发货, 无法退款 [{}] [{}]", context.getOrderNo(), order.getState());
            throw new BusinessException(ErrorCode.STATE_NOT_REFUND);
        }
        this.checkAfterSaleTime(context, order);
    }

    /**
     * 检查订单是否超过售后时间
     *
     * @param context 上下文
     * @param order 订单
     */
    protected void checkAfterSaleTime(RefundApplyContext context, Order order) {
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
