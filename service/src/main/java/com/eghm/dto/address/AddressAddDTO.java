package com.eghm.dto.address;

import com.eghm.annotation.Padding;
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

    @ApiModelProperty(hidden = true)
    @Padding
    private Long memberId;

    @ApiModelProperty("是否为默认地址 0:非默认 1:默认")
    private Integer state;

    @ApiModelProperty(value = "省份id",required = true)
    @NotNull(message = "省份不能为空")
    private Long provinceId;

    @ApiModelProperty(value = "城市id",required = true)
    @NotNull(message = "城市不能为空")
    private Long cityId;

    @ApiModelProperty(value = "县区id",required = true)
    @NotNull(message = "县区不能为空")
    private Long countyId;

    @ApiModelProperty(value = "详细地址",required = true)
    @NotEmpty(message = "详细地址不能为空")
    private String detailAddress;

}
