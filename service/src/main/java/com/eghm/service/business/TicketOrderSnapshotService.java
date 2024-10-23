package com.eghm.service.business;

/**
 * <p>
 * 组合票订单表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketOrderSnapshotService {

    /**
     * 插入组合门票订单
     *
     * @param orderNo 订单号
     * @param ticketId 门票ID
     */
    void insert(String orderNo, Long ticketId);

}
