package com.eghm.service.business.handler.dto;

import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("源状态")
    private Integer from;

    @ApiModelProperty("新状态")
    private Integer to;

    @Override
    public void setFrom(Integer from) {
        this.from = from;
    }

    @Override
    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public Integer getFrom() {
        return from;
    }

    @Override
    public Integer getTo() {
        return to;
    }
}
