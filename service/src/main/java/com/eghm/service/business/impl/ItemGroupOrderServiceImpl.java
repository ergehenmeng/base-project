package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ExchangeQueue;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemGroupOrderMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.mapper.OrderMapper;
import com.eghm.model.GroupBooking;
import com.eghm.model.Item;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.Order;
import com.eghm.service.business.GroupBookingService;
import com.eghm.service.business.ItemGroupOrderService;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
import com.eghm.mq.service.MessageService;
import com.eghm.common.AlarmService;
import com.eghm.vo.business.group.GroupMemberVO;
import com.eghm.vo.business.group.GroupOrderDetailVO;
import com.eghm.vo.business.group.GroupOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 拼团订单表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-24
 */
@Slf4j
@AllArgsConstructor
@Service("itemGroupOrderService")
public class ItemGroupOrderServiceImpl implements ItemGroupOrderService {

    private final ItemMapper itemMapper;

    private final OrderMapper orderMapper;

    private final AlarmService alarmService;

    private final MessageService messageService;

    private final GroupBookingService groupBookingService;

    private final ItemGroupOrderMapper itemGroupOrderMapper;

    @Override
    public void save(ItemOrderCreateContext context, Order order, Long itemId) {
        if (Boolean.FALSE.equals(context.getGroupBooking())) {
            log.info("非拼团订单,不做任何处理 [{}]", order.getOrderNo());
            return;
        }
        ItemGroupOrder groupOrder = new ItemGroupOrder();
        groupOrder.setOrderNo(order.getOrderNo());
        groupOrder.setBookingNo(context.getBookingNo());
        groupOrder.setMemberId(order.getMemberId());
        groupOrder.setStarter(context.getStarter());
        groupOrder.setItemId(itemId);
        groupOrder.setBookingId(context.getBookingId());
        groupOrder.setState(0);
        itemGroupOrderMapper.insert(groupOrder);
        // 团长发起的订单,需要发送消息方便取消订单
        if (Boolean.TRUE.equals(context.getStarter())) {
            messageService.sendDelay(ExchangeQueue.GROUP_ORDER_EXPIRE_SINGLE, context.getBookingNo(), context.getExpireTime());
        }
    }

    @Override
    public void updateState(String bookingNo, Integer state) {
        LambdaUpdateWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.set(ItemGroupOrder::getState, state);
        itemGroupOrderMapper.update(null, wrapper);
    }

    @Override
    public void updateState(String bookingNo, String orderNo, Integer state) {
        LambdaUpdateWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getOrderNo, orderNo);
        wrapper.set(ItemGroupOrder::getState, state);
        itemGroupOrderMapper.update(null, wrapper);
    }

    @Override
    public void delete(String bookingNo, String orderNo) {
        log.info("订单取消, 删除拼团订单 [{}] [{}]", bookingNo, orderNo);
        LambdaUpdateWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getOrderNo, orderNo);
        itemGroupOrderMapper.delete(wrapper);
    }

    @Override
    public ItemGroupOrder getGroupOrder(String bookingNo, String orderNo) {
        LambdaQueryWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return itemGroupOrderMapper.selectOne(wrapper);
    }

    @Override
    public List<ItemGroupOrder> getGroupList(String bookingNo, Integer state) {
        LambdaQueryWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getState, state);
        return itemGroupOrderMapper.selectList(wrapper);
    }

    @Override
    public List<ItemGroupOrder> getGroupList(Long bookingId, Integer state) {
        LambdaQueryWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemGroupOrder::getBookingId, bookingId);
        wrapper.eq(ItemGroupOrder::getState, state);
        return itemGroupOrderMapper.selectList(wrapper);
    }

    @Override
    public GroupOrderVO getGroupOrder(String bookingNo, Integer state) {
        List<ItemGroupOrder> groupList = this.getGroupList(bookingNo, state);
        GroupOrderVO vo = new GroupOrderVO();
        vo.setNum(groupList.size());
        if (!groupList.isEmpty()) {
            vo.setBookingId(groupList.get(0).getBookingId());
        }
        return vo;
    }

    @Override
    public GroupOrderDetailVO getGroupDetail(String bookingNo) {
        List<GroupMemberVO> memberList = itemGroupOrderMapper.getMemberList(bookingNo);
        if (memberList.isEmpty()) {
            log.error("拼团订单不存在 [{}]", bookingNo);
            throw new BusinessException(ErrorCode.GROUP_ORDER_NULL);
        }
        Long bookingId = memberList.get(0).getBookingId();
        GroupBooking booking = groupBookingService.getValidById(bookingId);
        Item item = itemMapper.selectById(booking.getItemId());
        if (item == null) {
            log.error("拼团商品不存在 [{}]", booking.getItemId());
            throw new BusinessException(ErrorCode.ITEM_DOWN);
        }
        GroupOrderDetailVO vo = new GroupOrderDetailVO();
        vo.setMemberList(memberList);
        vo.setBookingNum(booking.getNum());
        vo.setItemId(booking.getItemId());
        vo.setItemName(item.getTitle());
        vo.setItemCoverUrl(item.getCoverUrl());
        return vo;
    }

    @Override
    public void refundGroupOrder(Order order) {
        if (order.getBookingNo() == null) {
            log.info("该订单非拼团订单不做额外处理 [{}]", order.getOrderNo());
            return;
        }
        if (order.getBookingState() == 1) {
            log.warn("订单已拼团成功,无需同步退款[{}] [{}]", order.getOrderNo(), order.getBookingNo());
            return;
        }
        if (order.getBookingState() == 2) {
            log.warn("订单已拼团失败,无需同步退款[{}] [{}]", order.getOrderNo(), order.getBookingNo());
            return;
        }
        ItemGroupOrder groupOrder = this.getGroupOrder(order.getBookingNo(), order.getOrderNo());

        if (groupOrder == null) {
            log.error("订单无拼团记录,无法同步退款[{}] [{}]", order.getOrderNo(), order.getBookingNo());
            alarmService.sendMsg(String.format("订单[%s]无拼团记录,无法同步退款 [%s]", order.getOrderNo(), order.getBookingNo()));
            return;
        }
        if (groupOrder.getState() != 0) {
            log.error("订单拼单状态异常,无法同步退款[{}] [{}]", order.getOrderNo(), order.getBookingNo());
            alarmService.sendMsg(String.format("订单[%s]拼单状态异常,无法同步退款 [%s]", order.getOrderNo(), order.getBookingNo()));
            return;
        }
        // 先把自己的拼团订单改为失败, 后续在根据是否是团长来决定是否要退款
        this.updateState(order.getBookingNo(), order.getOrderNo(), 2);
        orderMapper.updateBookingState(order.getBookingNo(), order.getOrderNo(), 2);

        if (Boolean.TRUE.equals(groupOrder.getStarter())) {
            // 延迟队列(因为是团长自己退款,相当于解散拼团了, 因此需要取消拼团订单团员的所有订单), 延迟5秒防止当前事务没有提交, 该消息就已经被消费导致重复退款报错
            log.info("订单为拼团发起者,开始同步退款 [{}]: [{}]", order.getOrderNo(), order.getBookingNo());
            messageService.sendDelay(ExchangeQueue.GROUP_ORDER_EXPIRE_SINGLE, order.getBookingNo(), 5);
        } else {
            log.info("订单为拼团团员自身的退款[{}]: [{}]", order.getOrderNo(), order.getBookingNo());
        }
    }
}
