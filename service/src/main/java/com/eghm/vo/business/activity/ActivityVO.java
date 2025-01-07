package com.eghm.vo.business.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
public class ActivityVO {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "活动时间")
    private String activityTime;

    @Schema(description = "活动地点")
    private String address;

    @Schema(description = "活动封面图片")
    private String coverUrl;

    @Schema(description = "活动详细介绍")
    private String introduce;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime updateTime;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;

}
