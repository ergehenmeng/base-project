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

    @ApiModelProperty("状态 false:禁用 true:启用")
    private Boolean state;

    @ApiModelProperty("菜单权限: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    private Integer displayState;

    @ApiModelProperty("菜单类型 1:导航 2:按钮")
    private Integer grade;
}
