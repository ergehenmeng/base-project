package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.order.item.OrderExpressRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderExpressMapper;
import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.model.OrderExpress;
import com.eghm.model.ItemOrderExpress;
import com.eghm.service.business.OrderExpressService;
import com.eghm.common.JsonService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.item.ExpressVO;
import com.eghm.vo.business.order.item.FirstExpressVO;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */

@Slf4j
@Service("orderExpressService")
@AllArgsConstructor
public class OrderExpressServiceImpl implements OrderExpressService {

    private final JsonService jsonService;

    private final OrderExpressMapper orderExpressMapper;

    private final ItemOrderExpressMapper itemOrderExpressMapper;

    @Override
    public void insert(ItemSippingRequest request) {
        OrderExpress orderExpress = DataUtil.copy(request, OrderExpress.class);
        orderExpressMapper.insert(orderExpress);
        List<Long> orderIds = request.getOrderIds();
        for (Long orderId : orderIds) {
            ItemOrderExpress express = new ItemOrderExpress();
            express.setExpressId(orderExpress.getId());
            express.setOrderNo(request.getOrderNo());
            express.setItemOrderId(orderId);
            itemOrderExpressMapper.insert(express);
        }
    }

    @Override
    public void update(OrderExpressRequest request) {
        OrderExpress express = orderExpressMapper.selectById(request.getId());
        if (express == null) {
            log.error("物流信息不存在 [{}]", request.getId());
            throw new BusinessException(ErrorCode.ORDER_EXPRESS_NULL);
        }
        LambdaUpdateWrapper<OrderExpress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderExpress::getId, request.getId());
        // 尽量防止更新的订单不是自己商品下的
        wrapper.eq(OrderExpress::getOrderNo, request.getOrderNo());
        wrapper.set(OrderExpress::getExpressNo, request.getExpressNo());
        wrapper.set(OrderExpress::getExpressCode, request.getExpressCode());
        // 如果快递单号有更新,则清空物流信息
        wrapper.set(!express.getExpressNo().equals(request.getExpressNo()), OrderExpress::getContent, null);
        orderExpressMapper.update(null, wrapper);
    }

    @Override
    public List<FirstExpressVO> getFirstExpressList(String orderNo) {
        LambdaQueryWrapper<OrderExpress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderExpress::getOrderNo, orderNo);
        List<OrderExpress> selectList = orderExpressMapper.selectList(wrapper);
        if (selectList.isEmpty()) {
            return Lists.newArrayList();
        }
        // 如果只有一条物流信息,则直接返回(减少不必要的代码判断)
        return this.getFirstExpress(selectList);
    }

    @Override
    public OrderExpress selectById(Long id) {
        return orderExpressMapper.selectById(id);
    }

    /**
     * 计算每个物流的最新节点
     *
     * @param expressList 物流信息
     * @return list
     */
    public List<FirstExpressVO> getFirstExpress(Collection<OrderExpress> expressList) {
        List<FirstExpressVO> voList = Lists.newArrayList();
        for (OrderExpress express : expressList) {
            List<ExpressVO> vos = jsonService.fromJsonList(express.getContent(), ExpressVO.class);
            if (CollUtil.isNotEmpty(vos)) {
                FirstExpressVO vo = new FirstExpressVO();
                vo.setId(express.getId());
                vo.setContent(vos.get(0).getContent());
                voList.add(vo);
            }
        }
        return voList;
    }
}
