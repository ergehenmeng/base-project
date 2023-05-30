package com.eghm.service.business;

import com.eghm.model.VerifyLog;

/**
 * @author 二哥很猛
 * @date 2022/8/6
 */
public interface VerifyLogService {

    /**
     * 统计某个订单被核销过的商品数量总数
     * @param orderNo 订单编号
     * @return 数量
     */
    int getVerifiedNum(String orderNo);

    /**
     * 添加核销记录
     * @param verifyLog 核销记录
     */
    void insert(VerifyLog verifyLog);
}
