package com.eghm.vo.business.lottery;

import com.eghm.convertor.CentToYuanOmitSerializer;
import com.eghm.enums.PrizeType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */

@Data
public class LotteryConfigResponse {

    @ApiModelProperty("奖品位置")
    private Integer prizeIndex;

    @ApiModelProperty(value = "中奖商品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @ApiModelProperty(value = "中奖位置 1-8")
    private Integer location;

    @ApiModelProperty(value = "中奖权重")
    @JsonSerialize(using = CentToYuanOmitSerializer.class)
    private Integer weight;

    @ApiModelProperty(value = "奖品图片")
    private String coverUrl;
}
