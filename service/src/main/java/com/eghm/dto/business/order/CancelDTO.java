package com.eghm.dto.business.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2024/1/2
 */
@Data
public class CancelDTO {

    @ApiModelProperty(value = "交易单号", required = true)
    @NotBlank(message = "交易单号不能为空")
    private String tradeNo;
}
