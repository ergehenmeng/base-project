package com.eghm.dao.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 短信发送记录表
 * @author 二哥很猛
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsLog extends BaseEntity {

    @ApiModelProperty("短信分类")
    private String smsType;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信内容")
    private String content;

    @ApiModelProperty("发送状态 0:失败 1:已发送")
    private Byte state;

}