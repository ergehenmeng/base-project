package com.eghm.service.business;

import com.eghm.model.ItemOrder;
import com.eghm.service.business.handler.dto.OrderPackage;
import com.eghm.vo.business.order.ProductSnapshotVO;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
public interface ItemOrderService {

    /**
     * 订单插入
     * @param order 商品订单
     */
    void insert(ItemOrder order);

    /**
     * 根据订单号查询商品订单
     * @param orderNo 订单号
     * @return 商品订单(普通商品一个订单存在多商品)
     */
    List<ItemOrder> getByOrderNo(String orderNo);

    /**
     * 批量添加子订单信息
     * @param orderNo 订单号
     * @param packageList 商品信息
     * @param skuExpressMap 快递费
     */
    void insert(String orderNo, List<OrderPackage> packageList, Map<Long, Integer> skuExpressMap);

    /**
     * 查询订单信息
     * @param id id
     * @return 商品订单信息
     */
    ItemOrder selectById(Long id);

    /**
     * 查询商品订单,如果为空则抛异常
     * @param id id
     * @return 商品订单信息
     */
    ItemOrder selectByIdRequired(Long id);

    /**
     * 根据主键更新商品订单信息
     * @param itemOrder 商品订单
     */
    void updateById(ItemOrder itemOrder);

    /**
     * 获取订单下所有商品的数量
     * @param orderNo 订单编号
     * @return 总数量
     */
    int getProductNum(String orderNo);

    /**
     * 查询订单商品快照
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品基础信息
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);
}
