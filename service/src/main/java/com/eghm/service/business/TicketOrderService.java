package com.eghm.service.business;

import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 创建门票订单
     * @param dto 下单信息
     */
    void create(CreateTicketOrderDTO dto);
}
