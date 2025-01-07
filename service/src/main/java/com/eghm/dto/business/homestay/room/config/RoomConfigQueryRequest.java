package com.eghm.dto.business.homestay.room.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/29 22:08
 */
@Data
public class RoomConfigQueryRequest {

    @Schema(description = "月份 yyyy-MM", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "月份不能为空")
    private String month;

    @Schema(description = "房型id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "房型id不能为空")
    private Long roomId;
}
