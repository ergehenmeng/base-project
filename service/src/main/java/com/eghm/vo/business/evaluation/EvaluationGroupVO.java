package com.eghm.vo.business.evaluation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class EvaluationGroupVO {

    @ApiModelProperty("好评")
    private Long high;

    @ApiModelProperty("中评")
    private Long medium;

    @ApiModelProperty("差评")
    private Long bad;

    @ApiModelProperty("有图")
    private Long img;
}
