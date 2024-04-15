package com.eghm.dto.business.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* @author 二哥很猛
* @since 2024-04-15
*/
@Data
public class PayConfigEditRequest {

    @ApiModelProperty(value = "主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "是否开启微信支付", required = true)
    @NotNull(message = "是否开启微信支付不能为空")
    private Boolean wechatPay;

    @ApiModelProperty(value = "是否开启支付宝支付", required = true)
    @NotNull(message = "是否开启支付宝支付不能为空")
    private Boolean aliPay;

}
