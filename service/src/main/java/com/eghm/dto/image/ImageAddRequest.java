package com.eghm.dto.image;

import com.eghm.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/28 17:33
 */
@Data
public class ImageAddRequest implements Serializable {

    private static final long serialVersionUID = 3775459871066834161L;

    @ApiModelProperty(value = "图片名称", required = true)
    @NotBlank(message = "图片名称不能为空")
    private String title;

    @ApiModelProperty(value = "图片类型", required = true)
    @NotNull(message = "图片类型不能为空")
    private Integer classify;

    @ApiModelProperty("备注信息")
    private String remark;

    @Sign
    @ApiModelProperty("图片大小")
    private Long size;

    @Sign
    @ApiModelProperty("地址")
    private String path;
}
