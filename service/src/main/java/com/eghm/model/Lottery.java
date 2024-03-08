package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.LotteryState;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 抽奖活动表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Data
@TableName("lottery")
@EqualsAndHashCode(callSuper = true)
public class Lottery extends BaseEntity {

    @ApiModelProperty(value = "活动名称")
    private String title;

    @ApiModelProperty(value = "商户id")
    private Long merchantId;

    @ApiModelProperty("店铺id")
    private Long storeId;

    @ApiModelProperty("抽奖banner图")
    private String bannerUrl;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "活动状态 0:未开始 1:进行中 2:已结束")
    private LotteryState state;

    @ApiModelProperty(value = "单日抽奖次数限制")
    private Integer lotteryDay;

    @ApiModelProperty(value = "总抽奖次数限制")
    private Integer lotteryTotal;

    @ApiModelProperty(value = "中奖次数限制")
    private Integer winNum;

    @ApiModelProperty(value = "抽奖页面封面图")
    private String coverUrl;

    @ApiModelProperty(value = "抽奖标题")
    private String subTitle;

    @ApiModelProperty(value = "抽奖规则")
    private String rule;

}
