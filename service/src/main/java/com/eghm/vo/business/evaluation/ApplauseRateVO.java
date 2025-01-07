package com.eghm.vo.business.evaluation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class ApplauseRateVO {

    @Schema(description = "评论总数量")
    private Long commentNum;

    @Schema(description = "好评率百分比")
    private Integer rate;

}
