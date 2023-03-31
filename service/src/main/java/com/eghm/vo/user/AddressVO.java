package com.eghm.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Data
@ApiModel
public class AddressVO {

    /**
     * 省份id
     */
    @ApiModelProperty("省份id")
    private Long provinceId;

    /**
     * 省份名称
     */
    @ApiModelProperty("省份名称")
    private String provinceName;

    /**
     * 城市id
     */
    @ApiModelProperty("城市id")
    private Long cityId;

    /**
     * 城市名称
     */
    @ApiModelProperty("城市名称")
    private String cityName;

    /**
     * 区县id
     */
    @ApiModelProperty("县区id")
    private Long countyId;

    /**
     * 区县名称
     */
    @ApiModelProperty("县区名称")
    private String countyName;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    private String detailAddress;

    /**
     * 状态 0:普通地址 1: 默认地址
     */
    @ApiModelProperty("状态 0:普通地址 1:默认地址")
    private Integer state;
}
