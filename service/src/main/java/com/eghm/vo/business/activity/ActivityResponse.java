package com.eghm.vo.business.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class ActivityResponse {

    @ApiModelProperty("日期")
    private LocalDate nowDate;

    @ApiModelProperty("活动列表")
    private List<ActivityBaseDTO> activityList;

}
