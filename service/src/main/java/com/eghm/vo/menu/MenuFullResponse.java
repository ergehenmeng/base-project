package com.eghm.vo.menu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/9/7
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MenuFullResponse {

    @Schema(description = "id主键")
    private String id;

    @Schema(description = "菜单名称")
    private String title;

    @Schema(description = "父节点ID,一级菜单默认为0")
    private String pid;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "菜单地址")
    private String path;

    @Schema(description = "按钮地址")
    private String subPath;

    @Schema(description = "状态 true:启用 false:禁用")
    private Boolean state;

    @Schema(description = "菜单级别 1:导航 2:按钮")
    private Integer grade;

    @Schema(description = "菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    private Integer displayState;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注信息")
    private String remark;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "子菜单")
    private List<MenuFullResponse> children;

}
