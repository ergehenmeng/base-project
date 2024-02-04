package com.eghm.service.business;

import com.eghm.model.VenueOrder;
import com.eghm.model.VoucherOrder;

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
}
