package com.eghm.service.business;

import com.eghm.service.business.handler.OrderExpireHandler;
import com.eghm.service.business.handler.PayNotifyHandler;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
public interface CommonService {

    /**
     * 校验日期间隔是否超过最大值
     *
     * @param configNid 配置nid
     * @param maxValue  日期间隔值
     */
    void checkMaxDay(String configNid, long maxValue);

    /**
     * 根据订单编号查询订单处理类
     *
     * @param orderNo 订单编号 以ProductType中的prefix开头的订单
     * @return 订单处理类
     */
    PayNotifyHandler getPayHandler(String orderNo);

    /**
     * 订单过期处理类
     * @param orderNo 订单编号
     * @return handler
     */
    OrderExpireHandler getExpireHandler(String orderNo);
}
