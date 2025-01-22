package com.eghm.vo.business.lottery;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.eghm.enums.PrizeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */

@Data
public class LotteryConfigResponse {

    @Schema(description = "奖品位置")
    private Integer prizeIndex;

    @Schema(description = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @Schema(description = "中奖位置 1-8")
    private Integer location;

    @Schema(description = "中奖权重")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer weight;

    @Schema(description = "奖品图片")
    private String coverUrl;
}
