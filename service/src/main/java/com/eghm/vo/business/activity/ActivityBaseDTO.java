package com.eghm.vo.business.activity;

import com.eghm.convertor.SplitterArraySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ActivityBaseDTO {

    @ApiModelProperty("活动id")
    private Long id;

    @ApiModelProperty("活动名称")
    private String title;

    @ApiModelProperty("活动图片")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @ApiModelProperty(value = "活动地点")
    private String address;

    @ApiModelProperty(value = "活动时间")
    private String activityTime;
}
