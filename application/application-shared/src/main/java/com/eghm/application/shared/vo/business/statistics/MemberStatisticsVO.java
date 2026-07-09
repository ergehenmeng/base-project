package com.eghm.application.shared.vo.business.statistics;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/8/13
 */

@Data
public class MemberStatisticsVO {

    @Schema(description = "渠道统计")
    private List<PieDataVO> channelList;

    @Schema(description = "性别统计")
    private List<PieDataVO> sexList;
}
