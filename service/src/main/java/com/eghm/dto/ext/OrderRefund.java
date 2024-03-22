package com.eghm.dto.ext;

import com.eghm.enums.ref.PayType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/8/8
 */
@Data
public class OrderRefund {

    @ApiModelProperty("支付订单流水号")
    private String tradeNo;

    @ApiModelProperty("退款订单流水号")
    private String refundNo;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("支付方式")
    private PayType payType;
}
