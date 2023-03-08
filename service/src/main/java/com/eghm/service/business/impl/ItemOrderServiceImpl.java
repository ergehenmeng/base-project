package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.ItemOrderMapper;
import com.eghm.model.ItemOrder;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Service("itemOrderService")
@Slf4j
@AllArgsConstructor
public class ItemOrderServiceImpl implements ItemOrderService {

    private final ItemOrderMapper itemOrderMapper;

    @Override
    public void insert(ItemOrder order) {
        itemOrderMapper.insert(order);
    }

    @Override
    public List<ItemOrder> selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<ItemOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemOrder::getOrderNo, orderNo);
        return itemOrderMapper.selectList(wrapper);
    }

    @Override
    public void insert(String orderNo, List<OrderPackage> packageList) {
        for (OrderPackage aPackage : packageList) {
            ItemOrder order = DataUtil.copy(aPackage.getItem(), ItemOrder.class);
//            order.setOrderNo(orderNo);
//            order.setItemId(aPackage.getProductId());
//            ProductSku sku = aPackage.getSku();
//            order.setNum(aPackage.getNum());
//            order.setSkuTitle(sku.getTitle());
//            order.setSkuId(sku.getId());
//            order.setSkuCoverUrl(sku.getCoverUrl());
//            order.setLinePrice(sku.getLinePrice());
//            order.setCostPrice(sku.getCostPrice());
            itemOrderMapper.insert(order);
        }
    }

    @Override
    public ItemOrder selectById(Long id) {
        return itemOrderMapper.selectById(id);
    }

    @Override
    public ItemOrder selectByIdRequired(Long id) {
        ItemOrder order = itemOrderMapper.selectById(id);
        if (order == null) {
            log.error("商品订单信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.PRODUCT_ORDER_NULL);
        }
        return order;
    }

    @Override
    public void updateById(ItemOrder itemOrder) {
        itemOrderMapper.updateById(itemOrder);
    }

    @Override
    public int getProductNum(String orderNo) {
        return itemOrderMapper.getProductNum(orderNo);
    }
}
