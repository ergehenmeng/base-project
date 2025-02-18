package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.PrizeType;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "抽奖活动id")
    private Long lotteryId;

    @ApiModelProperty(value = "奖品id")
    private Long prizeId;

    @ApiModelProperty("位置index")
    private Integer prizeIndex;

    @ApiModelProperty(value = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @ApiModelProperty(value = "中奖位置 1-8")
    private Integer location;

    @ApiModelProperty(value = "中奖权重开始范围")
    private Integer startRange;

    @ApiModelProperty(value = "中奖权重截止范围")
    private Integer endRange;

    @ApiModelProperty(value = "中奖权重")
    private Integer weight;

}
