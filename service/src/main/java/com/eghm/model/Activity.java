package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * <p>
 * 活动信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-18
 */
@Data
@TableName("activity")
@EqualsAndHashCode(callSuper = false)
public class Activity extends BaseEntity {

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "日期")
    private LocalDate nowDate;

    @Schema(description = "活动时间")
    private String activityTime;

    @Schema(description = "活动地点")
    private String address;

    @Schema(description = "活动封面图片")
    private String coverUrl;

    @Schema(description = "活动详细介绍")
    private String introduce;

    @Schema(description = "活动关联的景区")
    private Long scenicId;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;
}
