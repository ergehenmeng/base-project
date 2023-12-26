package com.eghm.dto.address;

import com.eghm.annotation.Assign;
import com.eghm.validation.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @ApiModelProperty("收货人姓名")
    @Size(max = 10, message = "收货人姓名最大10字符")
    @NotBlank(message = "收货人姓名不能为空")
    private String nickName;

    @ApiModelProperty("收货人手机号")
    @Mobile(message = "收货人手机号格式错误")
    private String mobile;

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

    @ApiModelProperty(value = "是否为默认地址 true:是 false:不是")
    private Boolean state;
}
