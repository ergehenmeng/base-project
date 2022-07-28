package com.eghm.service.business;

/**
 * 订单相关公共接口
 * @author 二哥很猛
 * @date 2022/7/28
 */
public interface OrderService {

    /**
     * 订单过期处理(超时未支付)
     * @param orderNo 订单编号
     */
    void orderExpireHandler(String orderNo);
}
