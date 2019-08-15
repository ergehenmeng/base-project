package com.fanyin.model.system;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 */
@Data
public class SystemMenu implements Serializable {

    private static final long serialVersionUID = 3059147246970916080L;
    /**
     * 主键<br>
     * 表 : system_menu<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 菜单名称<br>
     * 表 : system_menu<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 菜单标示符 唯一<br>
     * 表 : system_menu<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 父节点ID,一级菜单默认为0<br>
     * 表 : system_menu<br>
     * 对应字段 : pid<br>
     */
    private Integer pid;

    /**
     * 菜单地址<br>
     * 表 : system_menu<br>
     * 对应字段 : url<br>
     */
    private String url;

    /**
     * 菜单分类 0:左侧菜单 1: 按钮菜单<br>
     * 表 : system_menu<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    /**
     * 排序规则 小的排在前面<br>
     * 表 : system_menu<br>
     * 对应字段 : sort<br>
     */
    private Integer sort;

    /**
     * 状态:0:正常,1:已删除<br>
     * 表 : system_menu<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

    /**
     * 备注信息<br>
     * 表 : system_menu<br>
     * 对应字段 : remark<br>
     */
    private String remark;

    /**
     * 添加时间<br>
     * 表 : system_menu<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : system_menu<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    /**
     * 该菜单包含的子url以分号做分割<br>
     * 表 : system_menu<br>
     *  对应字段 : sub_url<br>
     */
    private String subUrl;

    /**
     * 子菜单列表
     */
    private List<SystemMenu> subList;


}