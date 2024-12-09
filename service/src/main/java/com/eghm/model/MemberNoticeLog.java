package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.MessageType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员站内信日志(仅仅记录日志, 方便后续排查问题)
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_notice_log")
public class MemberNoticeLog extends BaseEntity {

    @ApiModelProperty(value = "通知名称")
    private String title;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "消息类型")
    private MessageType messageType;

    @ApiModelProperty(value = "消息参数")
    private String params;

    @ApiModelProperty("发送人id")
    private Long operatorId;

}
