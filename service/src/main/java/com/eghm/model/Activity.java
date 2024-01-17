package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
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
    @TableField(typeHandler = LikeTypeHandler.class)
    private String title;

    @ApiModelProperty(value = "日期")
    private LocalDate nowDate;

    @ApiModelProperty(value = "开始时间HH:mm")
    private String startTime;

    @ApiModelProperty(value = "结束时间HH:mm")
    private String endTime;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "活动封面图片")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍")
    private String introduce;

    @ApiModelProperty("活动关联的景区")
    private Long scenicId;
}
