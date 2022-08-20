package com.eghm.service.business.handler;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface OrderExpireHandler {

    /**
     * 订单过期处理
     * @param orderNo 订单编号
     */
    void process(String orderNo);
}
