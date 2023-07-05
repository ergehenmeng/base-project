package com.eghm.service.business;

import com.eghm.dto.business.order.ticket.TicketOfflineRefundRequest;

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
     * 查询订单线下退款人id
     * @param orderNo 订单编号
     * @return 游客id
     */
    List<Long> getRefundLog(String orderNo);

    /**
     * 添加线下退款日志
     * @param request 退款信息
     */
    void insertLog(TicketOfflineRefundRequest request);
}
