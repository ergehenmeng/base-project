package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.LotteryState;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 抽奖活动表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Data
@TableName("lottery")
@EqualsAndHashCode(callSuper = true)
public class Lottery extends BaseEntity {

    @Schema(description = "活动名称")
    private String title;

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "抽奖banner图")
    private String bannerUrl;

    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @Schema(description = "活动状态 0:未开始 1:进行中 2:已结束")
    private LotteryState state;

    @Schema(description = "单日抽奖次数限制")
    private Integer lotteryDay;

    @Schema(description = "总抽奖次数限制")
    private Integer lotteryTotal;

    @Schema(description = "中奖次数限制")
    private Integer winNum;

    @Schema(description = "抽奖页面封面图")
    private String coverUrl;

    @Schema(description = "抽奖标题")
    private String subTitle;

    @Schema(description = "抽奖规则")
    private String rule;

}
