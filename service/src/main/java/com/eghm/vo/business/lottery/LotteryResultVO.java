package com.eghm.vo.business.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/3
 */

@Data
public class LotteryResultVO {

    @ApiModelProperty(value = "抽奖位置")
    private Integer location;

    @ApiModelProperty("是否中奖 0:未中奖 1:中奖")
    private Boolean winning;

    @ApiModelProperty("奖品名称")
    private String prizeName;

    @ApiModelProperty(value = "奖品图片")
    private String coverUrl;
}
