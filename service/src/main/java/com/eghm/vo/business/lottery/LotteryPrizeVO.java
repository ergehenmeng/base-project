
package com.eghm.vo.business.lottery;

import com.eghm.enums.ref.PrizeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */

@Data
public class LotteryPrizeVO {

    @Schema(description = "奖品名称")
    private String prizeName;

    @Schema(description = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @Schema(description = "奖品图片")
    private String coverUrl;

    @Schema(description = "中奖位置 1-8")
    private Integer location;

}
