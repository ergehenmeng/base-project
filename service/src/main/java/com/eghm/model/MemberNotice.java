package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户站内信
 *
 * @author 二哥很猛
 */
@Data
@TableName("member_notice")
@EqualsAndHashCode(callSuper = true)
public class MemberNotice extends BaseEntity {

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "消息标题")
    private String title;

    @Schema(description = "站内信内容")
    private String content;

    @Schema(description = "站内信分类")
    private MessageType messageType;

    @Schema(description = "状态 0:未读 1:已读")
    private Boolean isRead;

    @Schema(description = "消息所属日志id")
    private Long noticeLogId ;

}