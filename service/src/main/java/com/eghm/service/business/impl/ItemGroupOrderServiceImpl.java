package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.ItemGroupOrderMapper;
import com.eghm.model.ItemGroupOrder;
import com.eghm.model.Order;
import com.eghm.service.business.ItemGroupOrderService;
import com.eghm.service.business.handler.context.ItemOrderCreateContext;
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
        groupOrder.setItemId(itemId);
        groupOrder.setBookingId(context.getBookingId());
        groupOrder.setState(0);
        itemGroupOrderMapper.insert(groupOrder);
    }

    @Override
    public void updateState(String bookingNo, Integer state) {
        LambdaUpdateWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getState, state);
        itemGroupOrderMapper.update(null, wrapper);
    }

    @Override
    public void delete(String bookingNo, String orderNo) {
        LambdaUpdateWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getOrderNo, orderNo);
        itemGroupOrderMapper.delete(wrapper);
    }

    @Override
    public List<ItemGroupOrder> getGroupList(String bookingNo, Integer state) {
        LambdaQueryWrapper<ItemGroupOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemGroupOrder::getBookingNo, bookingNo);
        wrapper.eq(ItemGroupOrder::getState, state);
        return itemGroupOrderMapper.selectList(wrapper);
    }

    @Override
    public GroupOrderVO getGroupOrder(String bookingNo, Integer state) {
        List<ItemGroupOrder> groupList = this.getGroupList(bookingNo, state);
        GroupOrderVO vo = new GroupOrderVO();
        vo.setNum(groupList.size());
        if (groupList.size() > 0) {
            vo.setBookingId(groupList.get(0).getBookingId());
        }
        return vo;
    }
}
