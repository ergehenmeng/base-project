package com.eghm.service.business;

import com.eghm.model.ExpressLogistics;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/28
 */
public interface ExpressLogisticsService {

    /**
     * 新增或更新物流信息
     *
     * @param expressNo 快递单号
     * @param expressCode 快递公司编码
     */
    void insertOrUpdate(String expressNo, String expressCode);

    /**
     * 根据订单查询物流信息
     * @param orderNo 订单编号
     * @return 物流信息
     */
    List<ExpressLogistics> getExpress(String orderNo);

}
