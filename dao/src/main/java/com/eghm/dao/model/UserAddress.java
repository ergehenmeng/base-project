package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("状态 0: 普通地址  1:默认地址")
    private Byte state;

    @ApiModelProperty("省份id")
    private Long provinceId;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市id")
    private Long cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("县区id")
    private Long countyId;

    @ApiModelProperty("县区")
    private String countyName;

    @ApiModelProperty("详细地址")
    private String detailAddress;

}