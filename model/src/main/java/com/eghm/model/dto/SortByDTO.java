package com.eghm.model.dto;

import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于排序
 * @author 殿小二
 * @date 2022/12/29
 */
@Data
public class SortByDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "id", required = true)
    @RangeInt(min = 1, max = 999, message = "排序字段应为1~999之间")
    private Integer sortBy;
}
