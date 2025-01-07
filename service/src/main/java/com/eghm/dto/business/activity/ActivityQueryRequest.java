package com.eghm.dto.business.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/10/18
 */
@Data
public class ActivityQueryRequest {

    @Schema(description = "月份 yyyy-MM")
    @NotBlank(message = "月份不能为空")
    private String month;

    @Schema(description = "景区id")
    private Long scenicId;
}
