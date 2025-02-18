package com.eghm.dto.business.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/11/27
 */
@Data
public class OrderExpressRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @Schema(description = "快递编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "快递编号不能为空")
    private String expressCode;

    @Schema(description = "快递单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "快递单号不能为空")
    @Size(max = 20, message = "快递单号长度最大20位")
    private String expressNo;
}
