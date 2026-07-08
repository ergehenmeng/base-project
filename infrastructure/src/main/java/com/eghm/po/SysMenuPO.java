package com.eghm.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 */
@Data
@TableName("sys_menu")
public class SysMenuPO {

    /** id主键 */
    private String id;

    /** 菜单名称 */
    private String title;

    /** 菜单标示符 唯一 */
    private String code;

    /** 菜单图标 */
    private String icon;

    /** 父节点ID,一级菜单默认为0 */
    private String pid;

    /** 菜单地址 */
    private String path;

    /** 菜单级别 1:导航菜单 2:按钮菜单 */
    private Integer grade;

    /** 排序规则 小的排在前面 */
    private Integer sort;

    /** 状态:1:正常,0:禁用 */
    private Boolean state;

    /** 备注信息 */
    private String remark;

    /** 该菜单包含的子url以分号做分割 */
    private String subPath;

    /** 菜单类型: 1:商户菜单(该菜单或按钮只对商户开放) 2:系统菜单(该菜单或按钮只对系统人员开放) 3:通用菜单(该菜单或按钮对商户和系统人员都开放) */
    private Integer displayState;

    /** 添加日期 */
    private LocalDateTime createTime;

    /** 更新日期 */
    private LocalDateTime updateTime;

}

