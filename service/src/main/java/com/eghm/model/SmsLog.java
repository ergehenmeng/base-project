package com.eghm.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.SmsTemplateType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 短信发送记录表
 *
 * @author 二哥很猛
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("sms_log")
public class SmsLog {

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("短信分类")
    private SmsTemplateType smsTemplateType;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信内容")
    private String content;

    @ApiModelProperty("发送状态 0:失败 1:已发送")
    private Integer state;

}