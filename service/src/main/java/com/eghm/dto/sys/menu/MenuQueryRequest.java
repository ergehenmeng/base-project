package com.eghm.dto.sys.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */
@Data
public class MenuQueryRequest {

    @ApiModelProperty(value = "搜索条件")
    private String queryName;

    @ApiModelProperty("父节点Id")
    private String pid;
}
