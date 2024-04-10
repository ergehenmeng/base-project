package com.eghm.vo.business.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/4/10
 */

@Data
public class LotteryLogVO {

    @ApiModelProperty("是否中奖 false:未中奖 true:中奖")
    private Boolean winning;

    @ApiModelProperty("奖品名称")
    private String prizeTitle;

    @ApiModelProperty("中奖数量")
    private Integer winNum;

    @ApiModelProperty("中奖时间时间")
    @JsonFormat(pattern = "MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
