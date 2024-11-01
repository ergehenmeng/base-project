package com.eghm.dto.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2018/11/28 17:33
 */
@Data
public class ImageAddRequest {

    @ApiModelProperty(value = "图片名称", required = true)
    @NotBlank(message = "图片名称不能为空")
    private String title;

    @ApiModelProperty(value = "图片类型", required = true)
    @NotNull(message = "图片类型不能为空")
    private Integer imageType;

    @ApiModelProperty("图片大小")
    private Long size;

    @ApiModelProperty("地址")
    private String path;

    @ApiModelProperty("备注信息")
    @Size(max = 100, message = "备注信息不能超过100字")
    private String remark;
}
