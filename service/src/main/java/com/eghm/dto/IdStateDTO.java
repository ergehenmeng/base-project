package com.eghm.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛 2022/6/17 19:03
 */
@Data
public class IdStateDTO {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("状态 0:待上架 1:已上架")
    @NotNull(message = "状态不能为空")
    private Integer state;
}
