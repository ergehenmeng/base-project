package com.eghm.vo.business.evaluation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class EvaluationGroupVO {

    @Schema(description = "好评")
    private Long high;

    @Schema(description = "中评")
    private Long medium;

    @Schema(description = "差评")
    private Long bad;

    @Schema(description = "有图")
    private Long img;
}
