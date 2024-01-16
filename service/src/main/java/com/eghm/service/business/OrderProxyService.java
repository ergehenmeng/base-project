package com.eghm.service.business;

import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface OrderProxyService {

    /**
     * 确认是否有房
     *
     * @param request 确认状态
     */
    void confirm(HomestayOrderConfirmRequest request);
}
