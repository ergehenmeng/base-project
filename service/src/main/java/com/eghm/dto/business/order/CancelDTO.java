package com.eghm.dto.business.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * @author 二哥很猛
 * @since 2024/1/2
 */
@Data
public class CancelDTO {

    @Schema(description = "交易单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "交易单号不能为空")
    private String tradeNo;
}
