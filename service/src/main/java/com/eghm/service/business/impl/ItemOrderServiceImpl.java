package com.eghm.service.business.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemOrderMapper;
import com.eghm.model.ItemOrder;
import com.eghm.model.ItemSku;
import com.eghm.service.business.ItemOrderService;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<ItemOrder> getByOrderNo(String orderNo) {
        LambdaQueryWrapper<ItemOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemOrder::getOrderNo, orderNo);
        return itemOrderMapper.selectList(wrapper);
    }

    @Override
    public void insert(String orderNo, List<OrderPackage> packageList, Map<Long, Integer> skuExpressMap) {
        for (OrderPackage aPackage : packageList) {
            ItemOrder order = DataUtil.copy(aPackage.getItem(), ItemOrder.class);
            BeanUtil.copyProperties(aPackage.getSku(), order);
            order.setSkuTitle(this.getSkuTitle(aPackage.getSku()));
            order.setExpressFee(skuExpressMap.get(order.getSkuId()));
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
            throw new BusinessException(ErrorCode.ITEM_ORDER_NULL);
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

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId) {
        ItemOrder selected = this.selectByIdRequired(orderId);
        ProductSnapshotVO vo = new ProductSnapshotVO();
        vo.setProductId(selected.getItemId());
        vo.setProductTitle(selected.getTitle());
        vo.setProductCover(selected.getCoverUrl());
        return vo;
    }

    /**
     * 拼接sku名称
     * @param sku sku信息
     * @return sku名称
     */
    private String getSkuTitle(ItemSku sku) {
        if (StrUtil.isBlank(sku.getSecondSpecValue())) {
            return sku.getPrimarySpecValue();
        }
        return sku.getPrimarySpecValue() + "/" + sku.getSecondSpecValue();
    }
}
