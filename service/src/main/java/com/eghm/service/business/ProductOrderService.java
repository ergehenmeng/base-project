package com.eghm.service.business;

import com.eghm.dao.model.ProductOrder;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
public interface ProductOrderService {

    /**
     * 订单插入
     * @param order 商品订单
     */
    void insert(ProductOrder order);

    /**
     * 根据订单号查询商品订单
     * @param orderNo 订单号
     * @return 商品订单(普通商品一个订单存在多商品)
     */
    List<ProductOrder> selectByOrderNo(String orderNo);
}
