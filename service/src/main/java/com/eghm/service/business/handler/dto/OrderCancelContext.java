package com.eghm.service.business.handler.dto;

import com.eghm.state.machine.Context;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/11/21
 */
@Data
public class OrderCancelContext implements Context {

    @ApiModelProperty("订单编号")
    private String orderNo;

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
