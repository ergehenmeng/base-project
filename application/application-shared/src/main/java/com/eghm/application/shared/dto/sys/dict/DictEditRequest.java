package com.eghm.application.shared.dto.sys.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2019/1/14 11:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictEditRequest extends DictAddRequest {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;
}
