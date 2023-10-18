package com.eghm.dto.business.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/10/18
 */
@Data
public class ActivityQueryRequest {

    @ApiModelProperty("月份 yyyy-MM")
    @NotBlank(message = "月份不能为空")
    private String month;

    @ApiModelProperty("景区id")
    private Long scenicId;
}
