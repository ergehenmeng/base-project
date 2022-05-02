package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 省市县三级地址
 * @author 二哥很猛
 */
@Data
public class SysArea implements Serializable {

    /**
     * 省级
     */
    public static final byte CLASSIFY_PROVINCE = 1;

    /**
     * 市级
     */
    public static final byte CLASSIFY_CITY = 2;

    /**
     * 县级
     */
    public static final byte CLASSIFY_COUNTY = 3;

    @ApiModelProperty("区域代码")
    private Integer id;

    @ApiModelProperty("区域名称")
    private String title;

    @ApiModelProperty("父级区域代码")
    private Integer pid;

    @ApiModelProperty("邮编")
    private String zipCode;

    @ApiModelProperty("标示符-首字母")
    private String mark;

    @ApiModelProperty("分类 省份1级 市2级 县3级")
    private Byte classify;

}