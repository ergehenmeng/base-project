package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.NoticeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员站内信日志
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("member_notice_log")
public class MemberNoticeLog extends BaseEntity {

    @Schema(description = "通知名称")
    private String title;

    @Schema(description = "通知内容")
    private String content;

    @Schema(description = "消息类型")
    private NoticeType noticeType;

    @Schema(description = "消息参数")
    private String params;

    @Schema(description = "发送人id")
    private Long operatorId;

}
