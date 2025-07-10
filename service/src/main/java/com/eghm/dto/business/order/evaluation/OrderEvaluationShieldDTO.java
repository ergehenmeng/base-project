package com.eghm.dto.business.order.evaluation;

import com.eghm.annotation.Assign;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/8/29
 */
@Data
public class OrderEvaluationShieldDTO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "屏蔽原因")
    @NotBlank(message = "请填写屏蔽原因")
    @Size(max = 100, message = "屏蔽原因最大100字符")
    private String remark;

    @Assign
    @Schema(description = "用户id", hidden = true)
    private Long userId;
}
