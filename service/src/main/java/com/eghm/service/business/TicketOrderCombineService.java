package com.eghm.service.business;

import com.eghm.model.TicketOrderCombine;
import com.eghm.vo.business.order.ticket.CombineOrderResponse;

import java.util.List;

/**
 * <p>
 * 套票票订单表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-10-23
 */
public interface TicketOrderCombineService {

    /**
     * 插入套票门票订单
     *
     * @param orderNo 订单号
     * @param ticketId 门票ID
     */
    void insert(String orderNo, Long ticketId);

    /**
     * 获取套票订单信息
     *
     * @param combineId 套票票id
     * @return 套票订单信息
     */
    TicketOrderCombine selectById(Long combineId);

    /**
     * 根据订单号获取套票下的门票信息
     *
     * @param orderNo 订单号
     * @return 订单门票信息
     */
    List<TicketOrderCombine> getByOrderNo(String orderNo);

    /**
     * 更新套票信息
     *
     * @param combine 套票信息
     */
    void updateById(TicketOrderCombine combine);

    /**
     * 查询订单所有组合票信息
     *
     * @param orderNo 订单编号
     * @return 票信息
     */
    List<CombineOrderResponse> getCombineList(String orderNo);
}
