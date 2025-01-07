package com.eghm.dto.business.merchant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2022/5/27
 */
@Data
public class MerchantRateRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "平台服务费,单位:%", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin(value = "0", message = "平台服务费应在0~10之间")
    @DecimalMax(value = "10", message = "平台服务费应在0~10之间")
    private BigDecimal platformServiceRate;
}
