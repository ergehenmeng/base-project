package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 抽奖记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-04-03
 */
@Data
@TableName("lottery_log")
@EqualsAndHashCode(callSuper = true)
public class LotteryLog extends BaseEntity {

    @Schema(description = "抽奖活动id")
    private Long lotteryId;

    @Schema(description = "用户id")
    private Long memberId;

    @Schema(description = "抽奖位置")
    private Integer location;

    @Schema(description = "是否中奖 false:未中奖 true:中奖")
    private Boolean winning;

    @Schema(description = "中奖奖品")
    private Long prizeId;
}
