package com.eghm.vo.business.lottery;

import com.eghm.enums.ref.PrizeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */

@Data
public class LotteryConfigResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "奖品id")
    private Long prizeId;

    @ApiModelProperty(value = "中奖商品类型")
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
