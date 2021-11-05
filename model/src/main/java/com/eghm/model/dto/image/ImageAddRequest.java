package com.eghm.model.dto.image;

import com.eghm.model.annotation.Sign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/28 17:33
 */
@Data
public class ImageAddRequest implements Serializable {

    private static final long serialVersionUID = 3775459871066834161L;

    @ApiModelProperty("图片名称")
    @NotNull(message = "图片名称不能为空")
    private String title;

    @ApiModelProperty("图片类型")
    @NotNull(message = "图片类型不能为空")
    private Byte classify;

    @ApiModelProperty("备注信息")
    private String remark;

    /**
     * 图片大小
     */
    @Sign
    private Long size;

    /**
     * 地址
     */
    @Sign
    private String path;
}
