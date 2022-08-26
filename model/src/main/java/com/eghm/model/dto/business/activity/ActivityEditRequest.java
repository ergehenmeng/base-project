package com.eghm.model.dto.business.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Data
public class ActivityEditRequest {

    @ApiModelProperty("活动id")
    @NotNull(message = "活动id不能为空")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    @Size(min = 2, max = 20, message = "活动名称长度2~20位")
    private String title;

    @ApiModelProperty(value = "开始时间HH:mm")
    @NotNull(message = "开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "结束时间HH:mm")
    @NotNull(message = "结束时间不能为空")
    private String endTime;

    @ApiModelProperty(value = "活动地点")
    @NotNull(message = "活动地点不能为空")
    private String address;

    @ApiModelProperty(value = "活动封面图片")
    @NotNull(message = "活动封面图片不能为空")
    private String coverUrl;

    @ApiModelProperty(value = "活动详细介绍")
    @NotNull(message = "活动详细介绍不能为空")
    private String introduce;
}
