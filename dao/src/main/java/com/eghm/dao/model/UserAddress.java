package com.eghm.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 二哥很猛
 */
@Data
public class UserAddress implements Serializable {

    /**
     * 默认地址
     */
    public static final byte STATE_DEFAULT = 1;

    /**
     * 普通地址
     */
    public static final byte STATE_COMMON = 0;

    /**
     * 主键<br>
     * 表 : user_address<br>
     * 对应字段 : id<br>
     */
    private Integer id;

    /**
     * 用户id<br>
     * 表 : user_address<br>
     * 对应字段 : user_id<br>
     */
    private Integer userId;

    /**
     * 状态 0: 普通地址  1:默认地址<br>
     * 表 : user_address<br>
     * 对应字段 : state<br>
     */
    private Byte state;

    /**
     * 省份id<br>
     * 表 : user_address<br>
     * 对应字段 : province_id<br>
     */
    private Integer provinceId;

    /**
     * 省份名称<br>
     * 表 : user_address<br>
     * 对应字段 : province_name<br>
     */
    private String provinceName;

    /**
     * 城市id<br>
     * 表 : user_address<br>
     * 对应字段 : city_id<br>
     */
    private Integer cityId;

    /**
     * 城市名称<br>
     * 表 : user_address<br>
     * 对应字段 : city_name<br>
     */
    private String cityName;

    /**
     * 县区id<br>
     * 表 : user_address<br>
     * 对应字段 : county_id<br>
     */
    private Integer countyId;

    /**
     * 县区id<br>
     * 表 : user_address<br>
     * 对应字段 : county_name<br>
     */
    private String countyName;

    /**
     * 详细地址<br>
     * 表 : user_address<br>
     * 对应字段 : detail_address<br>
     */
    private String detailAddress;

    /**
     * 添加时间<br>
     * 表 : user_address<br>
     * 对应字段 : add_time<br>
     */
    private Date addTime;

    /**
     * 更新时间<br>
     * 表 : user_address<br>
     * 对应字段 : update_time<br>
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}