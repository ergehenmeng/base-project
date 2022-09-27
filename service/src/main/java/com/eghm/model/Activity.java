package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("activity")
@ApiModel(value="Activity对象", description="活动信息表")
public class Activity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动名称")
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

}
