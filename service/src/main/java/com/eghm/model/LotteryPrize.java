package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty(value = "抽奖活动id")
    private Long lotteryId;

    @ApiModelProperty(value = "奖品名称")
    private String prizeName;

    @ApiModelProperty(value = "奖品类型")
    private Boolean prizeType;

    @ApiModelProperty(value = "奖品总数量")
    private Integer totalNum;

    @ApiModelProperty(value = "已抽中数量")
    private Integer winNum;

    @ApiModelProperty(value = "奖品图片")
    private String coverUrl;

}
