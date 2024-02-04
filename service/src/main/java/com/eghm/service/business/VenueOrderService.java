package com.eghm.service.business;

import com.eghm.model.VenueOrder;

/**
 * <p>
 * 场馆预约订单表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
public interface VenueOrderService {

    /**
     * 新增场馆预约订单
     *
     * @param venueOrder 订单信息
     */
    void insert(VenueOrder venueOrder);

    /**
     * 查询场馆预约订单
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    VenueOrder getByOrderNo(String orderNo);

    /**
     * 更新预约订单库存
     *
     * @param orderNo 订单编号
     * @param num +:增加库存 -:减少库存
     */
    void updateStock(String orderNo, Integer num);
}
