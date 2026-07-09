package com.eghm.application.shared.dto.operate.help;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/20 20:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HelpEditRequest extends HelpAddRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

}
