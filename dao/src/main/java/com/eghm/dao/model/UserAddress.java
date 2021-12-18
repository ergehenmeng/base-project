package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 二哥很猛
 */
@Getter
@Setter
@ToString(callSuper = true)
public class UserAddress extends BaseEntity {

    /**
     * 默认地址
     */
    public static final byte STATE_DEFAULT = 1;

    /**
     * 普通地址
     */
    public static final byte STATE_COMMON = 0;

    /**
     * 用户id<br>
     * 表 : user_address<br>
     * 对应字段 : user_id<br>
     */
    private Long userId;

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
    private Long provinceId;

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
    private Long cityId;

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
    private Long countyId;

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
     * 删除状态 0:未删除 1:已删除<br>
     * 表 : user_address<br>
     * 对应字段 : deleted<br>
     */
    private Boolean deleted;

}