package com.eghm.dto.business.line.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/8/30
 */
@Data
public class LineConfigQueryRequest {

    @ApiModelProperty(value = "月份 yyyy-MM", required = true)
    @NotBlank(message = "月份不能为空")
    private String month;

    @ApiModelProperty(value = "线路id", required = true)
    @NotNull(message = "线路id不能为空")
    private Long lineId;
}
