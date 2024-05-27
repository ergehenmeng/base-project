package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    @ApiModelProperty("id主键")
    private String id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单标示符 唯一")
    private String code;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    private String pid;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("菜单级别 1:导航菜单 2:按钮菜单")
    private Integer grade;

    @ApiModelProperty("排序规则 小的排在前面")
    private Integer sort;

    @ApiModelProperty("状态:1:正常,0:禁用")
    private Boolean state;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("该菜单包含的子url以分号做分割")
    private String subPath;

    @ApiModelProperty("菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放)")
    private Integer displayState;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}