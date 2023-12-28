package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "抽奖活动id")
    private Long lotteryId;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "抽奖位置")
    private Integer location;

    @ApiModelProperty("是否中奖 0:未中奖 1:中奖")
    private Boolean winning;

    @ApiModelProperty("中奖奖品")
    private Long prizeId;
}
