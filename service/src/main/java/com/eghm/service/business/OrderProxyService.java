package com.eghm.service.business;

import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;

/**
 * @author 二哥很猛
 * @since 2022/8/17
 */
public interface OrderProxyService {

    /**
     * 确认是否有房
     *
     * @param request 确认状态
     */
    void confirm(HomestayOrderConfirmRequest request);

    /**
     * 零售主动退款
     *
     * @param orderNo 订单编号
     */
    void refund(String orderNo);

    /**
     * 取消拼团订单
     *
     * @param bookingNo 拼团订单号
     */
    void cancelGroupOrder(String bookingNo);
}
