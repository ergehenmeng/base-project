package com.eghm.dto.business;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
* @author 二哥很猛
* @since 2024-10-09
*/
@Data
public class VoucherTagAddRequest {

    @ApiModelProperty(value = "标签名称", required = true)
    @NotBlank(message = "标签名称不能为空")
    @Size(max = 10, message = "标签名称最大10字符")
    private String title;

    @ApiModelProperty(value = "餐饮商家id", required = true)
    @NotNull(message = "请选择所属店铺")
    private Long restaurantId;

    @ApiModelProperty(value = "状态 0:禁用 1:启用", required = true)
    private Boolean state;

    @ApiModelProperty(value = "备注")
    private String remark;

}
