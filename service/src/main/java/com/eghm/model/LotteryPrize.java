package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.PrizeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 奖品信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Data
@TableName("lottery_prize")
@EqualsAndHashCode(callSuper = true)
public class LotteryPrize extends BaseEntity {

    @Schema(description = "商户id")
    private Long merchantId;

    @Schema(description = "抽奖活动id")
    private Long lotteryId;

    @Schema(description = "奖品名称")
    private String prizeName;

    @Schema(description = "奖品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @Schema(description = "关联商品ID")
    private Long relationId;

    @Schema(description = "单次中奖发放数量")
    private Integer num;

    @Schema(description = "奖品总数量")
    private Integer totalNum;

    @Schema(description = "已抽中数量")
    private Integer winNum;

    @Schema(description = "奖品图片")
    private String coverUrl;

}
