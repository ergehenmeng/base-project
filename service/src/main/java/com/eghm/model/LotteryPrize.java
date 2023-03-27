package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 奖品信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("lottery_prize")
@ApiModel(value="LotteryPrize对象", description="奖品信息表")
public class LotteryPrize extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
