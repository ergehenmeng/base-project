package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 抽奖记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lottery_log")
public class LotteryLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
