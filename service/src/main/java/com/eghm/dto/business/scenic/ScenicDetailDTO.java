package com.eghm.dto.business.scenic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ScenicDetailDTO {

    @Schema(description = "景区id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择景区")
    private Long scenicId;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;
}
