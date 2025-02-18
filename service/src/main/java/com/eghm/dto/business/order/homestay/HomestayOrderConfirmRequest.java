package com.eghm.dto.business.order.homestay;

import com.eghm.enums.ConfirmState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/9
 */

@Data
public class HomestayOrderConfirmRequest {

    @ApiModelProperty(value = "确认状态 1:确认有房 2:确认无房", required = true)
    @NotNull(message = "确认状态不能为空")
    private ConfirmState confirmState;

    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty("备注信息")
    @Size(max = 50, message = "备注信息最大50字符")
    private String remark;
}
