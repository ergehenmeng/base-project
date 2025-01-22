package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.PrizeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 抽奖位置配置表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Data
@TableName("lottery_config")
@EqualsAndHashCode(callSuper = true)
public class LotteryConfig extends BaseEntity {

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "抽奖活动id")
    private Long lotteryId;

    @Schema(description = "奖品id")
    private Long prizeId;

    @Schema(description = "位置index")
    private Integer prizeIndex;

    @Schema(description = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @Schema(description = "中奖位置 1-8")
    private Integer location;

    @Schema(description = "中奖权重开始范围")
    private Integer startRange;

    @Schema(description = "中奖权重截止范围")
    private Integer endRange;

    @Schema(description = "中奖权重")
    private Integer weight;

}
