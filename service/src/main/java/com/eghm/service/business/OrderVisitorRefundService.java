package com.eghm.service.business;

import java.util.List;

/**
 * <p>
 * 游客退款记录关联表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-01
 */
public interface OrderVisitorRefundService {

    /**
     * 退款时添加退款记录与游客关联关系
     *
     * @param orderNo     订单编号
     * @param refundId    退款记录id
     * @param visitorList 退款人
     */
    void insertVisitorRefund(String orderNo, Long refundId, List<Long> visitorList);
}
