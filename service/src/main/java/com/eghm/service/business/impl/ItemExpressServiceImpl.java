package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.order.item.ItemExpressRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.mapper.ItemExpressMapper;
import com.eghm.mapper.ItemOrderExpressMapper;
import com.eghm.model.ItemExpress;
import com.eghm.model.ItemOrderExpress;
import com.eghm.service.business.ItemExpressService;
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
@Service("itemOrderExpressService")
@AllArgsConstructor
public class ItemExpressServiceImpl implements ItemExpressService {

    private final ItemOrderExpressMapper itemOrderExpressMapper;

    private final ItemExpressMapper itemExpressMapper;

    private final JsonService jsonService;

    @Override
    public void insert(ItemSippingRequest request) {
        ItemExpress itemExpress = DataUtil.copy(request, ItemExpress.class);
        itemExpressMapper.insert(itemExpress);
        List<Long> orderIds = request.getOrderIds();
        for (Long orderId : orderIds) {
            ItemOrderExpress express = new ItemOrderExpress();
            express.setExpressId(itemExpress.getId());
            express.setOrderNo(request.getOrderNo());
            express.setItemOrderId(orderId);
            itemOrderExpressMapper.insert(express);
        }
    }

    @Override
    public void update(ItemExpressRequest request) {
        LambdaUpdateWrapper<ItemExpress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemExpress::getId, request.getId());
        // 尽量防止更新的订单不是自己商品下的
        wrapper.eq(ItemExpress::getOrderNo, request.getOrderNo());
        wrapper.set(ItemExpress::getExpressNo, request.getExpressNo());
        wrapper.set(ItemExpress::getExpressCode, request.getExpressCode());
        wrapper.set(ItemExpress::getContent, null);
        itemExpressMapper.update(null, wrapper);
    }

    @Override
    public List<FirstExpressVO> getFirstExpressList(String orderNo) {
        LambdaQueryWrapper<ItemExpress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemExpress::getOrderNo, orderNo);
        List<ItemExpress> selectList = itemExpressMapper.selectList(wrapper);
        if (selectList.isEmpty()) {
            return Lists.newArrayList();
        }
        // 如果只有一条物流信息,则直接返回(减少不必要的代码判断)
        return this.getFirstExpress(selectList);
    }

    @Override
    public ItemExpress selectById(Long id) {
        return itemExpressMapper.selectById(id);
    }

    /**
     * 计算每个物流的最新节点
     *
     * @param expressList 物流信息
     * @return list
     */
    public List<FirstExpressVO> getFirstExpress(Collection<ItemExpress> expressList) {
        List<FirstExpressVO> voList = Lists.newArrayList();
        for (ItemExpress express : expressList) {
            List<ExpressVO> vos = jsonService.fromJsonList(express.getContent(), ExpressVO.class);
            if (CollUtil.isNotEmpty(vos)) {
                FirstExpressVO vo = new FirstExpressVO();
                vo.setId(express.getId());
                vo.setContext(vos.get(0).getContext());
                voList.add(vo);
            }
        }
        return voList;
    }
}
