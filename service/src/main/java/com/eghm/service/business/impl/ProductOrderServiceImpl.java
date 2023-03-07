package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.ProductOrderMapper;
import com.eghm.model.ProductOrder;
import com.eghm.service.business.ProductOrderService;
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
@Service("productOrderService")
@Slf4j
@AllArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderMapper productOrderMapper;

    @Override
    public void insert(ProductOrder order) {
        productOrderMapper.insert(order);
    }

    @Override
    public List<ProductOrder> selectByOrderNo(String orderNo) {
        LambdaQueryWrapper<ProductOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductOrder::getOrderNo, orderNo);
        return productOrderMapper.selectList(wrapper);
    }

    @Override
    public void insert(String orderNo, List<OrderPackage> packageList) {
        for (OrderPackage aPackage : packageList) {
            ProductOrder order = DataUtil.copy(aPackage.getItem(), ProductOrder.class);
            order.setOrderNo(orderNo);
            order.setProductId(aPackage.getProductId());
            ProductSku sku = aPackage.getSku();
            order.setNum(aPackage.getNum());
            order.setSkuTitle(sku.getTitle());
            order.setSkuId(sku.getId());
            order.setSkuCoverUrl(sku.getCoverUrl());
            order.setLinePrice(sku.getLinePrice());
            order.setCostPrice(sku.getCostPrice());
            productOrderMapper.insert(order);
        }
    }

    @Override
    public ProductOrder selectById(Long id) {
        return productOrderMapper.selectById(id);
    }

    @Override
    public ProductOrder selectByIdRequired(Long id) {
        ProductOrder order = productOrderMapper.selectById(id);
        if (order == null) {
            log.error("商品订单信息未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.PRODUCT_ORDER_NULL);
        }
        return order;
    }

    @Override
    public void updateById(ProductOrder productOrder) {
        productOrderMapper.updateById(productOrder);
    }

    @Override
    public int getProductNum(String orderNo) {
        return productOrderMapper.getProductNum(orderNo);
    }
}
