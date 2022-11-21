package com.eghm.service.business.handler.dto;

import com.eghm.state.machine.Context;
import lombok.Data;

/**
 * @author 二哥很猛
 * @date 2022/8/20
 */
@Data
public class RefundNotifyContext implements Context {

    /**
     * 支付流水号
     */
    private String outTradeNo;

    /**
     * 退款流水号
     */
    private String outRefundNo;

    @Override
    public void setFrom(Integer from) {

    }

    @Override
    public void setTo(Integer to) {

    }

    @Override
    public Integer getFrom() {
        return null;
    }

    @Override
    public Integer getTo() {
        return null;
    }
}
