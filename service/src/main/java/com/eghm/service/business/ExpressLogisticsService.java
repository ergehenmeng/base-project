package com.eghm.service.business;

import com.eghm.model.ExpressLogistics;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2025/5/28
 */
public interface ExpressLogisticsService {

    /**
     * 新增物流信息(如果存在则不插入)
     *
     * @param expressNo 快递单号
     * @param expressCode 快递公司编码
     * @param phone   收发人手机号
     */
    void insert(String expressNo, String expressCode, String phone);

    /**
     * 根据订单查询物流信息
     * @param orderNo 订单编号
     * @return 物流信息
     */
    List<ExpressLogistics> getExpress(String orderNo);

}
