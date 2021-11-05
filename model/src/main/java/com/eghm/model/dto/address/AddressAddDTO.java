package com.eghm.model.dto.address;

import com.eghm.model.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 殿小二
 * @date 2020/9/8
 */
@Data
public class AddressAddDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(hidden = true)
    @Sign
    private Long userId;

    /**
     * 省份id
     */
    @ApiModelProperty(value = "省份id",required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    /**
     * 城市id
     */
    @ApiModelProperty(value = "城市id",required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    /**
     * 县区id
     */
    @ApiModelProperty(value = "县区id",required = true)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    /**
     * 详细地址
     */
    @ApiModelProperty(value = "详细地址",required = true)
    @NotEmpty(message = "详细地址不能为空")
    private String detailAddress;

}
