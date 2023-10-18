package com.eghm.dto.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.WordChecker;
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
    @Assign
    private Long memberId;

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
    @WordChecker(message = "详细地址存在敏感字")
    private String detailAddress;

}
