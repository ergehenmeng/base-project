package com.eghm.service.business;

import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 创建门票订单
     * 1. 校验门票信息
     * 2. 校验优惠券信息(如果有的话)
     * 3. 创建订单
     * 4. 增加联系人(如果有的话)
     * 5. 更新库存
     * 6. MQ门票订单超时处理
     * @param dto 下单信息
     */
    void create(CreateTicketOrderDTO dto);

    /**
     * 根据订单编号查询订单订单信息
     * @param orderNo 订单编号
     * @return 门票订单信息
     */
    TicketOrder selectByOrderNo(String orderNo);

}
