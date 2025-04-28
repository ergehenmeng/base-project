package com.eghm.vo.business.activity;

import com.eghm.convertor.SplitterArraySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ActivityBaseDTO {

    @Schema(description = "活动id")
    private Long id;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "活动图片")
    @JsonSerialize(using = SplitterArraySerializer.class)
    private String coverUrl;

    @Schema(description = "活动地点")
    private String address;

    @Schema(description = "活动时间")
    private String activityTime;
}
