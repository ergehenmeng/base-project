package com.eghm.vo.business.lottery;

import com.eghm.enums.ref.PrizeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryPrizeResponse {

    @Schema(description = "id")
    private Long id;

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

    @Schema(description = "奖品图片")
    private String coverUrl;
}
