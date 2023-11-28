package com.eghm.service.business;

import com.eghm.dto.business.order.item.ItemSippingRequest;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
public interface ItemOrderExpressService {

    /**
     * 插入订单物流信息
     *
     * @param request request
     */
    void insert(ItemSippingRequest request);
}
