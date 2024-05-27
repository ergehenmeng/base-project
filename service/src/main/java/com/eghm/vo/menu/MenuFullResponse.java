package com.eghm.vo.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/7
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuFullResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    private Long pid;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("按钮地址")
    private String subPath;

    @ApiModelProperty(value = "状态 true:启用 false:禁用")
    private Boolean state;

    @ApiModelProperty("菜单级别 1:导航 2:按钮")
    private Integer grade;

    @ApiModelProperty("菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    private Integer displayState;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("子菜单")
    private List<MenuFullResponse> children;

}
