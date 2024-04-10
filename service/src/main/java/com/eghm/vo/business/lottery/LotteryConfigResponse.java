package com.eghm.vo.business.lottery;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.PrizeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @ApiModelProperty(value = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @ApiModelProperty(value = "中奖位置 1-8")
    private Integer location;

    @ApiModelProperty(value = "中奖权重")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer weight;
}
