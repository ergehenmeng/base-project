package com.eghm.vo.business.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class ActivityResponse {

    @Schema(description = "日期")
    private LocalDate nowDate;

    @Schema(description = "活动列表")
    private List<ActivityBaseDTO> activityList;

}
