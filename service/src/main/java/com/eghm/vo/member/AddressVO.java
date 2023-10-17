package com.eghm.vo.member;

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

    @ApiModelProperty("id")
    private Long id;

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

    @ApiModelProperty("县区名称")
    private String countyName;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("状态 0:普通地址 1:默认地址")
    private Integer state;
}
