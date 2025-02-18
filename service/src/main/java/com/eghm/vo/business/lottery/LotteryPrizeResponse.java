package com.eghm.vo.business.lottery;

import com.eghm.enums.PrizeType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryPrizeResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "奖品名称")
    private String prizeName;

    @ApiModelProperty(value = "奖品类型 0:谢谢参与 1:优惠券 2:积分")
    private PrizeType prizeType;

    @ApiModelProperty("关联商品ID")
    private Long relationId;

    @ApiModelProperty(value = "单次中奖发放数量")
    private Integer num;

    @ApiModelProperty(value = "奖品总数量")
    private Integer totalNum;

    @ApiModelProperty(value = "奖品图片")
    private String coverUrl;
}
