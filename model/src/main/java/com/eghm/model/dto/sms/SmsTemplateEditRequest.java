package com.eghm.model.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 二哥很猛
 * @date 2019/11/25 10:21
 */
@Data
public class SmsTemplateEditRequest implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("id主键")
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 短信内容
     */
    @ApiModelProperty("短信模板内容")
    @NotNull(message = "短信内容不能为空")
    private String content;

    /**
     * 备注信息
     */
    @ApiModelProperty("备注信息")
    private String remark;
}
