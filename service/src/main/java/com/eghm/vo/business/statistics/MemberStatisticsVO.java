package com.eghm.vo.business.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/8/13
 */

@Data
public class MemberStatisticsVO {

    @ApiModelProperty("渠道统计")
    private List<MemberChannelVO> channelList;

    @ApiModelProperty("性别统计")
    private List<MemberSexVO> sexList;
}
