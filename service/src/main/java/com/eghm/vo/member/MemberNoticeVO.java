package com.eghm.vo.member;

import com.eghm.enums.NoticeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@Data
public class MemberNoticeVO {

    @Schema(description = "通知id")
    private Long id;

    @Schema(description = "通知标题")
    private String title;

    @Schema(description = "通知内容")
    private String content;

    @Schema(description = "通知类型")
    private NoticeType noticeType;

    @Schema(description = "已读未读状态 true:已读 false:未读")
    private Boolean isRead;

    @JsonFormat(pattern = "MM-dd HH:mm")
    @Schema(description = "处理时间 MM-dd HH:mm")
    private LocalDateTime createTime;
}
