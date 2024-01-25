package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
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
import com.eghm.service.business.handler.context.OrderCancelContext;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.common.SmsService;
import com.eghm.state.machine.StateHandler;
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

    private final HomestayOrderMapper homestayOrderMapper;

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    private final StateHandler stateHandler;

    private final SmsService smsService;

    private final ItemGroupOrderService itemGroupOrderService;

    private final ItemOrderService itemOrderService;

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
            context.setApplyAmount(order.getPayAmount());
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
            RefundApplyContext context = new RefundApplyContext();
            context.setMemberId(itemOrder.getMemberId());
            context.setNum(itemOrder.getNum());
            context.setItemOrderId(itemOrder.getId());
            context.setApplyAmount(itemOrder.getSalePrice() * itemOrder.getNum() + itemOrder.getExpressFee());
            context.setApplyType(1);
            context.setReason("系统退款");
            context.setOrderNo(orderNo);
            stateHandler.fireEvent(ProductType.prefix(orderNo), order.getState().getValue(), ItemEvent.PLATFORM_REFUND, context);
        }
    }

    @Override
    public void cancelGroupOrder(String bookingNo) {
        log.info("开始取消拼团订单 [{}]", bookingNo);
        List<ItemGroupOrder> groupList = itemGroupOrderService.getGroupList(bookingNo, 0);
        if (groupList.isEmpty()) {
            log.warn("该拼团订单可能已成团或已取消,不做取消处理 [{}]", bookingNo);
            return;
        }
        for (ItemGroupOrder group : groupList) {
            log.info("开始取消拼团订单 [{}]", group.getOrderNo());
            Order order = orderService.getByOrderNo(group.getOrderNo());
            if (order.getState() == OrderState.UN_PAY) {
                log.info("拼团订单未支付自动取消 [{}]", group.getOrderNo());
                OrderCancelContext context = new OrderCancelContext();
                context.setOrderNo(group.getOrderNo());
                stateHandler.fireEvent(ProductType.prefix(group.getOrderNo()), order.getState().getValue(), ItemEvent.AUTO_CANCEL, context);
            } else if (order.getState() == OrderState.UN_USED || order.getState() == OrderState.WAIT_TAKE || order.getState() == OrderState.WAIT_DELIVERY) {
                log.info("拼团订单已支付自动取消 [{}]", group.getOrderNo());
                this.refund(group.getOrderNo());
            } else {
                log.info("拼团订单不合法 [{}] [{}] [{}]", bookingNo, group, order.getState());
            }
        }
    }
}
