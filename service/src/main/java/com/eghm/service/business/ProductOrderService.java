package com.eghm.service.business;

import com.eghm.dao.model.ProductOrder;
import com.eghm.service.business.handler.dto.OrderPackage;

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

    /**
     * 批量添加子订单信息
     * @param packageList 商品信息
     */
    void insert(String orderNo, List<OrderPackage> packageList);

    /**
     * 查询订单信息
     * @param id id
     * @return 商品订单信息
     */
    ProductOrder selectById(Long id);

    /**
     * 查询商品订单,如果为空则抛异常
     * @param id id
     * @return 商品订单信息
     */
    ProductOrder selectByIdRequired(Long id);

    /**
     * 根据主键更新商品订单信息
     * @param productOrder 商品订单
     */
    void updateById(ProductOrder productOrder);
}
