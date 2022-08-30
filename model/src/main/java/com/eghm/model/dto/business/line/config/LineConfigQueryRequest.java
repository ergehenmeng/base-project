package com.eghm.model.dto.business.line.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @date 2022/8/30
 */
@Data
public class LineConfigQueryRequest {

    @ApiModelProperty("月份 yyyy-MM")
    @NotNull(message = "月份不能为空")
    private String month;

    @ApiModelProperty("线路id")
    @NotNull(message = "线路id不能为空")
    private Long lineId;
}
