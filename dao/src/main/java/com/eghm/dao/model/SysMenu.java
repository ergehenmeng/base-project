package com.eghm.dao.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    /**
     * 三级菜单即为按钮菜单
     */
    public static final byte BUTTON = 3;

    @ApiModelProperty("id主键")
    private String id;

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单标示符 唯一")
    private String nid;

    @ApiModelProperty("父节点ID,一级菜单默认为0")
    private String pid;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("菜单级别 1:一级菜单(导航) 2:二级菜单(导航) 3:三级菜单(按钮)")
    private Byte grade;

    @ApiModelProperty("排序规则 小的排在前面")
    private Integer sort;

    @ApiModelProperty("状态:0:正常,1:已删除")
    private Boolean deleted;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("该菜单包含的子url以分号做分割")
    private String subPath;

    @ApiModelProperty("添加日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    @ApiModelProperty("更新日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @TableField(exist = false)
    @ApiModelProperty("子订单")
    private List<SysMenu> children;

}