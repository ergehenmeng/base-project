package com.eghm.dto.business.homestay.room.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/6/29 22:08
 */
@Data
public class RoomConfigQueryRequest {

    @ApiModelProperty(value = "月份 yyyy-MM", required = true)
    @NotBlank(message = "月份不能为空")
    private String month;

    @ApiModelProperty(value = "房型id", required = true)
    @NotNull(message = "房型id不能为空")
    private Long roomId;
}
