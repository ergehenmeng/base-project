package com.eghm.service.business;

import com.eghm.model.SysDict;
import com.eghm.service.business.handler.OrderExpireHandler;
import com.eghm.service.business.handler.PayNotifyHandler;

import java.util.List;

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

    /**
     * 根据给定的字典列表和标签id进行解析
     * @param dictList 字典列表
     * @param tagIds 标签id 逗号分割
     * @return 标签名称
     */
    List<String> parseTags(List<SysDict> dictList, String tagIds);
}
