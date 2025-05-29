package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.JsonService;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.dto.business.order.item.OrderExpressRequest;
import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.model.ExpressLogistics;
import com.eghm.model.ItemOrderExpress;
import com.eghm.service.business.ExpressLogisticsService;
import com.eghm.service.business.ItemOrderExpressService;
import com.eghm.vo.business.order.item.ExpressLogisticsVO;
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
@Service("itemOrderExpressService")
@AllArgsConstructor
public class ItemOrderExpressServiceImpl implements ItemOrderExpressService {

    private final JsonService jsonService;

    private final ItemOrderExpressMapper itemOrderExpressMapper;

    private final ExpressLogisticsService expressLogisticsService;

    @Override
    public void insert(ItemSippingRequest request) {
        List<Long> orderIds = request.getOrderIds();
        for (Long orderId : orderIds) {
            ItemOrderExpress express = new ItemOrderExpress();
            express.setExpressNo(request.getExpressNo());
            express.setOrderNo(request.getOrderNo());
            express.setItemOrderId(orderId);
            itemOrderExpressMapper.insert(express);
        }
        expressLogisticsService.insert(request.getExpressNo(), request.getExpressCode(), request.getExpressCode());
    }

    @Override
    public void update(OrderExpressRequest request) {
        expressLogisticsService.insert(request.getExpressNo(), request.getExpressCode(), request.getExpressCode());
        LambdaUpdateWrapper<ItemOrderExpress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemOrderExpress::getOrderNo, request.getOrderNo());
        wrapper.eq(ItemOrderExpress::getId, request.getId());
        wrapper.set(ItemOrderExpress::getExpressNo, request.getExpressNo());
        itemOrderExpressMapper.update(wrapper);
    }

    @Override
    public List<FirstExpressVO> getFirstExpressList(String orderNo) {
        List<ExpressLogistics> selectList = expressLogisticsService.getExpress(orderNo);
        if (selectList.isEmpty()) {
            return Lists.newArrayList();
        }
        // 如果只有一条物流信息,则直接返回(减少不必要的代码判断)
        return this.getFirstExpress(selectList);
    }

    @Override
    public ExpressLogisticsVO getById(Long id) {
        return itemOrderExpressMapper.getById(id);
    }

    /**
     * 计算每个物流的最新节点
     *
     * @param expressList 物流信息
     * @return list
     */
    public List<FirstExpressVO> getFirstExpress(Collection<ExpressLogistics> expressList) {
        List<FirstExpressVO> voList = Lists.newArrayList();
        for (ExpressLogistics express : expressList) {
            List<ExpressVO> vos = jsonService.fromJsonList(express.getContent(), ExpressVO.class);
            if (CollUtil.isNotEmpty(vos)) {
                FirstExpressVO vo = new FirstExpressVO();
                vo.setExpressId(express.getId());
                vo.setContext(vos.get(0).getContext());
                voList.add(vo);
            }
        }
        return voList;
    }
}
