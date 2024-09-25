package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "日期")
    private LocalDate nowDate;

    @ApiModelProperty(value = "活动时间")
    private String activityTime;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "活动封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍")
    private String introduce;

    @ApiModelProperty("活动关联的景区")
    private Long scenicId;

    @ApiModelProperty("是否支持评论")
    private Boolean commentSupport;
}
