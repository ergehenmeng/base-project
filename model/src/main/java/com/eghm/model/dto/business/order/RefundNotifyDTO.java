package com.eghm.model.dto.business.order;

import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Data
public class RefundNotifyDTO {

    /**
     * 支付流水号
     */
    private String outTradeNo;

    /**
     * 退款流水号
     */
    private String outRefundNo;
}
