package com.eghm.service.business;

import java.util.List;

/**
 * <p>
 * 线下退款记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-14
 */
public interface OfflineRefundLogService {

    /**
     * 查询门票订单线下退款人id
     * @param orderNo 订单编号
     * @return 游客id
     */
    List<Long> getTicketRefundLog(String orderNo);
}
