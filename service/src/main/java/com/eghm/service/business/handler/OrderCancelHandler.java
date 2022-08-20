package com.eghm.service.business.handler;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
public interface OrderCancelHandler {

    /**
     * 订单取消处理
     * @param orderNo 订单编号
     */
    void process(String orderNo);
}
