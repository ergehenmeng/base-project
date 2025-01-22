package com.eghm.vo.business.lottery;

import com.eghm.enums.LotteryState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "状态 0:未开始 1:进行中 2:已结束")
    private LotteryState state;

    @Schema(description = "单日抽奖次数限制")
    private Integer lotteryDay;

    @Schema(description = "总抽奖次数限制")
    private Integer lotteryTotal;

    @Schema(description = "中奖次数限制")
    private Integer winNum;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
