package com.eghm.vo.business.evaluation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 二哥很猛
 * @since 2023/8/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvgScoreVO {

    @Schema(description = "订单数量")
    private Integer num;

    @Schema(description = "分数")
    private Integer totalScore;

    @Schema(description = "店铺id")
    private Long storeId;
}
