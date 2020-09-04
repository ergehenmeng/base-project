package com.eghm.dao.model.system;

import lombok.Data;

import java.io.Serializable;

/**
 * 省市县三级地址
 * @author 二哥很猛
 */
@Data
public class SysArea implements Serializable {
    /**
     * 区域代码 <br>
     * 表 : sys_area<br>
     * 对应字段 : id <br>
     */
    private Integer id;

    /**
     * 区域名称<br>
     * 表 : sys_area<br>
     * 对应字段 : title<br>
     */
    private String title;

    /**
     * 父级区域代码<br>
     * 表 : sys_area<br>
     * 对应字段 : pid<br>
     */
    private String pid;

    /**
     * 邮编<br>
     * 表 : sys_area<br>
     * 对应字段 : zip_code<br>
     */
    private String zipCode;

    /**
     * 标示符-首字母<br>
     * 表 : sys_area<br>
     * 对应字段 : mark<br>
     */
    private String mark;

    /**
     * 分类 省份1级 市2级 县3级<br>
     * 表 : sys_area<br>
     * 对应字段 : classify<br>
     */
    private Byte classify;

    private static final long serialVersionUID = 1L;

}