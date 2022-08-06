package com.eghm.service.business;

/**
 * @author 二哥很猛
 * @date 2022/8/6
 */
public interface VerifyLogService {

    /**
     * 统计某个订单被核销过的商品数量总数
     * @param orderId 订单id
     * @return 数量
     */
    int getVerifiedNum(Long orderId);

}
