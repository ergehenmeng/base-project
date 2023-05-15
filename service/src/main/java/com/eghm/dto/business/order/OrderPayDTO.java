package com.eghm.dto.business.order;

import com.eghm.service.pay.enums.TradeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @since 2023/5/5
 */
@Data
public class OrderPayDTO {

    @ApiModelProperty("订单编号,多笔订单(购物车订单)逗号分隔")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("购买人id(openId或者buyerId)")
    @NotBlank(message = "购买人id不能为空")
    private String buyerId;

    @ApiModelProperty("支付方式")
    @NotNull(message = "支付方式不能为空")
    private TradeType tradeType;
}
