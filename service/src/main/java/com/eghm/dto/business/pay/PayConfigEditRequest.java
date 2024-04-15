package com.eghm.dto.business.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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

    @ApiModelProperty("备注信息")
    @Length(max = 200, message = "备注信息最大200字符")
    private String remark;

}
