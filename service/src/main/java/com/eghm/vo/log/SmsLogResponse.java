package com.eghm.vo.log;

import com.eghm.enums.TemplateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class SmsLogResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("短信分类")
    private TemplateType templateType;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信内容")
    private String content;

    @ApiModelProperty("发送状态 0:失败 1:已发送")
    private Integer state;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
