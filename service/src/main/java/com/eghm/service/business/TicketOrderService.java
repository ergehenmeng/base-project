package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.TicketOrder;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import com.eghm.vo.business.order.ticket.TicketOrderVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 分页查询门票订单列表
     * @param request 查询条件
     * @return 订单列表
     */
    Page<TicketOrderResponse> getByPage(TicketOrderQueryRequest request);

    /**
     * 插入门票订单信息
     * @param order 门票订单
     */
    void insert(TicketOrder order);

    /**
     * 根据订单编号查询订单订单信息
     * @param orderNo 订单编号
     * @return 门票订单信息
     */
    TicketOrder selectByOrderNo(String orderNo);

    /**
     * 分页查询用户订单列表
     * @param query 分页信息
     * @param memberId
     * @return
     */
    List<TicketOrderVO> getList(PagingQuery query, Long memberId);
}
