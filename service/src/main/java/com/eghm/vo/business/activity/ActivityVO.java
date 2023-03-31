package com.eghm.vo.business.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/1/9
 */

@Data
public class ActivityVO {

    @ApiModelProperty("id主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String title;

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

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
