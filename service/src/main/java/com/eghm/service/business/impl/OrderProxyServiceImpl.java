package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.AlarmService;
import com.eghm.common.SmsService;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SmsType;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.event.impl.ItemEvent;
import com.eghm.enums.ref.ConfirmState;
import com.eghm.enums.ref.ItemRefundState;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.HomestayOrderMapper;
import com.eghm.model.*;
import com.eghm.service.business.*;
import com.eghm.state.machine.StateHandler;
import com.eghm.state.machine.access.AccessHandler;
import com.eghm.state.machine.context.ItemRefundApplyContext;
import com.eghm.state.machine.context.OrderCancelContext;
import com.eghm.state.machine.context.RefundApplyContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 该类为了解决构造方法注入循环依赖
 *
 * @author 二哥很猛
 * @since 2024/1/11
 */
@Slf4j
@Service("orderProxyService")
@AllArgsConstructor
public class OrderProxyServiceImpl implements OrderProxyService {

    private final SmsService smsService;

    private final StateHandler stateHandler;

    private final OrderService orderService;

    private final AlarmService alarmService;

    private final CommonService commonService;

    private final ItemOrderService itemOrderService;

    private final HomestayOrderMapper homestayOrderMapper;

    private final OrderVisitorService orderVisitorService;

    private HomestayOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<HomestayOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayOrderMapper.selectOne(wrapper);
    }

    @Override
    public void confirm(HomestayOrderConfirmRequest request) {
        HomestayOrder homestayOrder = this.getByOrderNo(request.getOrderNo());
        if (homestayOrder.getConfirmState() != ConfirmState.WAIT_CONFIRM) {
            log.warn("该民宿订单已确认 [{}] [{}]", request.getOrderNo(), homestayOrder.getConfirmState());
            throw new BusinessException(ErrorCode.ORDER_CONFIRM);
        }
        homestayOrder.setConfirmState(request.getConfirmState());
        homestayOrder.setRemark(request.getRemark());
        homestayOrderMapper.updateById(homestayOrder);

        if (request.getConfirmState() == ConfirmState.FAIL_CONFIRM) {
            log.info("订单:[{}],确认无房开始执行退款逻辑", request.getOrderNo());
            Order order = orderService.getByOrderNo(request.getOrderNo());
            List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(request.getOrderNo());
            List<Long> visitorIds = visitorList.stream().map(OrderVisitor::getId).collect(Collectors.toList());
            RefundApplyContext context = new RefundApplyContext();
            context.setMemberId(order.getMemberId());
            context.setNum(visitorIds.size());
            context.setVisitorIds(visitorIds);
            context.setApplyType(1);
            context.setReason(request.getRemark());
            context.setRefundAmount(order.getPayAmount());
            context.setOrderNo(request.getOrderNo());
            stateHandler.fireEvent(ProductType.HOMESTAY, order.getState().getValue(), HomestayEvent.CONFIRM_ROOM, context);
            // 发送短信通知
            smsService.sendSms(SmsType.CONFIRM_NO_ROOM, order.getMobile(), request.getOrderNo());
        }
    }


    @Override
    public void refund(String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
        if (order.getState() != OrderState.UN_USED && order.getState() != OrderState.WAIT_TAKE && order.getState() != OrderState.WAIT_DELIVERY) {
            log.warn("订单状态不匹配,无法退款 [{}] [{}]", orderNo, order.getState());
            throw new BusinessException(ErrorCode.REFUND_STATE);
        }
        List<ItemOrder> itemList = itemOrderService.getByOrderNo(orderNo);
        for (ItemOrder itemOrder : itemList) {
            if (itemOrder.getRefundState() == ItemRefundState.REFUND) {
                log.warn("该商品已退款,无需重复退款 [{}] [{}]", orderNo, itemOrder.getId());
                continue;
            }
            ItemRefundApplyContext context = new ItemRefundApplyContext();
            context.setMemberId(itemOrder.getMemberId());
            context.setNum(itemOrder.getNum());
            context.setItemOrderId(itemOrder.getId());
            context.setRefundAmount(itemOrder.getSalePrice() * itemOrder.getNum() + itemOrder.getExpressFee());
            context.setApplyType(1);
            context.setReason("系统退款");
            context.setOrderNo(orderNo);
            stateHandler.fireEvent(ProductType.prefix(orderNo), order.getState().getValue(), ItemEvent.PLATFORM_REFUND, context);
        }
    }

    @Override
    public void cancel(String orderNo, Long memberId) {
        Order order = orderService.getByOrderNo(orderNo);
        if (!order.getMemberId().equals(memberId)) {
            log.error("订单取消,操作了不属于自己的订单 [{}] [{}]", orderNo, memberId);
            throw new BusinessException(ErrorCode.ILLEGAL_OPERATION);
        }
        if (order.getState() == OrderState.CLOSE) {
            log.error("订单已取消,无需重复操作 [{}] [{}]", orderNo, memberId);
            return;
        }
        if (order.getState() != OrderState.UN_PAY) {
            log.info("订单已支付, 无法取消 [{}] [{}] [{}]", orderNo, memberId, order.getState());
            throw new BusinessException(ErrorCode.ORDER_PAID_CANCEL);
        }
        OrderCancelContext context = new OrderCancelContext();
        context.setOrderNo(orderNo);
        commonService.getHandler(orderNo, AccessHandler.class).cancel(context);
    }

    @Override
    public void doCancelGroupOrder(ItemGroupOrder group) {
        log.info("开始取消拼团订单 [{}]", group.getOrderNo());
        Order order = orderService.getByOrderNo(group.getOrderNo());
        try {
            if (order.getState() == OrderState.UN_PAY) {
                log.info("拼团订单未支付自动取消 [{}]", group.getOrderNo());
                OrderCancelContext context = new OrderCancelContext();
                context.setOrderNo(group.getOrderNo());
                stateHandler.fireEvent(ProductType.prefix(group.getOrderNo()), order.getState().getValue(), ItemEvent.AUTO_CANCEL, context);
            } else if (order.getState() == OrderState.UN_USED || order.getState() == OrderState.WAIT_TAKE || order.getState() == OrderState.WAIT_DELIVERY) {
                log.info("拼团订单已支付自动取消 [{}]", group.getOrderNo());
                this.refund(group.getOrderNo());
            } else {
                log.info("拼团订单不合法 [{}] [{}] [{}]", group.getBookingNo(), group, order.getState());
            }
        } catch (Exception e) {
            log.error("拼团订单取消异常 [{}] [{}]", group.getOrderNo(), group.getBookingNo(), e);
            alarmService.sendMsg(String.format("拼团订单取消异常 [%s] [%s]", group.getOrderNo(), group.getBookingNo()));
        }
    }
}
