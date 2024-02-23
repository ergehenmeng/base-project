package com.eghm.dto.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/1/18
 */

@Data
public class ConfirmReceiptDTO {

    @ApiModelProperty(value = "订单号", required = true)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

}
