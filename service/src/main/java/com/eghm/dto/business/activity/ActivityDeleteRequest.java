package com.eghm.dto.business.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/1/5
 */
@Data
public class ActivityDeleteRequest {

    @ApiModelProperty("活动标题")
    private String title;

    @ApiModelProperty("id")
    private Long id;
}
