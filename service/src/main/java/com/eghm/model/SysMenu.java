package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单标示符 唯一")
    private String code;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    @JsonSerialize(using = ToStringSerializer.class)
    private String pid;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("菜单级别 1:导航菜单 2:按钮菜单")
    private Integer grade;

    @ApiModelProperty("是否对超管隐藏 true: 是 false: 不隐藏")
    private Boolean adminHide;

    @ApiModelProperty("排序规则 小的排在前面")
    private Integer sort;

    @ApiModelProperty("状态:1:正常,0:禁用")
    private Boolean state;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("该菜单包含的子url以分号做分割")
    private String subPath;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}