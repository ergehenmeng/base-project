package com.eghm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/11/29
 */
@Data
public class StateRequest {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "状态 true:启用 false:禁用", required = true)
    @NotNull(message = "状态不能为空")
    private Boolean state;
}
