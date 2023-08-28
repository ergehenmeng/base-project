package com.eghm.service.business;

import com.eghm.model.RestaurantOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
public interface RestaurantOrderService {

    /**
     * 插入餐饮订单
     * @param order 订单信息
     */
    void insert(RestaurantOrder order);

    /**
     * 根据订单编号查询餐饮订单
     * @param orderNo 订单编号
     * @return 餐饮订单
     */
    RestaurantOrder getByOrderNo(String orderNo);

    /**
     * 主键查询
     * @param id id
     * @return 餐饮订单
     */
    RestaurantOrder selectById(Long id);

    /**
     * 查询餐饮快照
     * @param orderId 订单id
     * @return 商品餐饮快照
     */
    ProductSnapshotVO getSnapshot(Long orderId);
}
