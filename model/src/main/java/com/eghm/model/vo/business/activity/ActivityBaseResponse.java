package com.eghm.model.vo.business.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Data
public class ActivityBaseResponse {

    @ApiModelProperty("日期")
    private LocalDate nowDate;

    @ApiModelProperty("活动列表")
    private List<ActivityBaseDTO> activityList;

}
