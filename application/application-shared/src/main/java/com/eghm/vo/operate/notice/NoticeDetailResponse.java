package com.eghm.vo.operate.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理端公告详情响应
 *
 * @author 二哥很猛
 */
@Data
public class NoticeDetailResponse {

    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告类型")
    private Integer noticeType;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "是否发布 0:未发布 1:已发布")
    private Integer state;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
