package com.eghm.vo.sys.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/7
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuResponse {

    @ApiModelProperty("id主键")
    private String id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("菜单标示符 唯一")
    private String code;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("按钮地址")
    private String subPath;

    @ApiModelProperty("菜单级别 1:导航 2:按钮")
    private Integer grade;

    @ApiModelProperty("子菜单")
    private List<MenuResponse> children;

    @ApiModelProperty("排序")
    @JsonIgnore
    private Integer sort;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    private String pid;

}
