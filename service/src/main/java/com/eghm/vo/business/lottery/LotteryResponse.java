package com.eghm.vo.business.lottery;

import com.eghm.enums.LotteryState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "状态 0:未开始 1:进行中 2:已结束")
    private LotteryState state;

    @ApiModelProperty(value = "单日抽奖次数限制")
    private Integer lotteryDay;

    @ApiModelProperty(value = "总抽奖次数限制")
    private Integer lotteryTotal;

    @ApiModelProperty(value = "中奖次数限制")
    private Integer winNum;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
