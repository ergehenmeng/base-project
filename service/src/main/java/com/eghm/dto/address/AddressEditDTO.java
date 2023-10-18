package com.eghm.dto.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.WordChecker;
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

    @ApiModelProperty(value = "用户id", hidden = true)
    @Assign
    private Long memberId;

    @ApiModelProperty(value = "地址id",required = true)
    @NotNull(message = "id不能为空")
    private Long id;

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
    @WordChecker
    private String detailAddress;

    @ApiModelProperty(value = "是否为默认地址 true:是 false:不是")
    private Boolean state;
}
