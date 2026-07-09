package com.eghm.infrastructure.persistence.mybatis.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 省市县三级地址
 *
 * @author 二哥很猛
 */
@Data
@TableName("sys_area")
public class SysAreaPO {

    /**
     * 省级
     */
    public static final int PROVINCE = 1;

    /**
     * 市级
     */
    public static final int CITY = 2;

    /**
     * 县级
     */
    public static final int COUNTY = 3;

    /** 区域代码 */
    private Long id;

    /** 区域名称 */
    private String title;

    /** 父级区域代码 */
    private Long pid;

    /** 标示符-首字母 */
    private String mark;

    /** 分类 省份1级 市2级 县3级 */
    private Integer grade;

}
