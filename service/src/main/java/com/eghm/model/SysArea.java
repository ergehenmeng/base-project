package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 省市县三级地址
 * @author 二哥很猛
 */
@Data
@TableName("sys_area")
public class SysArea {

    /**
     * 省级
     */
    public static final int CLASSIFY_PROVINCE = 1;

    /**
     * 市级
     */
    public static final int CLASSIFY_CITY = 2;

    /**
     * 县级
     */
    public static final int CLASSIFY_COUNTY = 3;

    @ApiModelProperty("区域代码")
    private Long id;

    @ApiModelProperty("区域名称")
    private String title;

    @ApiModelProperty("父级区域代码")
    private Long pid;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("标示符-首字母")
    private String mark;

    @ApiModelProperty("分类 省份1级 市2级 县3级")
    private Integer classify;

}