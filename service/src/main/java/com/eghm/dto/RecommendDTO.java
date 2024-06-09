package com.eghm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/6/9
 */

@Data
public class RecommendDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "推荐状态", required = true)
    @NotNull(message = "推荐状态不能为空")
    private Boolean recommend;
}
