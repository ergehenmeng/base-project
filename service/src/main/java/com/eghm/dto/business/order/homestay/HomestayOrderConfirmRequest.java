package com.eghm.dto.business.order.homestay;

import com.eghm.enums.ConfirmState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/9
 */

@Data
public class HomestayOrderConfirmRequest {

    @Schema(description = "确认状态 1:确认有房 2:确认无房", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "确认状态不能为空")
    private ConfirmState confirmState;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @Schema(description = "备注信息")
    @Size(max = 50, message = "备注信息最大50字符")
    private String remark;
}
