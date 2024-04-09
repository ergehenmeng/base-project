package com.eghm.vo.business.lottery;

import com.eghm.enums.ref.LotteryState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */

@Data
public class LotteryDetailResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "活动状态 0:未开始 1:进行中 2:已结束")
    private LotteryState state;

    @ApiModelProperty(value = "单日抽奖次数限制")
    private Integer lotteryDay;

    @ApiModelProperty(value = "总抽奖次数限制")
    private Integer lotteryTotal;

    @ApiModelProperty(value = "中奖次数限制")
    private Integer winNum;

    @ApiModelProperty(value = "抽奖页面封面图")
    private String coverUrl;

    @ApiModelProperty(value = "抽奖标题")
    private String subTitle;

    @ApiModelProperty(value = "抽奖规则")
    private String rule;

    @ApiModelProperty("奖品列表")
    private List<LotteryPrizeResponse> prizeList;

    @ApiModelProperty("抽奖配置")
    private List<LotteryConfigResponse> configList;
}
