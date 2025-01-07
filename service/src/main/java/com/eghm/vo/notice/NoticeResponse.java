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
public class NoticeResponse {

    @Schema(description = "公告id")
    private Long id;

    @Schema(description = "公告名称")
    private String title;

    @Schema(description = "公告类型(数据字典表notice_type)")
    private Integer noticeType;

    @Schema(description = "是否发布 0:未发布 1:已发布")
    private Integer state;

    @Schema(description = "封面图片")
    private String coverUrl;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
