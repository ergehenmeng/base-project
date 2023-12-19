package com.eghm.dto.business.order.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
public class ItemExpressRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("订单号")
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty("快递编号")
    @NotBlank(message = "快递编号不能为空")
    private String expressCode;

    @ApiModelProperty("快递单号")
    @NotBlank(message = "快递单号不能为空")
    @Size(max = 20, message = "快递单号长度最大20位")
    private String expressNo;
}
