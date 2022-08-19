package com.eghm.service.business.handler;

import com.eghm.model.dto.business.order.ticket.ApplyRefundDTO;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
public interface ApplyRefundHandler {

    /**
     * 退款申请
     * @param dto 退款申请参数
     */
    void process(ApplyRefundDTO dto);
}
