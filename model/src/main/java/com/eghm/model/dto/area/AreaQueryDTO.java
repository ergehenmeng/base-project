package com.eghm.model.dto.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@Data
public class AreaQueryDTO {

    @ApiModelProperty(value = "区域代码父节点id,0:表示查询一级节点",required = true)
    @NotNull(message = "节点id不能为空")
    private Long pid;
}
