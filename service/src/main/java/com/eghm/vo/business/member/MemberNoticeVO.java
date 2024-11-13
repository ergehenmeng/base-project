package com.eghm.vo.business.member;

import com.eghm.enums.NoticeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 殿小二
 * @since 2020/9/12
 */
@Data
public class MemberNoticeVO {

    @ApiModelProperty(value = "通知id")
    private Long id;

    @ApiModelProperty(value = "通知标题")
    private String title;

    @ApiModelProperty(value = "通知内容")
    private String content;

    @ApiModelProperty(value = "通知类型")
    private NoticeType noticeType;

    @ApiModelProperty(value = "已读未读状态 true:已读 false:未读")
    private Boolean isRead;

    @JsonFormat(pattern = "MM-dd HH:mm")
    @ApiModelProperty(value = "处理时间 MM-dd HH:mm")
    private LocalDateTime createTime;
}
