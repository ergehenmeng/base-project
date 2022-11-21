package com.eghm.service.business.handler;

import com.eghm.service.business.handler.dto.ApplyRefundContext;

/**
 * @author 二哥很猛
 * @date 2022/8/19
 */
public interface ApplyRefundHandler  {

    /**
     * 退款申请
     * @param dto 退款申请参数
     */
    void process(ApplyRefundContext dto);
}
