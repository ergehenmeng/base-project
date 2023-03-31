package com.eghm.service.business.handler.context;

import com.eghm.model.Order;
import com.eghm.model.ScenicTicket;
import com.eghm.dto.ext.PreOrder;
import com.eghm.state.machine.Context;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class TicketCreateContext implements Context {

    /**
     * 门票id
     */
    private Long ticketId;

    /**
     * 景区门票
     */
    private ScenicTicket scenicTicket;

    /**
     * 商品基本信息
     */
    private PreOrder preOrder;

    /**
     * 生成的订单
     */
    private Order order;

    /**
     * 原状态
     */
    private Integer from;

    /**
     * 新状态
     */
    private Integer to;


}
