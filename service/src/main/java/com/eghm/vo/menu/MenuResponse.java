package com.eghm.vo.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/7
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuResponse {

    @Schema(description = "id主键")
    private String id;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "菜单标示符 唯一")
    private String code;

    @Schema(description = "菜单地址")
    private String path;

    @Schema(description = "按钮地址")
    private String subPath;

    @Schema(description = "菜单级别 1:导航 2:按钮")
    private Integer grade;

    @Schema(description = "子菜单")
    private List<MenuResponse> children;

    @Schema(description = "排序")
    @JsonIgnore
    private Integer sort;

    @Schema(description = "父节点ID,一级菜单默认为0")
    private String pid;

}
