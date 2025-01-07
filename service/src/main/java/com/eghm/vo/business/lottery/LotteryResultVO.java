package com.eghm.vo.business.lottery;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/3
 */

@Data
public class LotteryResultVO {

    @Schema(description = "抽奖位置")
    private Integer location;

    @Schema(description = "是否中奖 0:未中奖 1:中奖")
    private Boolean winning;

    @Schema(description = "奖品名称")
    private String prizeName;

    @Schema(description = "奖品图片")
    private String coverUrl;
}
