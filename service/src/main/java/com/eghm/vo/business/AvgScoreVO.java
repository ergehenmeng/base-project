package com.eghm.vo.business;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("订单数量")
    private Integer num;

    @ApiModelProperty("分数")
    private Integer totalScore;
}
