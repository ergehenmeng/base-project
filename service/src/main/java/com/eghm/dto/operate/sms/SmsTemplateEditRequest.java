package com.eghm.dto.operate.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2019/11/25 10:21
 */
@Data
public class SmsTemplateEditRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "短信模板内容", required = true)
    @NotBlank(message = "短信内容不能为空")
    private String content;

    @ApiModelProperty("备注信息")
    private String remark;
}
