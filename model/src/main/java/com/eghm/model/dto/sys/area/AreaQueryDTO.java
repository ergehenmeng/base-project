package com.eghm.model.dto.sys.area;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/4
 */
@Data
public class AreaQueryDTO {

    /**
     * 区域代码父节点id
     */
    @ApiModelProperty("区域代码父节点id,0:表示查询一级节点")
    private Integer pid;
}
