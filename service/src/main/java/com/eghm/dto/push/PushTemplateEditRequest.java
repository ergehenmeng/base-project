package com.eghm.dto.push;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/11/27 13:56
 */
@Data
public class PushTemplateEditRequest implements Serializable {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "推送标题", required = true)
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "推送内容", required = true)
    @NotBlank(message = "内容不能为空")
    private String content;

    @ApiModelProperty(value = "推送状态 false:关闭 true:开启", required = true)
    @NotNull(message = "推送状态不能为空")
    private Boolean state;

    @ApiModelProperty(value = "备注信息")
    private String remark;
}
