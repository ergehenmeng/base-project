package com.eghm.model.dto.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2018/11/29 17:00
 */
@Data
public class ImageEditRequest implements Serializable {

    private static final long serialVersionUID = 4134425550056402012L;

    /**
     * 主键
     */
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("图片名称")
    @NotNull(message = "图片名称不能为空")
    private String title;

    @ApiModelProperty("图片类型")
    @NotNull(message = "图片类型不能为空")
    private Byte classify;

    @ApiModelProperty("备注信息")
    private String remark;

}
