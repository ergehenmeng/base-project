
package com.eghm.dto.business.lottery;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/2
 */
@Data
public class LotteryGrantRequest {

    @Schema(description = "中奖id")
    @NotNull(message = "请选择要发放的记录")
    private Long id;

    @Schema(description = "备注")
    private String remark;
}
