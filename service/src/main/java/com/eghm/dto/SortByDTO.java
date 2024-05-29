package com.eghm.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用于排序
 *
 * @author 殿小二
 * @since 2022/12/29
 */
@Data
public class SortByDTO {

    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "id", required = true)
    @RangeInt(max = 999, message = "排序字段应为0~999之间")
    private Integer sortBy;
}
