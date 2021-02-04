package com.eghm.model.dto.address;

import com.eghm.model.annotation.BackstageTag;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2020/9/10
 */
@Data
public class AddressEditDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    @BackstageTag
    private Long userId;

    /**
     * id不能为空
     */
    @ApiModelProperty(value = "地址id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 省份id
     */
    @ApiModelProperty(value = "省份id",required = true)
    @NotNull(message = "省份不能为空")
    private Integer provinceId;

    /**
     * 城市id
     */
    @ApiModelProperty(value = "城市id",required = true)
    @NotNull(message = "城市不能为空")
    private Integer cityId;

    /**
     * 县区id
     */
    @ApiModelProperty(value = "县区id",required = true)
    @NotNull(message = "县区不能为空")
    private Integer countyId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址",required = true)
    @NotEmpty(message = "详细地址不能为空")
    private String detailAddress;

    /**
     * 设置为默认地址
     */
    @ApiModelProperty(value = "是否为默认地址 true:是 false:不是")
    private Boolean state;
}
