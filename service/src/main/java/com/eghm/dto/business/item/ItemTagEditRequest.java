package com.eghm.dto.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/8/9
 */
@Data
public class ItemTagEditRequest {

    @ApiModelProperty("id不能为空")
    @NotBlank(message = "id不能为空")
    private String id;

    @ApiModelProperty("标签名称")
    @NotBlank(message = "标签名称不能为空")
    private String title;

    @ApiModelProperty(value = "标签图标")
    @NotBlank(message = "标签icon不能为空")
    private String icon;

    @ApiModelProperty("排序id")
    private Integer sort;
}
