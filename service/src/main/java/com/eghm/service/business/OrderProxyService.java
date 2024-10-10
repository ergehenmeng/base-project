package com.eghm.service.business;

import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;
import com.eghm.model.ItemGroupOrder;

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
    void itemRefund(String orderNo);

    /**
     * 退款 (非零售商品)
     *
     * @param orderNo 订单号
     */
    void refund(String orderNo);

    /**
     * 取消订单 (通用)
     *
     * @param orderNo 订单编号(通用)
     */
    void cancel(String orderNo, Long memberId);

    /**
     * 取消拼团订单
     *
     * @param group 拼团订单
     */
    void doCancelGroupOrder(ItemGroupOrder group);

}
