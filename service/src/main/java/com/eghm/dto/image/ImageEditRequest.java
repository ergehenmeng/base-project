package com.eghm.dto.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2018/11/29 17:00
 */
@Data
public class ImageEditRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "图片名称", required = true)
    @NotBlank(message = "图片名称不能为空")
    private String title;

    @ApiModelProperty(value = "图片类型", required = true)
    @NotNull(message = "图片类型不能为空")
    private Integer imageType;

    @ApiModelProperty("备注信息")
    private String remark;

}
