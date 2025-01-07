package com.eghm.vo.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 公告置顶vo
 *
 * @author 二哥很猛
 * @since 2019/11/25 15:30
 */
@Data
public class NoticeDetailVO {

    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "公告名称")
    private String title;

    @Schema(description = "公告内容(富文本)")
    private String content;

    @Schema(description = "公告类型名称")
    private String noticeType;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
