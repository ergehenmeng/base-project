package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dao.mapper.ProductOrderMapper;
import com.eghm.dao.model.ProductOrder;
import com.eghm.service.business.ProductOrderService;
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
}
