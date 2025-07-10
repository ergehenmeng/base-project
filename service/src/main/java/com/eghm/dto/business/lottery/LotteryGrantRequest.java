
package com.eghm.dto.business.lottery;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "请填写备注")
    @Size(max = 200, message = "备注信息最大200字符")
    private String remark;
}
